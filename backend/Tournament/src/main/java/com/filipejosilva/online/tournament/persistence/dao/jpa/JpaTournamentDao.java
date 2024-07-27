package com.filipejosilva.online.tournament.persistence.dao.jpa;

import com.filipejosilva.online.tournament.model.Tournament;
import com.filipejosilva.online.tournament.persistence.dao.TournamentDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaTournamentDao extends JpaGenericDao<Tournament> implements TournamentDao {

    public JpaTournamentDao(){
        super(Tournament.class);
    }
}
