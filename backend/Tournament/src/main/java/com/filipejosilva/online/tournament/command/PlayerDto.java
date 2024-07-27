package com.filipejosilva.online.tournament.command;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class PlayerDto {

    private Integer id;
    private String name;
    private String mainDeck;
    private List<DeckDto> Other;
    private Integer tournaments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(String mainDeck) {
        this.mainDeck = mainDeck;
    }

    public List<DeckDto> getOther() {
        return Other;
    }

    public void setOther(List<DeckDto> other) {
        Other = other;
    }

    public Integer getTournaments() {
        return tournaments;
    }

    public void setTournaments(Integer tournaments) {
        this.tournaments = tournaments;
    }
}
