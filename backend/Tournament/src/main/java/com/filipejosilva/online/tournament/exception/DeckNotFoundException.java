package com.filipejosilva.online.tournament.exception;

import com.filipejosilva.online.tournament.errors.ErrorMessages;

public class DeckNotFoundException extends TournamentAppException{
    public DeckNotFoundException() {
        super(ErrorMessages.DECK_NOT_FOUND);
    }
}
