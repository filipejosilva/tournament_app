package com.filipejosilva.online.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches")
public class Match implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@JsonIgnore
    @ManyToMany(
            cascade = {CascadeType.DETACH},
            fetch = FetchType.EAGER
            /*mappedBy = "matchp"*/
    )
    private List<Point> pointp;
    /* if points list doest work go back to this delete many to many on ppoints and match onetoone it doesnt work
    private Player p1;

    private Player p2;*/

    /* BATTLE/FINISH - MAYbe no draw the tcg is almost impossible to drawn*/
    private String status;

    @ManyToOne
    private Round round;

    @ManyToOne
    private Point winner;

    public Match(){
        this.pointp = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Point getWinner() {
        return winner;
    }

    public void setWinner(Point winner) {
        this.winner = winner;
    }

    public List<Point> getPointp() {
        return pointp;
    }

    public void setPointp(List<Point> pointp) {
        this.pointp = pointp;
    }
}
