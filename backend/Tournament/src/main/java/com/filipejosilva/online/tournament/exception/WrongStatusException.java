package com.filipejosilva.online.tournament.exception;

import com.filipejosilva.online.tournament.errors.ErrorMessages;

public class WrongStatusException extends TournamentAppException{
    public WrongStatusException(){
        super(ErrorMessages.WRONG_STATUS);
    }
}
