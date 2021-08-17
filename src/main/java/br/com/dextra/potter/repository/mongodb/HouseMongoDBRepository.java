package br.com.dextra.potter.repository.mongodb;

import br.com.dextra.potter.dto.HouseDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseMongoDBRepository extends MongoRepository<HouseDTO, String>{
}
