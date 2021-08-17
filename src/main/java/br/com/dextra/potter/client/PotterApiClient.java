package br.com.dextra.potter.client;

import br.com.dextra.potter.dto.DadosCadastroDeUsuario;
import br.com.dextra.potter.dto.HouseDTO;
import br.com.dextra.potter.utils.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static br.com.dextra.potter.utils.GsonUtils.getAsJsonObject;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class PotterApiClient {

    private final WebClient webClient;

    @Value("${app.service.potter-api.domain.uri-houses}")
    private String urlHouses;

    @Value("${app.service.potter-api.domain.uri-users}")
    private String urlUsers;

    public PotterApiClient() {
        this.webClient = WebClient.create();
    }

    public List<HouseDTO> getHouses(String apikey) {
        return webClient.get()
                .uri(urlHouses)
                .header("apikey", apikey)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .map(this::readHouseResponse)
                .block();
    }

    private List<HouseDTO> readHouseResponse(String jsonResponse) {
        List<HouseDTO> listaResultados = new ArrayList<>();

        try {
            JsonArray jsonArray = GsonUtils.getAsJsonArray(jsonResponse, "houses");
            listaResultados = new Gson().fromJson(jsonArray, new TypeToken<List<HouseDTO>>() {
            }.getType());
        } catch (Exception ignored) {
        }

        return listaResultados;
    }

    public JsonObject getUser(DadosCadastroDeUsuario dadosUsuario) {
        return webClient.post()
                .uri(urlUsers)
                .contentType(APPLICATION_JSON)
                .bodyValue(dadosUsuario)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .map(responseBody -> getAsJsonObject(responseBody, "user"))
                .block();
    }
}
