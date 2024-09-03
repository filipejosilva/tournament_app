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
import jakarta.persistence.criteria.CriteriaBuilder;
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

    /**
     * In this method we create the matches for that round
     * Pairing is not random, matches the other people around the same W/L ratio
     * @param round that the match belongs
     */
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

    /**
     * We create the first match of the tournament, all pairing are random
     * @param round that the match belongs
     */
    @Override
    public List<Match> createFirstMatch(Round round) {
            List<Match> newMatches = new ArrayList<>();
            List<Point> players = round.getTournament().getPoints();

            ArrayList<Integer> randomNumbers = getRandom(players.size());

            for(int i = 0; i< randomNumbers.size(); i+=2){
                Match match = new Match();
                match.setStatus("BATTLE");
                match.setRound(round);
                match.getPointp().add(players.get(randomNumbers.get(i)));

                if(i == (randomNumbers.size() -1) && players.size()%2 != 0){
                    match.getPointp().add(null);
                    match.setWinner(match.getPointp().get(0));
                    match.setStatus("FINISH");
                    newMatches.add(match);
                    break;
                }

                match.getPointp().add(players.get(randomNumbers.get(i+1)));
                newMatches.add(match);
            }

            return newMatches;
    }

    /**
     * Update the matches, select a winner and change status
     * @param match that the match belongs
     */
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

    /**
     * Check if any match is finished or not
     * @param round to get the current matches
     */
    @Override
    public void checkMatches(Round round) throws MatchNotFinishException {
        List<Match> checkMatches = round.getMatches();

        for(Match m : checkMatches){
            if(m.getStatus().equals("BATTLE")){
                throw new MatchNotFinishException();
            }
        }

    }

    /**
     * Random pairing for matches
     * @param size how many players theres is
     */
    public ArrayList<Integer> getRandom(int size){
        ArrayList<Integer> numbers = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < size; i++)
        {
            int newNumber = random.nextInt(size);

            if(i == 0){
                numbers.add(newNumber);
                continue;
            }
            while(numbers.contains(newNumber)){
                newNumber = random.nextInt(size);
            }
            numbers.add(newNumber);
        }

        return numbers;

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
