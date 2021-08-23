package br.com.dextra.potter.service;

import br.com.dextra.potter.client.PotterApiClient;
import br.com.dextra.potter.dto.HouseDTO;
import br.com.dextra.potter.repository.mongodb.HouseMongoDBRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class HouseService {

    private final PotterApiClient potterApiClient;
    private final HouseMongoDBRepository houseMongoDBRepository;

    public HouseService(PotterApiClient potterApiClient, HouseMongoDBRepository houseMongoDBRepository) {
        this.potterApiClient = potterApiClient;
        this.houseMongoDBRepository = houseMongoDBRepository;
    }

    public List<HouseDTO> findAll() {
        return this.houseMongoDBRepository.findAll();
    }

    public List<HouseDTO> findAllByPotterApi(String apiKey) {
        return this.potterApiClient.getHouses(apiKey);
    }

    public List<HouseDTO> saveAll(List<HouseDTO> list) {

        if (list != null && !list.isEmpty()) {
            list = this.houseMongoDBRepository.saveAll(list);
        }
        return list;
    }

    @Cacheable(value="HouseService", key="#root.method.toString().concat(#id)")
    public Optional<HouseDTO> findOne(String id) {
        Optional<HouseDTO> houseOptional = Optional.empty();

        if (isNotBlank(id)) {
            houseOptional = houseMongoDBRepository.findById(id);
        }

        return houseOptional;
    }
}
