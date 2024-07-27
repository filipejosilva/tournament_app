package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.RegisterErrorException;
import com.filipejosilva.online.tournament.exception.StatusUnavailableException;
import com.filipejosilva.online.tournament.exception.TournamentNotFoundException;
import com.filipejosilva.online.tournament.model.Tournament;

import java.util.List;

public interface TournamentService {

    Tournament get(int id) throws TournamentNotFoundException;

    List<Tournament> list();

    void updateTournament(Tournament tournament);

    void changeStatus(int id, String status) throws TournamentNotFoundException, StatusUnavailableException;

    void newTournament(Tournament tournament);

    void deleteTournament(int id);

    void createRounds(Tournament tournament);
    void addPlayers(int tid, int pid) throws RegisterErrorException;
}
