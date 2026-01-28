package scoreboard;

import scoreboard.Model.Game;
import scoreboard.Model.Team;
import scoreboard.Requests.*;

import java.util.List;

public interface Scoreboard {
    void addTeam(String countryName);

    List<Team> getTeams(GetTeamsRequest request);

    Team getTeam(GetTeamRequest request);

    void startGame(String homeTeamId, String awayTeamId);

    void finishGame(String gameId);

    void updateScore(UpdateScoreRequest request);

    Game getGame(GetGameRequest request);

    List<Game> getActiveGamesSummary(GamesSummaryRequest request);

    List<Game> getActiveGamesSummary();

    void clearScoreboard();
}
