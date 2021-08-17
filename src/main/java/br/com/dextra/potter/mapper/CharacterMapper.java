package br.com.dextra.potter.mapper;


import br.com.dextra.potter.domain.CharacterEntity;
import br.com.dextra.potter.dto.CharacterDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface CharacterMapper  {

    CharacterEntity toEntity(CharacterDTO dto);

    CharacterDTO toDto(CharacterEntity entity);

    List<CharacterEntity> toEntity(List<CharacterDTO> dtoList);

    List<CharacterDTO> toDto(List<CharacterEntity> entityList);

}
