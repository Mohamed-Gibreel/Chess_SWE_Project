package results;

import javafx.fxml.FXML;
import util.jpa.GenericJpaDao;
import javax.persistence.Persistence;
import java.util.List;


public class TimetableDao extends GenericJpaDao<Timetable> {

    @FXML
    private static TimetableDao instance;

    private TimetableDao() {
        super(Timetable.class);
    }

    public static TimetableDao getInstance() {
        if (instance == null) {
            instance = new TimetableDao();
            instance.setEntityManager(Persistence.createEntityManagerFactory("persistence-unit").createEntityManager());
        }
        return instance;
    }

    public List<Timetable> findTimetable(){
            return entityManager.createQuery("SELECT t from Timetable t", Timetable.class)
                    .getResultList();
    }

}
