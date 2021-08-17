package br.com.dextra.potter.web;

import br.com.dextra.potter.dto.CharacterDTO;
import br.com.dextra.potter.service.CharacterService;
import br.com.dextra.potter.validation.UUID;
import br.com.dextra.potter.web.errors.BadRequestAlertException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("${app.service.domain-uri}")
public class CharacterResource {

    private final CharacterService characterService;

    public CharacterResource(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping("/characters")
    public ResponseEntity<CharacterDTO> createCharacter(@Valid @RequestBody CharacterDTO characterDTO) throws URISyntaxException {

        if (characterDTO.getId() != null) {
            throw new BadRequestAlertException("A new AntiFraude Documental cannot already have an ID");
        }

        CharacterDTO result = characterService.save(characterDTO);

        return ResponseEntity.created(new URI("/api/characters/" + result.getId())).body(result);
    }

    @PutMapping("/characters")
    public ResponseEntity<CharacterDTO> updateCharacter(@Valid @RequestBody CharacterDTO characterDTO) {

        if (StringUtils.isEmpty(characterDTO.getId())) {
            throw new BadRequestAlertException("Id AntiFraude Documental  is null or empty.");
        }

        return ok().body(characterService.save(characterDTO));
    }

    @GetMapping("/characters")
    public ResponseEntity<List<CharacterDTO>> getAllCharacters(@RequestParam(required = false) String house) {
        return ok().body(this.characterService.findAll(house));
    }

    @GetMapping("/characters/{id}")
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable @UUID String id) {
        return characterService.findOne(id)
                .map(characterDTO -> ok().body(characterDTO))
                .orElseGet(() -> ok().build());
    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<Void> deleteCharacter(@Valid @PathVariable @UUID String id) {
        characterService.delete(id);
        return noContent().build();
    }
}
