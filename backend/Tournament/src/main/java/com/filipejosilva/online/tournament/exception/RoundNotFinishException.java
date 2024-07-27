package com.filipejosilva.online.tournament.exception;

import com.filipejosilva.online.tournament.errors.ErrorMessages;

public class RoundNotFinishException extends TournamentAppException{
    public RoundNotFinishException(){
        super(ErrorMessages.ROUND_NOT_FINISH);
    }
}
