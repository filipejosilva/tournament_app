package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.NoUndefeatedPlayerException;
import com.filipejosilva.online.tournament.exception.TournamentNotFoundException;
import com.filipejosilva.online.tournament.model.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankingServiceImpl implements RankingService{

    private TournamentService tournamentService;

    /**
     * Create ranking object to know the rankings
     * @param tournamentID
     * @return
     */
    @Override
    public Ranking getRankings(int tournamentID) throws TournamentNotFoundException{
        try {
            Ranking ranking = new Ranking(tournamentService.get(tournamentID).getPoints());
            return ranking;
        }catch (TournamentNotFoundException e){
            e.getMessage();
            throw new TournamentNotFoundException();
        }

    }

    /**
     * We check if the there any undefeated player if theres no thrown an exception so we know we need more rounds
     * @param tournamentID current tournament
     * @throws NoUndefeatedPlayerException
     */
    @Override
    public void undefeatedPlayers(int tournamentID) throws NoUndefeatedPlayerException {
        try {
            Ranking ranking = new Ranking(tournamentService.get(tournamentID).getPoints());
            if(ranking.getRanking()[0].getScore() == ranking.getRanking()[1].getScore()){
                throw new NoUndefeatedPlayerException();
            }
        }catch (TournamentNotFoundException e){
            e.getMessage();
        }
    }


    @Autowired
    public void setTournamentDao(TournamentService tournamentService){
        this.tournamentService = tournamentService;
    }

}
