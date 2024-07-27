package com.filipejosilva.online.tournament.command;

import java.util.List;

public class MatchDto {
    private Integer id;
    private String Status;
    private List<PointDto> players;
    //Later may change just to a string or pointDto
    private PointDto winner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<PointDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<PointDto> players) {
        this.players = players;
    }

    public PointDto getWinner() {
        return winner;
    }

    public void setWinner(PointDto winner) {
        this.winner = winner;
    }
}
