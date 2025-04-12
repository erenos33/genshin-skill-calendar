package me.genshin.skillcalendar.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TalentMaterialService {

    private final ObjectMapper objectMapper;

    public List<String> getAvailableDays(String characterName) {
        Set<Integer> materialIds = new HashSet<>();

        try {
            // 1. 캐릭터 JSON 열기
            Resource characterResource = new ClassPathResource("static/json/talents/" + characterName.toLowerCase() + ".json");
            try (InputStream is = characterResource.getInputStream()) {
                JsonNode root = objectMapper.readTree(is);
                JsonNode costs = root.path("costs");
                if (costs.isMissingNode()) return Collections.emptyList();

                // 2. lvl2~lvl10 순회하며 모든 재료 id 수집
                for (int i = 2; i <= 10; i++) {
                    JsonNode levelNode = costs.path("lvl" + i);
                    if (levelNode.isArray()) {
                        for (JsonNode item : levelNode) {
                            if (item.has("id")) {
                                materialIds.add(item.get("id").asInt());
                            }
                        }
                    }
                }
            }

            // 3. materials.json 하나만 열어서 요일 추출
            Resource materialsJson = new ClassPathResource("static/json/materials.json");
            try (InputStream is = materialsJson.getInputStream()) {
                JsonNode materials = objectMapper.readTree(is);

                Set<String> result = new HashSet<>();
                for (JsonNode material : materials) {
                    int id = material.path("id").asInt();
                    if (materialIds.contains(id)) {
                        JsonNode daysNode = material.path("daysOfWeek");
                        if (daysNode != null && daysNode.isArray()) {
                            for (JsonNode day : daysNode) {
                                result.add(day.asText());
                            }
                        }
                    }
                }

                return new ArrayList<>(result);
            }

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
