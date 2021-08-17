package br.com.dextra.potter.web;

import br.com.dextra.potter.dto.HouseDTO;
import br.com.dextra.potter.service.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("${app.service.domain-uri}")
public class HouseResource {

    private final HouseService houseService;

    public HouseResource(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/houses")
    public ResponseEntity<List<HouseDTO>> getAllHouses() {
        return ok().body(this.houseService.findAll());
    }

    @GetMapping("/houses/{id}")
    public ResponseEntity<HouseDTO> getHouse(@PathVariable String id) {
        return houseService.findOne(id)
                .map(houseDTO -> ok().body(houseDTO))
                .orElseGet(() -> ok().build());
    }
}
