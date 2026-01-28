package scoreboard.Requests;

import scoreboard.Model.GameScore;
import java.util.List;

public record GamesSummaryRequest(
        List<String> gameIds,
        List<String> teamIds,
        GameScore gameScore
) {
}
