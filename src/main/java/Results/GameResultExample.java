package Results;


import java.time.Duration;

/**
 * A class that sends an example of a player that won the game to our database.
 */
public class GameResultExample {
    /**
     * Contains the players information.
     * @param args argument.
     */
    public static void main(String[] args) {
        GameResultDao gameResultDao = GameResultDao.getInstance();
        GameResult gameResult = GameResult.builder()
                .player("test")
                .solved(true)
                .steps(36)
                .duration(Duration.ofMinutes(3))
                .build();
        gameResultDao.persist(gameResult);
        System.out.println(gameResult);
        System.out.println(gameResultDao.findBest(5));
    }

}
