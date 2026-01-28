package scoreboard.Model;

public record GameScore(Integer homeScore, Integer awayScore) {
    public GameScore homeTeamGoal() {
        return new GameScore(homeScore + 1, awayScore);
    }

    public GameScore awayTeamGoal() {
        return new GameScore(homeScore, awayScore + 1);
    }

    public GameScore setScore(Integer homeScore, Integer awayScore) {
        return new GameScore(homeScore, awayScore);
    }
}
