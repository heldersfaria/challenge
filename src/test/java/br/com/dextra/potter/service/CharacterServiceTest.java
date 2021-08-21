package br.com.dextra.potter.service;

import br.com.dextra.potter.mapper.CharacterMapper;
import br.com.dextra.potter.repository.h2.CharacterH2Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;

public class CharacterServiceTest {

    @InjectMocks
    private CharacterService characterService;

    @Mock
    private CharacterH2Repository characterH2Repository;

    @Mock
    private CharacterMapper characterMapper;

    @Mock
    private HouseService houseService;

    @BeforeEach
    public void beforeEach() {
        openMocks(this);
    }

    @Test
    public void when_findOne_receber_NullOrEmptyString_should_do_nothing() {
        assertThat(characterService.findOne("")).isEmpty();
        assertThat(characterService.findOne(null)).isEmpty();
        assertThat(characterService.findOne(" ")).isEmpty();
        verifyNoMoreInteractions(characterH2Repository);
    }

    @Test
    public void when_delete_receber_NullOrEmptyString_should_do_nothing() {
        characterService.delete("");
        characterService.delete(" ");
        characterService.delete(null);
        verifyNoMoreInteractions(characterH2Repository);
    }

    @Test
    public void when_findByHouse_receber_NullOrEmptyString_should_do_nothing() {
        assertThat(characterService.findByHouse("")).isNotNull().isEmpty();
        assertThat(characterService.findByHouse(null)).isNotNull().isEmpty();
        assertThat(characterService.findByHouse(" ")).isNotNull().isEmpty();
        verifyNoMoreInteractions(characterH2Repository);
    }
}

