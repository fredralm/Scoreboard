import Util.IdGenerator;
import org.junit.jupiter.api.Test;

public class IdGeneratorTest {
    @Test
    public void givenIdPrefix_whenGeneratingId_thenIdShouldStartWithPrefix() {
        String prefix = "SOME_PREFIX";
        String id = IdGenerator.generate(prefix);

        assert id.startsWith(prefix);
    }

    @Test
    public void givenIdPrefix_whenGeneratingTwoIds_thenIdsShouldBeUnique() {
        String prefix = "SOME_PREFIX";
        String id0 = IdGenerator.generate(prefix);
        String id1 = IdGenerator.generate(prefix);

        assert !id0.equals(id1);
    }
}
