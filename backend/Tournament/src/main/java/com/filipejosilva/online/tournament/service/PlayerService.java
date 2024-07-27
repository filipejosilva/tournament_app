package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.DeckNotFoundException;
import com.filipejosilva.online.tournament.exception.PlayerNotFoundException;
import com.filipejosilva.online.tournament.model.Deck;
import com.filipejosilva.online.tournament.model.Player;

import java.util.List;

public interface PlayerService {

    Player get(int id) throws PlayerNotFoundException;

    List<Player> list();

    void updatePlayer(Player player) throws DeckNotFoundException;

    void addDeck(int id, int deck) throws DeckNotFoundException;

    void removeDeck(int playerId, int deckId) throws DeckNotFoundException;

    void removePlayer(int id) throws PlayerNotFoundException;

    void addPlayer(Player player);
}
