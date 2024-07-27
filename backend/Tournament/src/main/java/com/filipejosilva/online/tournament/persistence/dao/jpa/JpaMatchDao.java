package com.filipejosilva.online.tournament.persistence.dao.jpa;

import com.filipejosilva.online.tournament.model.Match;
import com.filipejosilva.online.tournament.persistence.dao.MatchDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMatchDao extends JpaGenericDao<Match>implements MatchDao {

    public JpaMatchDao() {
        super(Match.class);
    }
}