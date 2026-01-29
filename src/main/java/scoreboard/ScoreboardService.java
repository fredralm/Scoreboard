package scoreboard;

import scoreboard.Model.Game;
import scoreboard.Model.Team;
import scoreboard.Requests.*;

import java.util.*;

public class ScoreboardService implements Scoreboard {
    HashMap<String, Team> teams = new HashMap<>();
    HashMap<String, Game> allGames = new HashMap<>();
    HashMap<String, Game> activeGames = new HashMap<>();

    public void addTeam(String countryName) {
        Team team = new Team(countryName);
        teams.put(team.id(), team);
    }

    @Override
    public List<Team> getTeams(GetTeamsRequest request) {
        return teams.values()
                .stream()
                .filter(team ->
                        (request.teamIds() == null || request.teamIds().contains(team.id()))
                                && (request.teamNames() == null || request.teamNames().contains(team.countryName()))
                )
                .toList();
    }

    @Override
    public Team getTeam(GetTeamRequest request) {
        if (request.teamId() != null) {
            return teams.get(request.teamId());
        } else return teams.values()
                .stream()
                .filter(t -> Objects.equals(t.countryName(), request.teamName()))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Too many elements match the predicate");
                })
                .orElseThrow(() -> new IllegalStateException("No element matches the predicate"));
    }


    public String startGame(String homeTeamId, String awayTeamId) {
        Game game = new Game(homeTeamId, awayTeamId);
        allGames.put(game.id(), game);
        activeGames.put(game.id(), game);
        return game.id();
    }

    public void finishGame(String gameId) {
        activeGames.remove(gameId);
    }

    @Override
    public void updateScore(UpdateScoreRequest request) {
        Game original = getGame(new GetGameRequest(request.gameId()));
        Game updated = original.setScore(request.scoreUpdate().homeScore(), request.scoreUpdate().awayScore());
        activeGames.put(updated.id(), updated);
        allGames.put(updated.id(), updated);
    }

    @Override
    public Game getGame(GetGameRequest request) {
        return allGames.get(request.gameId());
    }

    @Override
    public List<Game> getActiveGamesSummary(GamesSummaryRequest request) {
        return activeGames.values()
                .stream()
                .filter(game ->
                        (request.gameIds() == null || request.gameIds().contains(game.id()))
                                && (
                                request.teamIds() == null
                                        || request.teamIds().contains(game.homeTeamId())
                                        || request.teamIds().contains(game.awayTeamId())
                        ) && (request.gameScore() == null || request.gameScore().equals(game.gameScore()))
                )
                .sorted(Comparator.comparing(Game::startTime).reversed())
                .toList();
    }

    @Override
    public List<Game> getActiveGamesSummary() {
        return activeGames.values()
                .stream()
                .sorted(Comparator.comparing(Game::startTime).reversed())
                .toList();
    }

    @Override
    public void clearScoreboard() {
        teams.clear();
        allGames.clear();
        activeGames.clear();
    }
}
