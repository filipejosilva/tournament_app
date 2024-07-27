package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.NoUndefeatedPlayerException;
import com.filipejosilva.online.tournament.exception.TournamentNotFoundException;
import com.filipejosilva.online.tournament.model.Ranking;

public interface RankingService {

    Ranking getRankings(int tournamentID) throws TournamentNotFoundException;

    void undefeatedPlayers(int tournamentID) throws NoUndefeatedPlayerException;

}
