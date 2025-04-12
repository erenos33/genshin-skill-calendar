package me.genshin.skillcalendar.service.impl;

import lombok.RequiredArgsConstructor;
import me.genshin.skillcalendar.dto.CharacterDetailDto;
import me.genshin.skillcalendar.service.CharacterService;
import me.genshin.skillcalendar.util.GenshinApiClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final GenshinApiClient genshinApiClient;

    @Override
    public List<String> getCharacterNames() {
        return genshinApiClient.getAllCharacterNames();
    }

    @Override
    public CharacterDetailDto getCharacterDetail(String name) {
        return genshinApiClient.getCharacterDetail(name);
    }
}
