
import org.junit.jupiter.api.Test;

import scoreboard.Model.Team;

public class TeamTest {
    @Test
    public void givenCountryName_whenCreateNewTeam_thenTeamIdShouldBeGenerated() {
        String countryName = "Mexico";
        Team team = new Team(countryName);

        assert team.id() != null;
        assert team.id().startsWith("team");
    }
}
