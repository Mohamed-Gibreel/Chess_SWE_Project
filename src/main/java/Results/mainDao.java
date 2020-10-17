package results;

import javafx.fxml.FXML;
import util.jpa.GenericJpaDao;

import javax.persistence.Persistence;
import java.util.List;


public class mainDao extends GenericJpaDao<Main> {

    @FXML
    private static mainDao instance;

    private mainDao() {
        super(Main.class);
    }

    public static mainDao getInstance() {
        if (instance == null) {
            instance = new mainDao();
            instance.setEntityManager(Persistence.createEntityManagerFactory("persistence-unit").createEntityManager());
        }
        return instance;
    }

    public List<Main> findAll(){
        return entityManager.createQuery("SELECT m from Main m", Main.class)
                .getResultList();
    }

}
