package me.genshin.skillcalendar.controller;

import lombok.RequiredArgsConstructor;
import me.genshin.skillcalendar.service.TalentMaterialService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterCalendarController {

    private final TalentMaterialService talentMaterialService;

    @GetMapping("/{characterId}/skill-days")
    public List<String> getSkillMaterialDays(@PathVariable String characterId) throws IOException {
        System.out.println("💡 API 요청 들어옴: " + characterId);
        return talentMaterialService.getMaterialDays(characterId);
    }
}