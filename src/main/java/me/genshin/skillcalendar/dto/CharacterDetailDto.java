package me.genshin.skillcalendar.dto;

import lombok.Data;

@Data
public class CharacterDetailDto {

    private String name;
    private String title;
    private String gender;
    private String weaponText;
    private String description;
    private String affiliation;
    private int rarity;
    private String constellation;
    private String birthday;
    private String elementText;

}
