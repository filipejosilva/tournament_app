package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.DeckNotFoundException;
import com.filipejosilva.online.tournament.model.Deck;
import com.filipejosilva.online.tournament.persistence.TransactionManager;
import com.filipejosilva.online.tournament.persistence.dao.DeckDao;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeckServiceImpl implements DeckService{

    private TransactionManager tx;
    private DeckDao deckDao;

    /**
     * Put a new deck to the deck list
     * @param deck new deck
     */
    @Override
    public void add(Deck deck) {
        try {
            tx.beginWrite();
            deckDao.saveOrUpdate(deck);
            tx.commit();
        }catch (PersistenceException e){
            tx.rollback();
            e.getMessage();
        }
    }

    /**
     * remove deck from the list, not going to use for now
     */
    @Override
    public void remove(int id) {

        try {
            tx.beginWrite();

            deckDao.delete(id);

            tx.commit();
        }catch (PersistenceException e){
            tx.rollback();
            e.getMessage();
        }

    }

    @Override
    public Deck get(int id) throws DeckNotFoundException{

        Deck deck = Optional.ofNullable(deckDao.findById(id)).orElseThrow(DeckNotFoundException::new);
        return deck;
    }

    public List<Deck> getList(){
        List<Deck> list = deckDao.findAll();

        return list;

    }

    /* AutoWired */

    @Autowired
    public void setTx(TransactionManager tx) {
        this.tx = tx;
    }

    @Autowired
    public void setDeckDao(DeckDao deckDao) {
        this.deckDao = deckDao;
    }
}
