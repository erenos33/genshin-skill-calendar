package me.genshin.skillcalendar.service;

import me.genshin.skillcalendar.dto.CharacterDetailDto;

import java.util.List;

public interface CharacterService {
    List<String> getCharacterNames();
    CharacterDetailDto getCharacterDetail(String name);
}
