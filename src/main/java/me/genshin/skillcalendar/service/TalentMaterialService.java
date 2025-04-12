package me.genshin.skillcalendar.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TalentMaterialService {

    private final ObjectMapper objectMapper;

    public List<String> getMaterialDays(String characterName) throws IOException {
        System.out.println("🚀 service 진입: " + characterName);

        // talents JSON 파일 경로
        String talentPath = "src/main/resources/static/json/talents/" + characterName.toLowerCase() + ".json";
        File talentFile = new File(talentPath);

        if (!talentFile.exists()) {
            System.out.println("❌ 해당 캐릭터의 talent json이 존재하지 않음: " + talentPath);
            return List.of();
        }

        JsonNode talentJson = objectMapper.readTree(talentFile);
        JsonNode materialNode = talentJson.path("costs").path("lvl2");

        if (!materialNode.isArray() || materialNode.size() < 2) {
            System.out.println("⚠️ lvl2 배열이 없거나 아이템이 부족함!");
            return List.of();
        }

        String materialName = materialNode.get(1).path("name").asText();
        System.out.println("📘 스킬책 이름: " + materialName);

        String materialFileName = materialName.replaceAll("\\s", "") + ".json";
        String materialPath = "src/main/resources/static/json/materials/" + materialFileName;

        File materialFile = new File(materialPath);
        if (!materialFile.exists()) {
            System.out.println("❌ 재료 파일 없음: " + materialPath);
            return List.of();
        }

        JsonNode materialJson = objectMapper.readTree(materialFile);
        JsonNode daysNode = materialJson.path("daysOfWeek");

        if (!daysNode.isArray()) {
            System.out.println("❌ 요일 정보 없음");
            return List.of();
        }

        List<String> days = new ArrayList<>();
        for (JsonNode day : daysNode) {
            days.add(day.asText());
        }

        System.out.println("📅 요일 정보 반환: " + days);
        return days;
    }
}