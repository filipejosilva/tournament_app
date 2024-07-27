package com.filipejosilva.online.tournament.exception;

import com.filipejosilva.online.tournament.errors.ErrorMessages;

public class RoundNotFoundException extends TournamentAppException{
    public RoundNotFoundException(){
        super(ErrorMessages.ROUND_NOT_FOUND);
    }
}
