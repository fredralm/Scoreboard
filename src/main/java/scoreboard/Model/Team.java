package scoreboard.Model;

public record Team(
        String id,
        String countryName,
        Integer totalPoints,
        Integer goalsScored,
        Integer goalsAgainst
) {
}
