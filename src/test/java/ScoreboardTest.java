import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scoreboard.Model.Game;
import scoreboard.Model.GameScore;
import scoreboard.Model.Team;
import scoreboard.Requests.GamesSummaryRequest;
import scoreboard.Requests.GetGameRequest;
import scoreboard.Requests.GetTeamRequest;
import scoreboard.Requests.UpdateScoreRequest;
import scoreboard.Scoreboard;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ScoreboardTest {
    Scoreboard scoreboard = null;

    List<String> seedTeams = List.of("Mexico", "Canada", "Spain", "Brazil", "Germany", "France",
            "Uruguay", "Italy", "Argentina", "Australia"
    );

    String exampleGameId;

    // Clear scoreboard after tests to ensure equal conditions on each starting test
    @AfterEach
    public void clearScoreboard() {
        scoreboard.clearScoreboard();
    }

    // Add a set of test teams and games to scoreboard before tests
    @BeforeEach
    public void seedTestData() {
        seedTeams.forEach(teamName -> scoreboard.addTeam(teamName));
        Team germany = scoreboard.getTeam(new GetTeamRequest(null, "Germany"));
        Team france = scoreboard.getTeam(new GetTeamRequest(null, "France"));
        String game0Id = scoreboard.startGame(germany.id(), france.id());
        UpdateScoreRequest scoreUpdate0 = new UpdateScoreRequest(
                game0Id,
                new GameScore(2, 2)
        );
        scoreboard.updateScore(scoreUpdate0);
        exampleGameId = game0Id;

        Team argentina = scoreboard.getTeam(new GetTeamRequest(null, "Argentina"));
        Team australia = scoreboard.getTeam(new GetTeamRequest(null, "Australia"));
        String game1Id = scoreboard.startGame(argentina.id(), australia.id());
        UpdateScoreRequest scoreUpdate1 = new UpdateScoreRequest(
                game1Id,
                new GameScore(3, 1)
        );
        scoreboard.updateScore(scoreUpdate1);

        Team mexico = scoreboard.getTeam(new GetTeamRequest(null, "Mexico"));
        Team canada = scoreboard.getTeam(new GetTeamRequest(null, "Canada"));
        String game2Id = scoreboard.startGame(mexico.id(), canada.id());
        UpdateScoreRequest scoreUpdate2 = new UpdateScoreRequest(
                game2Id,
                new GameScore(0, 5)
        );
        scoreboard.updateScore(scoreUpdate2);

        Team spain = scoreboard.getTeam(new GetTeamRequest(null, "Spain"));
        Team brazil = scoreboard.getTeam(new GetTeamRequest(null, "Brazil"));
        String game3Id = scoreboard.startGame(spain.id(), brazil.id());
        UpdateScoreRequest scoreUpdate3 = new UpdateScoreRequest(
                game3Id,
                new GameScore(10, 2)
        );
        scoreboard.updateScore(scoreUpdate3);

        Team uruguay = scoreboard.getTeam(new GetTeamRequest(null, "Uruguay"));
        Team italy = scoreboard.getTeam(new GetTeamRequest(null, "Italy"));
        String game4Id = scoreboard.startGame(uruguay.id(), italy.id());
        UpdateScoreRequest scoreUpdate4 = new UpdateScoreRequest(
                game4Id,
                new GameScore(6, 6)
        );
        scoreboard.updateScore(scoreUpdate4);
    }

    // The board supports the operation "Start a game"
    @Test
    public void givenTeams_whenStartGame_thenGameShouldBeAddedToGamesSummary() {
        Team homeTeam = new Team("Mexico");
        Team awayTeam = new Team("Italy");
        GameScore expectedGameScore = new GameScore(0, 0);

        scoreboard.startGame(homeTeam.id(), awayTeam.id());
        List<Game> gameSummaries = scoreboard.getActiveGamesSummary();
        assert gameSummaries.size() == 6; // 5 seed games + 1 new game

        Game game = gameSummaries.getFirst(); // First should be the newly added game, due to ordering by most recently added
        assert game.id().startsWith("game");
        assert Objects.equals(game.homeTeamId(), homeTeam.id());
        assert Objects.equals(game.awayTeamId(), awayTeam.id());
        assert Objects.equals(game.gameScore(), expectedGameScore);
    }

    // The board supports the operation "Finish a game"
    @Test
    public void givenGameId_whenFinishGame_thenGameShouldBeRemovedFromGamesSummary() {
        Game exampleGame = scoreboard.getGame(new GetGameRequest(exampleGameId));
        List<Game> gameSummaries = scoreboard.getActiveGamesSummary();
        assert gameSummaries.size() == 5; // 5 seed games
        assert gameSummaries.contains(exampleGame);

        scoreboard.finishGame(exampleGameId);

        List<Game> gameSummariesAfterUpdate = scoreboard.getActiveGamesSummary();
        assert gameSummariesAfterUpdate.size() == 4; // 5 seed games - 1 finished game
        assert !gameSummariesAfterUpdate.contains(exampleGame);
    }

    // The board supports the operation "Update a score"
    @Test
    public void givenGameIdAndScore_whenSetScore_thenGameShouldHaveUpdatedScoreInGamesSummary() {
        Game exampleGame = scoreboard.getGame(new GetGameRequest(exampleGameId));
        assert Objects.equals(exampleGame.gameScore(), new GameScore(2, 2));

        GameScore newScore = new GameScore(4, 2);
        scoreboard.updateScore(new UpdateScoreRequest(exampleGameId, newScore));

        Game exampleGameAfterUpdate = scoreboard.getGame(new GetGameRequest(exampleGameId));
        assert Objects.equals(exampleGameAfterUpdate.gameScore(), newScore);
        List<Game> gameSummaries = scoreboard.getActiveGamesSummary();
        Game exampleGameFromSummaries = gameSummaries
                .stream()
                .filter(game -> Objects.equals(game.id(), exampleGameId))
                .toList()
                .getFirst();
        assert Objects.equals(exampleGameFromSummaries.gameScore(), newScore);
    }

    // The board supports the operation "Get a summary of games by total score"
    @Test
    public void givenGameScore_whenFilteringGameSummaries_thenGameSummaryShouldContainGamesWithGameScore() {
        GameScore gameScoreForFilter = new GameScore(10, 2);
        GamesSummaryRequest request = new GamesSummaryRequest(null, null, gameScoreForFilter);

        List<Game> matchingGames = scoreboard.getActiveGamesSummary(request);
        assert matchingGames.size() == 1; // Only one seed game has score 10-2

        Team expectedHomeTeam = scoreboard.getTeam(new GetTeamRequest(null, "Spain"));
        Team expectedAwayTeam = scoreboard.getTeam(new GetTeamRequest(null, "Brazil"));
        GameScore expectedGameScore = new GameScore(10, 2);
        Game matchingGame = matchingGames.getFirst();
        assert Objects.equals(matchingGame.homeTeamId(), expectedHomeTeam.id());
        assert Objects.equals(matchingGame.awayTeamId(), expectedAwayTeam.id());
        assert Objects.equals(matchingGame.gameScore(), expectedGameScore);

        // If multiple games have same score all matching games are returned
        scoreboard.updateScore(new UpdateScoreRequest(exampleGameId, gameScoreForFilter));
        List<Game> matchingGamesAfterUpdate = scoreboard.getActiveGamesSummary(request);
        assert matchingGamesAfterUpdate.size() == 2; // Seed game + updated game
    }

    // The summary of games is ordered by most recently added
    @Test
    public void givenScoreboard_whenGetActiveGameSummaries_thenGameSummaryShouldContainActiveGamesOrderedByTheMostRecentlyAdded() {
        List<Game> gameSummaries = scoreboard.getActiveGamesSummary();
        List<Game> sortedGameSummaries = gameSummaries.stream()
                .sorted(Comparator.comparing(Game::startTime).reversed())
                .toList();
        assert gameSummaries.equals(sortedGameSummaries);
    }
}
