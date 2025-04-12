package me.genshin.skillcalendar.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TalentMaterialService {

    private final ObjectMapper objectMapper;

    public List<String> getAvailableDays(String characterName) {
        try {
            // 1. 캐릭터 JSON 불러오기
            Resource talentResource = new ClassPathResource("static/json/talents/" + characterName.toLowerCase() + ".json");
            File talentFile = talentResource.getFile();
            JsonNode root = objectMapper.readTree(talentFile);

            // 2. 필요한 재료 이름 추출 (e.g. "자유의 가르침")
            JsonNode materialNode = root.path("lvl2").path(1);
            if (materialNode.isMissingNode()) return Collections.emptyList();

            String materialName = materialNode.asText().replaceAll("\\s", "") + ".json";

            // 3. 해당 재료 JSON 열기
            Resource materialResource = new ClassPathResource("static/json/materials/" + materialName);
            File materialFile = materialResource.getFile();
            JsonNode materialJson = objectMapper.readTree(materialFile);

            // 4. 요일 정보 추출
            JsonNode daysNode = materialJson.path("daysOfWeek");
            if (daysNode == null || !daysNode.isArray()) return Collections.emptyList();

            List<String> result = new ArrayList<>();
            for (JsonNode day : daysNode) {
                result.add(day.asText());
            }
            return result;

        } catch (Exception e) {
            return Collections.emptyList(); // 오류 발생 시 빈 리스트
        }
    }
}
