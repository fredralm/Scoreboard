import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scoreboard.Scoreboard;

public class ScoreboardTest {
    Scoreboard scoreboard = null;

    // Clear scoreboard after tests to ensure equal conditions on each starting test
    @AfterEach
    public void clearScoreboard() {
    }

    // Add a set of test teams and games to scoreboard before tests
    @BeforeEach
    public void seedTestData() {
    }

    // The board supports the operation "Start a game"
    @Test
    public void givenTeams_whenStartGame_thenGameShouldBeAddedToGamesSummary() {
    }

    // The board supports the operation "Finish a game"
    @Test
    public void givenGameId_whenFinishGame_thenGameShouldBeRemovedFromGamesSummary() {
    }

    // The board supports the operation "Update a score"
    @Test
    public void givenGameIdAndScore_whenSetScore_thenGameShouldHaveUpdatedScoreInGamesSummary() {

    }

    // The board supports the operation "Get a summary of games by total score"
    @Test
    public void givenScoreboard_whenGetGamesSummary_thenGameSummaryShouldContainGamesOrderedByTheMostRecentlyAdded() {

    }
}
