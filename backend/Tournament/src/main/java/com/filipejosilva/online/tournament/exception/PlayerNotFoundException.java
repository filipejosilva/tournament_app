package com.filipejosilva.online.tournament.exception;

import com.filipejosilva.online.tournament.errors.ErrorMessages;
import com.filipejosilva.online.tournament.model.Tournament;

public class PlayerNotFoundException extends TournamentAppException{

    public PlayerNotFoundException(){
        super(ErrorMessages.PLAYER_NOT_FOUND);
    }
}
