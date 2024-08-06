package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.PointNotFoundException;
import com.filipejosilva.online.tournament.model.Player;
import com.filipejosilva.online.tournament.model.Point;
import com.filipejosilva.online.tournament.model.Tournament;
import com.filipejosilva.online.tournament.persistence.TransactionManager;
import com.filipejosilva.online.tournament.persistence.dao.PointDao;
import com.filipejosilva.online.tournament.persistence.dao.TournamentDao;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PointServiceImpl implements PointService{
    private TransactionManager tx;
    private PointDao pointDao;
    private TournamentDao tournamentDao;

    @Override
    public Point get(int id) throws PointNotFoundException {
        return Optional.ofNullable(pointDao.findById(id)).orElseThrow(PointNotFoundException::new);
    }

    /**
     * We create the table Points, in this we get the score and owm to know the position of the players
     * @param tournament to create the point system
     */
    @Override
    public void createPointsForTournament(Tournament tournament) {
        /* if we update the tournament does it create points stuff for the table? trying it later) */
        try {

            tx.beginWrite();

            for(Player p : tournament.getPlayers()){

                Point point = new Point();
                point.setTournament(tournament);
                point.setPlayer(p);
                point.setScore(0);
                point.setOMW(0);
                tournament.getPoints().add(point);
            }

            tournament.setStatus("PLAY");
            tournamentDao.saveOrUpdate(tournament);

            tx.commit();

        }catch (PersistenceException e){
            e.getMessage();
            tx.rollback();
        }


    }

    @Override
    public void delete(int id) {

        try {
            tx.beginWrite();
            pointDao.delete(id);
            tx.commit();

        }catch (PersistenceException e){
            e.getMessage();
            tx.rollback();
        }

    }

    @Override
    public void update(Point point) throws PointNotFoundException{

        try {
            tx.beginWrite();
            Point updatePoint = get(point.getId());

            updatePoint.setScore(point.getScore());
            updatePoint.setOMW(point.getOMW());
            pointDao.saveOrUpdate(updatePoint);
            tx.commit();

        }catch (PersistenceException | PointNotFoundException e){
            e.getMessage();
            tx.rollback();
        }

    }

    public List<Point> pointTournament(int id){
        List<Point> pointTournament = pointDao.findAll();
        List<Point> pointId = new ArrayList<>();
        for(Point p : pointTournament){
            if(p.getTournament().getId() == id){
                pointId.add(p);
            }
        }

        return pointId;
    }

    /* AutoWired */
    @Autowired
    public void setTx(TransactionManager tx) {
        this.tx = tx;
    }
    @Autowired
    public void setPointDao(PointDao pointDao) {
        this.pointDao = pointDao;
    }
    @Autowired
    public void setTournamentDao(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }
}
