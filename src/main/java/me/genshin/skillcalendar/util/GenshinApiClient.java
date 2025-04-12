package me.genshin.skillcalendar.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.genshin.skillcalendar.dto.CharacterDetailDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenshinApiClient {

    private final ObjectMapper objectMapper;

    // ✅ 캐릭터 이름 전체 가져오기
    public List<String> getAllCharacterNames() {
        File folder = new File("src/main/resources/static/json/talents");
        String[] files = folder.list((dir, name) -> name.endsWith(".json"));

        if (files == null) return List.of();

        return Arrays.stream(files)
                .map(name -> name.replace(".json", ""))
                .sorted()
                .collect(Collectors.toList());
    }

    // ✅ 캐릭터 상세 정보 가져오기
    public CharacterDetailDto getCharacterDetail(String name) {
        try {
            Path file = new ClassPathResource("static/json/characters/" + name + ".json").getFile().toPath();
            return objectMapper.readValue(Files.newInputStream(file), CharacterDetailDto.class);
        } catch (IOException e) {
            throw new RuntimeException("캐릭터 상세 불러오기 실패: " + name, e);
        }
    }
}
