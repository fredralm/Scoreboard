package scoreboard.Model;

import Util.IdGenerator;

public record Team(
        String id,
        String countryName
) {
    public Team(String countryName) {
        this(IdGenerator.generate("team"), countryName);
    }
}
