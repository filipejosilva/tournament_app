package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.MatchNotFinishException;
import com.filipejosilva.online.tournament.exception.RoundNotFinishException;
import com.filipejosilva.online.tournament.exception.RoundNotFoundException;
import com.filipejosilva.online.tournament.model.Match;
import com.filipejosilva.online.tournament.model.Point;
import com.filipejosilva.online.tournament.model.Round;
import com.filipejosilva.online.tournament.model.Tournament;
import com.filipejosilva.online.tournament.persistence.TransactionManager;
import com.filipejosilva.online.tournament.persistence.dao.PointDao;
import com.filipejosilva.online.tournament.persistence.dao.RoundDao;
import com.filipejosilva.online.tournament.persistence.dao.TournamentDao;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoundServiceImpl implements RoundService{

    private TransactionManager tx;
    private RoundDao roundDao;
    private MatchService matchService;
    private TournamentDao tournamentDao;
    private PointDao pointDao;
    private PointService pointService;

    @Override
    public Round get(int id) throws RoundNotFoundException {
        return Optional.ofNullable(roundDao.findById(id)).orElseThrow(RoundNotFoundException::new);
    }

    /**
     * Create rounds and matches for the tournament
     * @param tournament
     */
    @Override
    public void createRound(Tournament tournament) {
        /* we call this from tournament when we need to create new rounds ?*/

        try {

            tx.beginWrite();
            Round newRound = new Round();
            newRound.setTournament(tournament);
            newRound.setStatus("BATTLE");

            List<Match> matches = matchService.create(newRound);
            for(Match m : matches){
                newRound.getMatches().add(m);
            }

            tournament.getRounds().add(newRound);

            tournamentDao.saveOrUpdate(tournament);

            tx.commit();

        }catch (PersistenceException e){
            e.getMessage();
            tx.rollback();
        }

    }

    /**
     * Create First round and matches, for the tournament it will randomize the matchup
     * @param tournament
     */
    @Override
    public void createFirstRound(Tournament tournament) {
        /* we call this from tournament when we need to create new rounds ?*/

        try {

            tx.beginWrite();
            Round newRound = new Round();
            newRound.setTournament(tournament);
            newRound.setStatus("BATTLE");

            List<Match> matches = matchService.createFirstMatch(newRound);
            for(Match m : matches){
                newRound.getMatches().add(m);
            }

            tournament.getRounds().add(newRound);


            tournamentDao.saveOrUpdate(tournament);

            tx.commit();

        }catch (PersistenceException e){
            e.getMessage();
            tx.rollback();
        }

    }

    /**
     * check if the round is still in battle status
     * @param round
     * @throws RoundNotFinishException
     */
    public void checkRound(Round round) throws RoundNotFinishException{
        if(round.getStatus().equals("BATTLE")){
            throw new RoundNotFinishException();
        }

    }

    /**
     * In this method we change the round status so its possible to start new rounds.
     * By updating the round status it will update the points and OMW(need to find or create a formula for this)
     * @param round round that is going to be updated
     */
    @Override
    public void updateRound(Round round){
        /*Update score when round is updated to finish */
        try {
            tx.beginWrite();
            roundDao.saveOrUpdate(round);

            matchService.checkMatches(round);
            List<Match> updatePoints = round.getMatches();

            for(Match m : updatePoints){
                /* make a service to return something when theres no winner */
                Point pointUpdate = m.getWinner();

                pointUpdate.setScore(pointUpdate.getScore()+3);
                pointDao.saveOrUpdate(pointUpdate);
            }


            /* we can just use tournament.getpoints()... */
            //List<Point> tournamentPoints = pointService.pointTournament(round.getTournament().getId());
            List<Point> tournamentPoints = round.getTournament().getPoints();

            /*Update the OMW added pointTournament method and this until save closed */
            for(Point p : tournamentPoints){
                int matchesWin = 0;
                double divisor = p.getMatches().size() * p.getMatches().size();

                for(Match m : p.getMatches()){

                    /* something is off in this logic */
                    for(Point opponent : m.getPointp()){
                        if(!opponent.equals(p)){

                            if(opponent.getScore() != 0){
                                matchesWin += (opponent.getScore() /3);
                            }

                        }
                    }

                }

                /* converting to double*/
                double matchesWon = matchesWin;

                double OMW = (matchesWon/divisor)*100;
                p.setOMW(OMW);

                pointDao.saveOrUpdate(p);
            }

            round.setStatus("CLOSED");

            tx.commit();


        }catch (PersistenceException | MatchNotFinishException  e){
            e.getMessage();
            tx.rollback();
        }

    }

    public void updatePoints(Round round){
        try {
            tx.beginWrite();
            roundDao.saveOrUpdate(round);

            matchService.checkMatches(round);
            List<Match> updatePoints = round.getMatches();

            for(Match m : updatePoints){
                /* make a service to return something when theres no winner */
                Point pointUpdate = m.getWinner();

                pointUpdate.setScore(pointUpdate.getScore()+3);
                pointDao.saveOrUpdate(pointUpdate);
            }
            tx.commit();
        }catch (PersistenceException | MatchNotFinishException  e){
            e.getMessage();
            tx.rollback();
        }
    }

    public void updateOMW(Round round){
        try {
            tx.beginWrite();
            /* we can just use tournament.getpoints()... */
            //List<Point> tournamentPoints = pointService.pointTournament(round.getTournament().getId());
            List<Point> tournamentPoints = round.getTournament().getPoints();

            /*Update the OMW */
            for(Point p : tournamentPoints){
                int matchesWin = 0;
                double divisor = p.getMatches().size() * p.getMatches().size();

                for(Match m : p.getMatches()){

                    /* something is off in this logic */
                    for(Point opponent : m.getPointp()){
                        if(!opponent.equals(p)){
                            if(opponent.getScore() != 0){
                                matchesWin += (opponent.getScore() /3);
                            }

                        }
                    }

                }

                /* converting to double*/
                double matchesWon = matchesWin;

                double OMW = (matchesWon/divisor)*100;
                p.setOMW(OMW);

                pointDao.saveOrUpdate(p);
            }

            round.setStatus("CLOSED");

            tx.commit();
        }catch (PersistenceException e){
            e.getMessage();
            tx.rollback();
        }
    }

    @Override
    public List<Round> allTournamentRounds(Tournament tournament) {
        return tournament.getRounds();
    }


    /* Autowired */
    @Autowired
    public void setTx(TransactionManager tx) {
        this.tx = tx;
    }
    @Autowired
    public void setRoundDao(RoundDao roundDao) {
        this.roundDao = roundDao;
    }
    @Autowired
    public void setMatchService(MatchService matchService) {
        this.matchService = matchService;
    }
    @Autowired
    public void setTournamentDao(TournamentDao tournamentDao){
        this.tournamentDao = tournamentDao;
    }
    @Autowired
    public void setPointDao(PointDao pointDao) {
        this.pointDao = pointDao;
    }
    @Autowired
    public void setPointService(PointService pointService) {
        this.pointService = pointService;
    }
}
