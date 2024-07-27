package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.DeckNotFoundException;
import com.filipejosilva.online.tournament.model.Deck;

import java.util.List;

public interface DeckService {
    Deck get(int id) throws DeckNotFoundException;
    void add(Deck deck);
    void remove(int id);
    List<Deck> getList();
}
