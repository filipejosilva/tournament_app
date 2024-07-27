package com.filipejosilva.online.tournament.exception;

import com.filipejosilva.online.tournament.errors.ErrorMessages;

public class MatchNotFinishException extends TournamentAppException{
    public MatchNotFinishException(){
        super(ErrorMessages.MATCH_NOT_FINISH);
    }
}
