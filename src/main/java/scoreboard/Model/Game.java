package scoreboard.Model;

import java.time.Instant;

public record Game(
        String id,
        String homeTeamId,
        String awayTeamId,
        GameScore gameScore,
        Instant startTime
) {
}
