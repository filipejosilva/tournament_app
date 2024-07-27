package com.filipejosilva.online.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "points")
public class Point implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Player player;

    @ManyToOne
    private Tournament tournament;

    private Integer score;

    private double OMW;

    /*fixing match players*/
    @JsonIgnore
    @ManyToMany(
            cascade = {CascadeType.DETACH},
            fetch = FetchType.EAGER,
            mappedBy = "pointp" /* was without mappedby */
    )
    private List<Match> matchp;

    @JsonIgnore
    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            mappedBy = "winner",
            fetch = FetchType.EAGER
    )
    private List<Match> matches;

    public Point(){
        this.matches= new ArrayList<>();
        this.matchp = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public double getOMW() {
        return OMW;
    }

    public void setOMW(double OMW) {
        this.OMW = OMW;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<Match> getMatchp() {
        return matchp;
    }

    public void setMatchp(List<Match> matchp) {
        this.matchp = matchp;
    }
}
