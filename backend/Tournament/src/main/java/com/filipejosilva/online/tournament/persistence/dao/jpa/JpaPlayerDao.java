package com.filipejosilva.online.tournament.persistence.dao.jpa;

import com.filipejosilva.online.tournament.model.Player;
import com.filipejosilva.online.tournament.persistence.dao.PlayerDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPlayerDao extends JpaGenericDao<Player> implements PlayerDao {

    public JpaPlayerDao(){
        super(Player.class);
    }
}
