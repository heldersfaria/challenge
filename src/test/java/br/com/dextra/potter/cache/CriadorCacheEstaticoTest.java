package br.com.dextra.potter.cache;

import br.com.dextra.potter.service.IntegracaoDeHouses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.ApplicationArguments;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class CriadorCacheEstaticoTest {

    @InjectMocks
    private CriadorCacheEstatico criadorCacheEstatico;

    @Mock
    private IntegracaoDeHouses integracaoDeHouses;

    @Mock
    private ApplicationArguments applicationArguments;

    @BeforeEach
    public void before() {
        openMocks(this);
    }

    @Test
    public void testeRun() {
        when(integracaoDeHouses.popularHouse()).thenReturn(Mono.empty().then());
        criadorCacheEstatico.run(applicationArguments);
        verify(integracaoDeHouses, times(1)).popularHouse();
    }

    @Test
    public void testeRunWithError() {
        when(integracaoDeHouses.popularHouse()).thenThrow(new RuntimeException("Error"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> criadorCacheEstatico.run(applicationArguments));
        assertEquals(exception.getMessage(), "Error");
        verify(integracaoDeHouses, times(1)).popularHouse();
    }
}
