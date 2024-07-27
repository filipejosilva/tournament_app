package com.filipejosilva.online.tournament.command;

public class RankingDto {

    private String name;
    private int points;
    private double OMW;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getOMW() {
        return OMW;
    }

    public void setOMW(double OMW) {
        this.OMW = OMW;
    }
}
