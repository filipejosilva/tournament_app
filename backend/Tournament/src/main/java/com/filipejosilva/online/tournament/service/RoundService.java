package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.MatchNotFinishException;
import com.filipejosilva.online.tournament.exception.RoundNotFinishException;
import com.filipejosilva.online.tournament.exception.RoundNotFoundException;
import com.filipejosilva.online.tournament.model.Round;
import com.filipejosilva.online.tournament.model.Tournament;

import java.util.List;

public interface RoundService {

    Round get(int id) throws RoundNotFoundException;
    /* see if we give object or id*/
    void createRound(Tournament tournament);
    void createFirstRound(Tournament tournament);
    List<Round> allTournamentRounds(Tournament tournament);

    void checkRound(Round round) throws RoundNotFinishException;
    void updateRound(Round round);

}
