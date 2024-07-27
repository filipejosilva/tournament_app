package com.filipejosilva.online.tournament.command;

public class PointDto {

    private Integer id;
    private String name;
    private Integer score;
    private double OMW;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public double getOMW() {
        return OMW;
    }

    public void setOMW(double OMW) {
        this.OMW = OMW;
    }
}
