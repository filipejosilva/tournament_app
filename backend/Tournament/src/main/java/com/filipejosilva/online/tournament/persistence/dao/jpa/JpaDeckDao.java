package com.filipejosilva.online.tournament.persistence.dao.jpa;

import com.filipejosilva.online.tournament.model.Deck;
import com.filipejosilva.online.tournament.persistence.dao.DeckDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaDeckDao extends JpaGenericDao<Deck>implements DeckDao {

    public JpaDeckDao() {
        super(Deck.class);
    }
}
