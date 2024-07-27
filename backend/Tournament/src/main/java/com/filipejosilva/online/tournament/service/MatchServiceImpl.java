package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.MatchNotFinishException;
import com.filipejosilva.online.tournament.exception.MatchNotFoundException;
import com.filipejosilva.online.tournament.exception.TournamentNotFoundException;
import com.filipejosilva.online.tournament.model.Match;
import com.filipejosilva.online.tournament.model.Point;
import com.filipejosilva.online.tournament.model.Ranking;
import com.filipejosilva.online.tournament.model.Round;
import com.filipejosilva.online.tournament.persistence.TransactionManager;
import com.filipejosilva.online.tournament.persistence.dao.MatchDao;
import com.filipejosilva.online.tournament.persistence.dao.PointDao;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class MatchServiceImpl implements MatchService{

    private TransactionManager tx;
    private MatchDao matchDao;
    private RankingService rankingService;
    private PointService pointService;

    @Override
    public Match get(int id) throws MatchNotFoundException {
        return Optional.ofNullable(matchDao.findById(id)).orElseThrow(MatchNotFoundException::new);
    }

    @Override
    public List<Match> create(Round round) {
        try {
            List<Match> newMatches = new ArrayList<>();
            Ranking ranking = rankingService.getRankings(round.getTournament().getId());

            for (int i = 0; i< ranking.getRanking().length; i+=2){
                Match match = new Match();
                match.setStatus("BATTLE");
                match.setRound(round);
                match.getPointp().add(ranking.getRanking()[i]);

                if(i == (ranking.getRanking().length -1) && ranking.getRanking().length%2 != 0){
                    match.getPointp().add(null);
                    match.setWinner(match.getPointp().get(0));
                    match.setStatus("FINISH");
                    newMatches.add(match);
                    break;
                }
                match.getPointp().add(ranking.getRanking()[i+1]);
                newMatches.add(match);

            }
            return newMatches;
        }catch (TournamentNotFoundException e){
            e.getMessage();
            return null;
        }

    }

    @Override
    public List<Match> createFirstMatch(Round round) {
            List<Match> newMatches = new ArrayList<>();
            List<Point> players = round.getTournament().getPoints();

            while(!players.isEmpty()){
                Random rand = new Random();
                //int random = rand.nextInt(players.size());
                int random = (int) (Math.random()* (players.size()-1));
                int fr = random;
                //Point first = players.get(random);

                Match match = new Match();
                match.setStatus("BATTLE");
                match.setRound(round);
                match.getPointp().add(players.get(random));


                //match.getPointp().add(first);
                //players.remove(match.getPointp().get(0));
                //players.remove(match.getPointp().size()-1);
                //players.remove(random);
                //players.remove(players.get(random));
                players.remove(fr);

                if(players.isEmpty()){
                    match.setWinner(match.getPointp().get(0));
                    match.setStatus("FINISH");
                    newMatches.add(match);
                    break;
                }
                //int secondRandom = rand.nextInt(players.size());
                int secondRandom = (int) (Math.random() * (players.size()-1));
                int sr = secondRandom;
                //Point second = players.get(secondRandom);
                match.getPointp().add(players.get(secondRandom));
                //match.getPointp().add(second);
                //players.remove(match.getPointp().size()-1);
                //players.remove(match.getPointp().get(1));
                //players.remove(secondRandom);
                //players.remove(players.get(secondRandom));
                players.remove(sr);

                newMatches.add(match);


            }
            return newMatches;
    }

    @Override
    public void updateMatch(Match match) {

        try {
            tx.beginWrite();
            matchDao.saveOrUpdate(match);
            tx.commit();

        }catch (PersistenceException e){
            e.getMessage();
            tx.rollback();
        }

    }

    @Override
    public void checkMatches(Round round) throws MatchNotFinishException {
        List<Match> checkMatches = round.getMatches();

        for(Match m : checkMatches){
            if(m.getStatus().equals("BATTLE")){
                throw new MatchNotFinishException();
            }
        }

    }

    /* Autowired */
    @Autowired
    public void setTx(TransactionManager tx) {
        this.tx = tx;
    }
    @Autowired
    public void setMatchDao(MatchDao matchDao) {
        this.matchDao = matchDao;
    }
    @Autowired
    public void setRankingService(RankingService rankingService){
        this.rankingService = rankingService;
    }
    @Autowired
    public void setPointService(PointService pointService) {
        this.pointService = pointService;
    }
}
