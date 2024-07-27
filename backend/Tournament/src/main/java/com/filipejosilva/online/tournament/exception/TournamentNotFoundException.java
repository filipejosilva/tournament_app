package com.filipejosilva.online.tournament.exception;

import com.filipejosilva.online.tournament.errors.ErrorMessages;

public class TournamentNotFoundException extends TournamentAppException {
    public TournamentNotFoundException() {
        super(ErrorMessages.TOURNAMENT_NOT_FOUND);
    }
}
