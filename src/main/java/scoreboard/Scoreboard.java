package scoreboard;

import scoreboard.Model.Game;
import scoreboard.Model.Team;
import scoreboard.Requests.*;

import java.util.List;

public interface Scoreboard {
    /**
     * Add team to scoreboard team storage.
     * Generates a unique team id and adds team to storage.
     *
     * @param countryName Name of team to be added
     */
    void addTeam(String countryName);

    /**
     * Get list of teams from scoreboard storage
     *
     * @param request GetTeamsRequest with attributes
     *                List<String> teamIds,
     *                List<String> teamNames
     *                Match criteria: Corresponding String in Team object should be in request List
     * @return List of teams matching all request criteria using AND logic
     */
    List<Team> getTeams(GetTeamsRequest request);

    /**
     * Get single team from scoreboard storage
     *
     * @param request GetTeamRequest with attributes
     *                String teamIds,
     *                String teamNames
     *                Match criteria: Corresponding String in Team object should be equal to request String
     * @return Single team matching all request criteria using AND logic
     */
    Team getTeam(GetTeamRequest request);

    /**
     * Create a new Game with score 0-0 and add it to scoreboard storage
     *
     * @param homeTeamId ID of home team
     * @param awayTeamId ID of away team
     */
    void startGame(String homeTeamId, String awayTeamId);

    /**
     * Remove game from currently active games
     *
     * @param gameId String identifier for a single game
     */
    void finishGame(String gameId);

    /**
     * Set score of a game identified by gameId to gameScore(homeScore, awayScore)
     *
     * @param request UpdateScoreRequest with attributes
     *                String gameId,
     *                GameScore gameScore
     */
    void updateScore(UpdateScoreRequest request);

    /**
     * Get a single game from all registered games by gameId
     *
     * @param request GetGameRequest with attribute String gameId
     * @return Game with id matching request gameId
     */
    Game getGame(GetGameRequest request);

    /**
     * Get summaries of currently active games filtered by request
     *
     * @param request GetTeamRequest with attributes
     *                List<String> gameIds,
     *                List<String> teamIds,
     *                GameScore gameScore
     *                Match criteria: Corresponding String in Game object should be in to request List
     *                and gameScore should be equal to request gameScore
     * @return List of all currently active games matching criteria ordered by most recently added to scoreboard
     */
    List<Game> getActiveGamesSummary(GamesSummaryRequest request);

    /**
     * Get summaries of all currently active games
     *
     * @return List of all currently active games ordered by most recently added to scoreboard
     */
    List<Game> getActiveGamesSummary();

    /**
     * Clear all scoreboard storage, emptying all collections
     */
    void clearScoreboard();
}
