package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.PointNotFoundException;
import com.filipejosilva.online.tournament.model.Point;
import com.filipejosilva.online.tournament.model.Tournament;

import java.util.List;

public interface PointService {
    Point get(int id) throws PointNotFoundException;
    void createPointsForTournament(Tournament tournament);
    /*we shouldnt be able to delete Points only when tournament is deleted */
    void delete(int id);

    void update(Point point) throws PointNotFoundException;
    /* create a method to get only specif points for that tournament */
    List<Point> pointTournament(int id);
}
