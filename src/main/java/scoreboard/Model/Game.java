package scoreboard.Model;

import Util.IdGenerator;

import java.time.Instant;

public record Game(
        String id,
        String homeTeamId,
        String awayTeamId,
        GameScore gameScore,
        Instant startTime
) {
    public Game(String homeTeamId, String awayTeamId) {
        this(
                IdGenerator.generate("game"),
                homeTeamId,
                awayTeamId,
                new GameScore(0, 0),
                Instant.now()
        );
    }

    public Game homeTeamGoal() {
        return new Game(
                this.id,
                this.homeTeamId,
                this.awayTeamId,
                gameScore.homeTeamGoal(),
                this.startTime
        );
    }

    public Game awayTeamGoal() {
        return new Game(
                this.id,
                this.homeTeamId,
                this.awayTeamId,
                gameScore.awayTeamGoal(),
                this.startTime
        );
    }

    public Game setScore(Integer homeScore, Integer awayScore) {
        return new Game(
                this.id,
                this.homeTeamId,
                this.awayTeamId,
                gameScore.setScore(homeScore, awayScore),
                this.startTime
        );
    }
}
