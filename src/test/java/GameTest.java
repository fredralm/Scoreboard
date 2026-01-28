
import org.junit.jupiter.api.Test;

import scoreboard.Model.Game;
import scoreboard.Model.GameScore;
import scoreboard.Model.Team;

import java.util.Objects;

public class GameTest {
    Team mexico = new Team("Mexico");
    Team canada = new Team("Canada");

    @Test
    public void givenTeams_whenCreateNewGame_thenScoresShouldBeZeroAndIdShouldBeGenerated() {
        Game game = new Game(mexico.id(), canada.id());
        GameScore expectedScore = new GameScore(0, 0);

        assert game.id() != null;
        assert game.id().startsWith("game");
        assert Objects.equals(game.gameScore(), expectedScore);
    }

    @Test
    public void givenGame_whenHomeTeamScores_thenScoreShouldIncrementForHomeTeam() {
        Game game = new Game(mexico.id(), canada.id());
        Game updatedGame = game.homeTeamGoal();
        GameScore expectedScore = new GameScore(1, 0);

        assert Objects.equals(updatedGame.gameScore(), expectedScore);
    }

    @Test
    public void givenGame_whenAwayTeamScores_thenScoreShouldIncrementForAwayTeam() {
        Game game = new Game(mexico.id(), canada.id());
        Game updatedGame = game.awayTeamGoal();
        GameScore expectedScore = new GameScore(0, 1);

        assert Objects.equals(updatedGame.gameScore(), expectedScore);
    }

    @Test
    public void givenGame_whenSettingScore_thenScoreShouldEqualScoreInput() {
        Game game = new Game(mexico.id(), canada.id());
        Game updatedGame = game.setScore(2, 3);
        GameScore expectedScore = new GameScore(2, 3);

        assert Objects.equals(updatedGame.gameScore(), expectedScore);
    }
}
