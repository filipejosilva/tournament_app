package com.filipejosilva.online.tournament.exception;

import com.filipejosilva.online.tournament.errors.ErrorMessages;

public class RegisterErrorException extends TournamentAppException{
    public RegisterErrorException(){
        super(ErrorMessages.REGISTER_ERROR);
    }
}
