package com.filipejosilva.online.tournament.persistence.dao.jpa;

import com.filipejosilva.online.tournament.model.Round;
import com.filipejosilva.online.tournament.persistence.dao.RoundDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRoundDao extends JpaGenericDao<Round> implements RoundDao {

    public JpaRoundDao(){
        super(Round.class);
    }
}
