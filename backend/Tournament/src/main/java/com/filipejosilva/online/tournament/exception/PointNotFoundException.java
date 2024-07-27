package com.filipejosilva.online.tournament.exception;

import com.filipejosilva.online.tournament.errors.ErrorMessages;

public class PointNotFoundException extends TournamentAppException {
    public PointNotFoundException() {
        super(ErrorMessages.POINT_NOT_FOUND);
    }
}