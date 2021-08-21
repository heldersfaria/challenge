package br.com.dextra.potter.service;

import br.com.dextra.potter.domain.CharacterEntity;
import br.com.dextra.potter.dto.CharacterDTO;
import br.com.dextra.potter.mapper.CharacterMapper;
import br.com.dextra.potter.repository.h2.CharacterH2Repository;
import br.com.dextra.potter.web.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@Transactional
public class CharacterService {

    private final CharacterH2Repository characterH2Repository;
    private final CharacterMapper characterMapper;
    private final HouseService houseService;

    public CharacterService(CharacterH2Repository characterH2Repository,
                            CharacterMapper characterMapper, HouseService houseService) {
        this.characterH2Repository = characterH2Repository;
        this.characterMapper = characterMapper;
        this.houseService = houseService;
    }

    public CharacterDTO save(CharacterDTO characterDTO) {

        if (houseService.findOne(characterDTO.getHouse()).isEmpty()) {
            throw new BadRequestAlertException("A casa relacionada a este personagem não existe.");
        }

        CharacterEntity character = characterMapper.toEntity(characterDTO);
        character = characterH2Repository.save(character);
        return characterMapper.toDto(character);
    }

    @Transactional(readOnly = true)
    public Optional<CharacterDTO> findOne(String id) {
        if (isBlank(id)) {
            return empty();
        }
        return characterH2Repository.findById(id).map(characterMapper::toDto);
    }

    public void delete(String id) {
        if (isNotBlank(id)) {
            characterH2Repository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public List<CharacterDTO> findAll(String house) {
        if (isNotBlank(house)) {
            return findByHouse(house);
        }

        return characterMapper.toDto(characterH2Repository.findAll());
    }

    @Transactional(readOnly = true)
    public List<CharacterDTO> findByHouse(String house) {
        if (isBlank(house)) {
            return new ArrayList<>();
        }
        return characterMapper.toDto(characterH2Repository.findByHouse(house));
    }
}
