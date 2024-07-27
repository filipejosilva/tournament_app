package com.filipejosilva.online.tournament.model;


import java.io.Serializable;
import java.util.List;

/**
 * Ranking the players for the tournament
 * we from a list we put the points in array and then sort them
 */

public class Ranking implements Serializable {
    /* It may be not needed for now its here if needed */
    private Point[] ranking;

    public Point[] getRanking() {
        return ranking;
    }

    public Ranking(List<Point> list){
        setRanking(list);
    }

    public void setRanking(List<Point>  ranking) {
        /* Set the size of the array */
        this.ranking = new Point[ranking.size()];

        /* Add the list to the array */
        for(int i = 0; i < ranking.size(); i++){
            this.ranking[i] = ranking.get(i);
        }
        /* Sort */
        sortRanking();

    }

    /* puts the score in the correct position using insertion */
    public void sortRanking(){

        for(int i = 1; i < ranking.length; i++){
            int higherPoint = ranking[i].getScore();
            double higherOMW = ranking[i].getOMW();
            Point point = ranking[i];

            int j;

            for( j = i-1; j >= 0; j--){

                ranking[j+1] = ranking[j];
                if (ranking[j].getScore() > higherPoint) {
                    break;
                }else if(ranking[j].getScore() == higherPoint && ranking[j].getOMW() >= higherOMW){
                    break;
                }
            }

            ranking[j+1] = point;

        }

    }


}

