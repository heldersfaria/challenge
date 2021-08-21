package br.com.dextra.potter.builder;


import br.com.dextra.potter.dto.HouseDTO;
import br.com.dextra.potter.repository.mongodb.HouseMongoDBRepository;

import java.util.ArrayList;

import static br.com.dextra.potter.utils.ConstantUtils.*;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class HouseDTOBuilderIT {

    private final HouseMongoDBRepository repository;
    private HouseDTO object;

    private HouseDTOBuilderIT(HouseMongoDBRepository repository) {
        this.object = new HouseDTO();
        this.repository = repository;
    }

    public static HouseDTOBuilderIT houseBuilder(HouseMongoDBRepository repository) {
        return new HouseDTOBuilderIT(repository);
    }

    public HouseDTO build() {

        object.setId(randomUUID().toString());

        if (isBlank(object.getSchool())) {
            object.setSchool(SCHOOL);
        }

        if (isBlank(object.getMascot())) {
            object.setMascot(MASCOT);
        }

        if (isBlank(object.getFounder())) {
            object.setFounder(FOUNDER);
        }

        if (isBlank(object.getName())) {
            object.setName(NAME);
        }

        if (isBlank(object.getHouseGhost())) {
            object.setHouseGhost(HOUSE_GHOST);
        }

        if (isBlank(object.getHeadOfHouse())) {
            object.setHeadOfHouse(HEAD_OF_HOUSE);
        }

        if (object.getColors() == null) {
            object.setColors(new ArrayList<>());
        }

        if (object.getValues() == null) {
            object.setValues(new ArrayList<>());
        }

        return object;
    }

    public HouseDTO save() {

        build();

        if (repository != null) {
            this.repository.save(object);
        }

        return object;
    }
}
