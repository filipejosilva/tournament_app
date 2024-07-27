package com.filipejosilva.online.tournament.persistence.dao.jpa;

import com.filipejosilva.online.tournament.model.Point;
import com.filipejosilva.online.tournament.persistence.dao.PointDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPointDao extends JpaGenericDao<Point> implements PointDao {

    public JpaPointDao(){
        super(Point.class);
    }

}
