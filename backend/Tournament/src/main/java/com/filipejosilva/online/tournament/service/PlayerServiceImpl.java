package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.DeckNotFoundException;
import com.filipejosilva.online.tournament.exception.PlayerNotFoundException;
import com.filipejosilva.online.tournament.model.Deck;
import com.filipejosilva.online.tournament.model.Player;
import com.filipejosilva.online.tournament.persistence.TransactionManager;
import com.filipejosilva.online.tournament.persistence.dao.PlayerDao;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService{

    private TransactionManager tx;
    private PlayerDao playerDao;
    private DeckService deckService;


    /**
     * Fetch the player with that id
     * @throws PlayerNotFoundException when that players does not exist
     */
    @Override
    public Player get(int id) throws PlayerNotFoundException {
        return Optional.ofNullable(playerDao.findById(id)).orElseThrow(PlayerNotFoundException::new);
    }

    /**
     * List of the player in the DB
     */
    @Override
    public List<Player> list() {
        return playerDao.findAll();
    }

    /**
     * Update player information
     */
    @Override
    public void updatePlayer(Player player) throws DeckNotFoundException {

        try {
            tx.beginWrite();

            playerDao.saveOrUpdate(player);

            tx.commit();
        }catch (PersistenceException e){
            tx.rollback();
        }

    }

    /**
     * Add deck to the player list
     */
    @Override
    public void addDeck(int id, int deck){

        try {
            tx.beginWrite();

            Deck newDeck = deckService.get(deck);

            Player player = get(id);

            assert player != null;

            if(player.getDecks().contains(newDeck)){
                return;
            }
            player.getDecks().add(newDeck);
            playerDao.saveOrUpdate(player);
            tx.commit();

        }catch (PersistenceException  | DeckNotFoundException | PlayerNotFoundException e){
            e.getMessage();
            tx.rollback();
        }

    }

    /**
     * remove deck from the player list
     */
    @Override
    public void removeDeck(int playerId, int deckId) throws DeckNotFoundException{

        try {
            tx.beginWrite();
            Deck newDeck = deckService.get(deckId);
            Player player = get(playerId);

            assert player != null;

            if(player.getDecks().contains(newDeck)){
                player.getDecks().remove(newDeck);
                playerDao.saveOrUpdate(player);
            }

            tx.commit();
        }catch (PersistenceException | DeckNotFoundException | PlayerNotFoundException e){
            tx.rollback();
        }

    }

    /**
     * remove player from the db
     */
    @Override
    public void removePlayer(int id) throws PlayerNotFoundException{

        try {
            tx.beginWrite();
            get(id);
            playerDao.delete(id);

            tx.commit();
        }catch (PersistenceException e){
            tx.rollback();
        }
    }

    /**
     * Add player from the db
     */
    @Override
    public void addPlayer(Player player){
        try {
            tx.beginWrite();
            playerDao.saveOrUpdate(player);

            tx.commit();
        }catch (PersistenceException e){
            tx.rollback();
        }
    }

    /* Auto wired */
    @Autowired
    public void setTx(TransactionManager tx) {
        this.tx = tx;
    }
    @Autowired
    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }
    @Autowired
    public void setDeckService(DeckService deckService) {
        this.deckService = deckService;
    }
}
