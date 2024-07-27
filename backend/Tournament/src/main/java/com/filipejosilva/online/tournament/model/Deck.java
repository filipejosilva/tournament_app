package com.filipejosilva.online.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "decks")
public class Deck implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String leader;

    //remove json ignore after having a dto on all models
    @JsonIgnore
    @ManyToMany(
            //cascade = {CascadeType.DETACH},
            fetch = FetchType.LAZY,
            mappedBy = "decks"
    )
    private List<Player> players;

    public Deck(){
        this.players = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
