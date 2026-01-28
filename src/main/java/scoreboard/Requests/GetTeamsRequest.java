package scoreboard.Requests;

import java.util.List;

public record GetTeamsRequest(
        List<String> teamIds,
        List<String> teamNames
) {
}
