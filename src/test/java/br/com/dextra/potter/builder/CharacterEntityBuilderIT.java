package br.com.dextra.potter.builder;


import br.com.dextra.potter.domain.CharacterEntity;
import br.com.dextra.potter.dto.HouseDTO;

import javax.persistence.EntityManager;

import static br.com.dextra.potter.utils.ConstantUtils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class CharacterEntityBuilderIT extends BaseBuilderIT<CharacterEntity> {

    public CharacterEntityBuilderIT() {
        object = new CharacterEntity();
    }

    public CharacterEntityBuilderIT(EntityManager em) {
        this();
        this.entityManager = em;
    }

    public static CharacterEntityBuilderIT characterBuilder() {
        return new CharacterEntityBuilderIT();
    }

    public static CharacterEntityBuilderIT characterBuilder(EntityManager em) {
        return new CharacterEntityBuilderIT(em);
    }

    @Override
    public CharacterEntity build() {

        if (isBlank(object.getSchool())) {
            object.setSchool(SCHOOL);
        }

        if (isBlank(object.getPatronus())) {
            object.setPatronus(PATRONUS);
        }
        if (isBlank(object.getName())) {
            object.setName(NAME);
        }

        if (isBlank(object.getHouse())) {
            object.setHouse(HOUSE);
        }
        return super.build();
    }

    public CharacterEntityBuilderIT house(String house) {
        object.setHouse(house);
        return this;
    }

    public CharacterEntityBuilderIT house(HouseDTO houseDTO) {
        object.setHouse(houseDTO.getId());
        return this;
    }

}
