import utilities.MoveDirection;
import utilities.Vector2d;
import org.junit.Test;
import org.junit.Assert;
import utilities.MapDirection;


public class MapDirectionTest {

    private MapDirection direction = MapDirection.NORTH_WEST;

    @Test
    public void MapDirectionTests() {
        Assert.assertEquals(direction.getDirectionAsInt(),7);
        Assert.assertEquals(direction.getDirectionAsInt(),7);
        Assert.assertEquals(direction.getDirectionAsInt(),7);
        Assert.assertEquals(direction.turn(MoveDirection.BACKWARD),MapDirection.SOUTH_EAST);
        Assert.assertEquals(direction.toUnitVector(), new Vector2d(-1,1));
    }
}
