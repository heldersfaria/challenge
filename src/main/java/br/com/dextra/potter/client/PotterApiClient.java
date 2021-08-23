package br.com.dextra.potter.client;

import br.com.dextra.potter.dto.DadosCadastroDeUsuario;
import br.com.dextra.potter.dto.HouseDTO;
import br.com.dextra.potter.exceptions.RetryableException;
import br.com.dextra.potter.utils.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static br.com.dextra.potter.utils.GsonUtils.getAsJsonObject;
import static org.springframework.http.HttpStatus.*;
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

    @CircuitBreaker(name = "potter-api-cb")
    @Retry(name = "potter-api-r", fallbackMethod = "retryfallback")
    public List<HouseDTO> getHouses(String apikey) {
        return webClient.get()
                .uri(urlHouses)
                .header("apikey", apikey)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::readHouseResponse)
                .onErrorMap(WebClientException.class, this::handleHttpClientException)
                .block();
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

    public List<HouseDTO> retryfallback(String url, RetryableException exception) {
        return new ArrayList<>();
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

    private Throwable handleHttpClientException(Throwable ex) {
        if (ex instanceof WebClientResponseException) {
            WebClientResponseException exception = ((WebClientResponseException) ex);
            if (INTERNAL_SERVER_ERROR.equals(exception.getStatusCode()) ||
                    SERVICE_UNAVAILABLE.equals(exception.getStatusCode()) ||
                    GATEWAY_TIMEOUT.equals(exception.getStatusCode()) ||
                    BAD_GATEWAY.equals(exception.getStatusCode())) {
                return new RetryableException();
            }
        }
        return ex;
    }
}
