package br.com.dextra.potter.web;

import br.com.dextra.potter.domain.CharacterEntity;
import br.com.dextra.potter.dto.CharacterDTO;
import br.com.dextra.potter.dto.HouseDTO;
import br.com.dextra.potter.mapper.CharacterMapper;
import br.com.dextra.potter.repository.h2.CharacterH2Repository;
import br.com.dextra.potter.repository.mongodb.HouseMongoDBRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.dextra.potter.builder.CharacterEntityBuilderIT.characterBuilder;
import static br.com.dextra.potter.builder.HouseDTOBuilderIT.houseBuilder;
import static br.com.dextra.potter.web.TestUtil.APPLICATION_JSON_UTF8;
import static br.com.dextra.potter.web.TestUtil.convertObjectToJsonBytes;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CharacterResourceIT extends ResourceBaseIT {

    @Autowired
    private CharacterResource characterResource;

    @Autowired
    private HouseMongoDBRepository houseRepository;

    @Autowired
    private CharacterH2Repository characterH2Repository;

    @Autowired
    private CharacterMapper characterMapper;

    private HouseDTO houseDTO;
    private CharacterEntity character;

    @Override
    public CharacterResource getResource() {
        return characterResource;
    }

    @BeforeEach
    public void initTest() {
        houseDTO = houseBuilder(houseRepository).build();
        character = characterBuilder(em).build();
    }

    @Test
    @Transactional
    public void verificarExistenciaDeVinculoComHouseNaBaseDados() throws Exception {
        int databaseSizeBeforeCreate = characterH2Repository.findAll().size();

        character.setHouse(houseDTO.getId());
        CharacterDTO characterDTO = characterMapper.toDto(character);

        restMockMvc.perform(post(appServiceDomainUri + "/characters")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(characterDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath_for_badRequest("A casa relacionada a este personagem não existe."));

        assertThat(characterH2Repository.findAll()).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void createCharacter() throws Exception {
        int databaseSizeBeforeCreate = characterH2Repository.findAll().size();

        houseRepository.save(houseDTO);

        character.setHouse(houseDTO.getId());
        CharacterDTO characterDTO = characterMapper.toDto(character);

        restMockMvc.perform(post(appServiceDomainUri + "/characters")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(characterDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.name").value(character.getName()))
                .andExpect(jsonPath("$.house").value(character.getHouse()))
                .andExpect(jsonPath("$.patronus").value(character.getPatronus()))
                .andExpect(jsonPath("$.school").value(character.getSchool()));

        List<CharacterEntity> characterList = characterH2Repository.findAll();

        assertThat(characterList).hasSize(databaseSizeBeforeCreate + 1);

        CharacterEntity testCharacter = characterList.get(characterList.size() - 1);

        assertThat(testCharacter.getName()).isEqualTo(character.getName());
        assertThat(testCharacter.getHouse()).isEqualTo(character.getHouse());
        assertThat(testCharacter.getPatronus()).isEqualTo(character.getPatronus());
        assertThat(testCharacter.getSchool()).isEqualTo(character.getSchool());
    }

    @Test
    @Transactional
    public void createCharacterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = characterH2Repository.findAll().size();

        houseRepository.save(houseDTO);

        character.setHouse(houseDTO.getId());
        CharacterDTO characterDTO = characterMapper.toDto(character);
        characterDTO.setId(randomUUID().toString());

        restMockMvc.perform(post(appServiceDomainUri + "/characters")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(characterDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath_for_badRequest("A new AntiFraude Documental cannot already have an ID."));

        assertThat(characterH2Repository.findAll()).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCharacteres() throws Exception {
        houseRepository.save(houseDTO);
        character.setHouse(houseDTO.getId());
        characterH2Repository.saveAndFlush(character);

        restMockMvc.perform(get(appServiceDomainUri + "/characters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(hasItem(character.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(character.getName())))
                .andExpect(jsonPath("$.[*].house").value(hasItem(character.getHouse())))
                .andExpect(jsonPath("$.[*].patronus").value(hasItem(character.getPatronus())))
                .andExpect(jsonPath("$.[*].house").value(hasItem(character.getHouse())))
                .andExpect(jsonPath("$.[*].school").value(hasItem(character.getSchool())));
    }

    @Test
    @Transactional
    public void getCharacteresByHouse() throws Exception {
        houseRepository.save(houseDTO);
        character.setHouse(houseDTO.getId());
        characterH2Repository.saveAndFlush(character);

        restMockMvc.perform(get(appServiceDomainUri + "/characters?house={house}", houseDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(hasItem(character.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(character.getName())))
                .andExpect(jsonPath("$.[*].house").value(hasItem(character.getHouse())))
                .andExpect(jsonPath("$.[*].patronus").value(hasItem(character.getPatronus())))
                .andExpect(jsonPath("$.[*].house").value(hasItem(character.getHouse())))
                .andExpect(jsonPath("$.[*].school").value(hasItem(character.getSchool())));
    }

    @Test
    @Transactional
    public void getCharacter() throws Exception {
        houseRepository.save(houseDTO);
        character.setHouse(houseDTO.getId());
        characterH2Repository.saveAndFlush(character);

        restMockMvc.perform(get(appServiceDomainUri + "/characters/{id}", character.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(character.getId()))
                .andExpect(jsonPath("$.name").value(character.getName()))
                .andExpect(jsonPath("$.house").value(character.getHouse()))
                .andExpect(jsonPath("$.patronus").value(character.getPatronus()))
                .andExpect(jsonPath("$.house").value((character.getHouse())))
                .andExpect(jsonPath("$.school").value(character.getSchool()));
    }

    @Test
    @Transactional
    public void getNonExistingCharacter() throws Exception {
        restMockMvc.perform(get(appServiceDomainUri + "/characters/{id}", randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void getNonValidCharacter() throws Exception {
        restMockMvc.perform(get(appServiceDomainUri + "/characters/{id}", "XXXX"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void udpateCharacter() throws Exception {
        character.setHouse(houseRepository.save(houseDTO).getId());
        characterH2Repository.saveAndFlush(character);

        int databaseSizeBeforeCreate = characterH2Repository.findAll().size();

        CharacterDTO characterDTO = characterMapper.toDto(character);

        characterDTO.setName(characterDTO.getName() + "_UPDATED");
        characterDTO.setSchool(characterDTO.getSchool() + "_UPDATED");
        characterDTO.setPatronus(characterDTO.getPatronus() + "_UPDATED");
        characterDTO.setHouse(houseBuilder(houseRepository).save().getId());

        restMockMvc.perform(put(appServiceDomainUri + "/characters")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(characterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(characterDTO.getId()))
                .andExpect(jsonPath("$.name").value(characterDTO.getName()))
                .andExpect(jsonPath("$.house").value(characterDTO.getHouse()))
                .andExpect(jsonPath("$.patronus").value(characterDTO.getPatronus()))
                .andExpect(jsonPath("$.school").value(characterDTO.getSchool()));

        List<CharacterEntity> characterList = characterH2Repository.findAll();

        assertThat(characterList).hasSize(databaseSizeBeforeCreate);

        CharacterEntity testCharacter = characterList.get(characterList.size() - 1);

        assertThat(testCharacter.getId()).isEqualTo(characterDTO.getId());
        assertThat(testCharacter.getName()).isEqualTo(characterDTO.getName());
        assertThat(testCharacter.getHouse()).isEqualTo(characterDTO.getHouse());
        assertThat(testCharacter.getPatronus()).isEqualTo(characterDTO.getPatronus());
        assertThat(testCharacter.getSchool()).isEqualTo(characterDTO.getSchool());
    }

    @Test
    @Transactional
    public void udpateCharacterWithNotExistingCharacter() throws Exception {
        int databaseSizeBeforeCreate = characterH2Repository.findAll().size();

        houseRepository.save(houseDTO);

        character.setHouse(houseDTO.getId());
        CharacterDTO characterDTO = characterMapper.toDto(character);

        restMockMvc.perform(put(appServiceDomainUri + "/characters")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(characterDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath_for_badRequest("Id AntiFraude Documental  is null or empty."));

        assertThat(characterH2Repository.findAll()).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void deletedCharacter() throws Exception {
        character.setHouse(houseRepository.save(houseDTO).getId());
        characterH2Repository.saveAndFlush(character);

        int databaseSizeBeforeCreate = characterH2Repository.findAll().size();

        restMockMvc.perform(delete(appServiceDomainUri + "/characters/{id}", character.getId()))
                .andExpect(status().isNoContent());

        List<CharacterEntity> cartaoCreditoList = characterH2Repository.findAll();
        assertThat(cartaoCreditoList).hasSize(databaseSizeBeforeCreate - 1);
    }
}
