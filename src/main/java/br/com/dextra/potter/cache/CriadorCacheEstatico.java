package br.com.dextra.potter.cache;

import br.com.dextra.potter.service.IntegracaoDeHouses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class CriadorCacheEstatico implements ApplicationRunner {

    private final Logger log = LoggerFactory.getLogger(CriadorCacheEstatico.class);

    private final IntegracaoDeHouses integracaoDeHouses;

    public CriadorCacheEstatico(IntegracaoDeHouses integracaoDeHouses) {
        this.integracaoDeHouses = integracaoDeHouses;
    }

    @Override
    public void run(ApplicationArguments args) {
        integracaoDeHouses.popularHouse()
                .subscribe(x -> log.info("Carga de Houses realizada com sucesso."),
                           e -> log.error("Error ao realizar Carga de dados. Message:{}", e.getMessage()));
    }
}
