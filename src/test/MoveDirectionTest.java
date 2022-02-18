import utilities.MoveDirection;
import org.junit.Test;
import org.junit.Assert;


public class MoveDirectionTest {

    private MoveDirection direction = MoveDirection.LEFT;

    @Test
    public void MoveDirectionTests() {
        for (int i=0; i<100; i++) {
            Assert.assertTrue(MoveDirection.RandDirection().getTurnBy() >= 0);
            Assert.assertTrue(MoveDirection.RandDirection().getTurnBy() < 8);
        }

    }
}