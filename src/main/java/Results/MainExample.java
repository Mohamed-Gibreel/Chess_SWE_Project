package results;

public class MainExample {

    public static void main(String[] args) {
        mainDao MainDao = mainDao.getInstance();
        System.out.println(MainDao.findAll());

    }

}