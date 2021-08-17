package br.com.dextra.potter.repository.h2;

import br.com.dextra.potter.domain.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterH2Repository extends JpaRepository<CharacterEntity, String> {

    @Query("select c from CharacterEntity c where c.house = :house ")
    List<CharacterEntity> findByHouse(@Param("house") String house);
}
