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

            // 3. materials 폴더 내 모든 파일을 InputStream으로 순회
            Resource materialDir = new ClassPathResource("static/json/materials/");
            String[] materialFileNames = Objects.requireNonNull(materialDir.getFile().list((dir, name) -> name.endsWith(".json")));

            Set<String> result = new HashSet<>();
            for (String fileName : materialFileNames) {
                try (InputStream materialStream = new ClassPathResource("static/json/materials/" + fileName).getInputStream()) {
                    JsonNode material = objectMapper.readTree(materialStream);
                    int id = material.path("id").asInt();
                    if (materialIds.contains(id)) {
                        JsonNode daysNode = material.path("daysOfWeek");
                        if (daysNode != null && daysNode.isArray()) {
                            for (JsonNode day : daysNode) {
                                result.add(day.asText());
                            }
                        }
                    }
                } catch (Exception ignore) {
                }
            }

            return new ArrayList<>(result);

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
