package br.com.dextra.potter.service;

import br.com.dextra.potter.client.PotterApiClient;
import br.com.dextra.potter.config.UserCadastroProperties;
import br.com.dextra.potter.dto.HouseDTO;
import br.com.dextra.potter.dto.InformacoesParaCadastroUsuario;
import br.com.dextra.potter.mapper.DadosCadastroDeUsuarioMapper;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class IntegracaoDeHouses {

    private final Logger log = LoggerFactory.getLogger(IntegracaoDeHouses.class);

    private final HouseService houseService;
    private final PotterApiClient potterApiClient;
    private final UserCadastroProperties userCadastradoProperties;
    private final DadosCadastroDeUsuarioMapper dadosCadastroDeUsuarioMapper;

    public IntegracaoDeHouses(HouseService houseService,
                              PotterApiClient potterApiClient,
                              UserCadastroProperties userCadastradoProperties,
                              DadosCadastroDeUsuarioMapper dadosCadastroDeUsuarioMapper) {
        this.houseService = houseService;
        this.potterApiClient = potterApiClient;
        this.userCadastradoProperties = userCadastradoProperties;
        this.dadosCadastroDeUsuarioMapper = dadosCadastroDeUsuarioMapper;
    }

    public Mono<Void> popularHouse() {

        Mono<Void> mono = Mono.fromSupplier(() -> {

            List<HouseDTO> housesPersisted = houseService.findAll();

            if (housesPersisted.isEmpty()) {

                InformacoesParaCadastroUsuario informacoesParaCadastroUsuario = this.dadosCadastroDeUsuarioMapper.montarCadastro(this.userCadastradoProperties);
                JsonObject jsonObject = this.potterApiClient.getUser(informacoesParaCadastroUsuario);

                String apikey = jsonObject.get("apiKey").getAsString();

                List<HouseDTO> houses = potterApiClient.getHouses(apikey);

                if (!houses.isEmpty()) {
                    housesPersisted = houseService.saveAll(houses);
                }
            }

            Flux.fromStream(housesPersisted.stream())
                    .subscribe(h -> {
                                log.info("House {} persistida com sucesso!", h.getId());
                                houseService.findOne(h.getId());
                            },
                            e -> log.error("Erro ao persistir house. {}", e.getMessage()));

            return null;
        });

        return mono.then();
    }
}