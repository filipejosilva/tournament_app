package com.filipejosilva.online.tournament.exception;

import com.filipejosilva.online.tournament.errors.ErrorMessages;

public class NoUndefeatedPlayerException extends TournamentAppException{
    public NoUndefeatedPlayerException(){
        super(ErrorMessages.NO_UNDEFEATED_PLAYER);
    }
}
