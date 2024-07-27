package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.MatchNotFinishException;
import com.filipejosilva.online.tournament.exception.MatchNotFoundException;
import com.filipejosilva.online.tournament.model.Match;
import com.filipejosilva.online.tournament.model.Round;

import java.util.List;

public interface MatchService {

    Match get(int id) throws MatchNotFoundException;
    List<Match> create(Round round);
    List<Match> createFirstMatch(Round round);
    void updateMatch(Match match);
    void checkMatches(Round round) throws MatchNotFinishException;

}
