package com.filipejosilva.online.tournament.exception;

import com.filipejosilva.online.tournament.errors.ErrorMessages;

public class StatusUnavailableException extends TournamentAppException{
    public StatusUnavailableException(){
        super(ErrorMessages.STATUS_UNAVAILABLE);
    }
}
