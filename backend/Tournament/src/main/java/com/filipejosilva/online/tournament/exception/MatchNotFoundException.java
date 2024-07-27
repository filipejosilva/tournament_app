package com.filipejosilva.online.tournament.exception;

import com.filipejosilva.online.tournament.errors.ErrorMessages;

public class MatchNotFoundException extends TournamentAppException{
    public MatchNotFoundException(){
        super(ErrorMessages.MATCH_NOT_FOUND);
    }
}
