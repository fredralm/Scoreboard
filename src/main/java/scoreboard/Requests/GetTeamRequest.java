package scoreboard.Requests;

public record GetTeamRequest(
        String teamId,
        String teamName
) {
}
