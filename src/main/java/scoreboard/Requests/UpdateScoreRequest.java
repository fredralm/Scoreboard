package scoreboard.Requests;

import scoreboard.Model.GameScore;

public record UpdateScoreRequest(
        String gameId,
        GameScore scoreUpdate
) {
}
