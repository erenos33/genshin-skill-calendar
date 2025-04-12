package me.genshin.skillcalendar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.genshin.skillcalendar.dto.CharacterDetailDto;
import me.genshin.skillcalendar.service.CharacterService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    @GetMapping
    public ResponseEntity<List<String>> getAllCharacters() {
        return ResponseEntity.ok(characterService.getCharacterNames());
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getCharacterDetail(
            @PathVariable String name,
            @RequestParam(defaultValue = "en") String lang
    ) {
        try {
            String folder = switch (lang) {
                case "ko" -> "characters-ko";
                case "ja" -> "characters-ja";
                default -> "characters";
            };

            String filePath = String.format("classpath:static/json/%s/%s.json", folder, name.toLowerCase());
            var resource = resourceLoader.getResource(filePath);

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            var jsonNode = objectMapper.readTree(resource.getInputStream());
            return ResponseEntity.ok(jsonNode);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("데이터 로딩 실패: " + e.getMessage());
        }
    }
}