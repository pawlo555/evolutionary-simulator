import Utilities.Vector2d;
import org.junit.Assert;
import org.junit.Test;

public class Vector2dTest {
    Vector2d one = new Vector2d(3,8);
    Vector2d two = new Vector2d(-2,8);

    @Test
    public void toStringTest() {
        Assert.assertEquals(one.toString(),"(3,8)");
    }

    @Test
    public void equalsTest() {
        Assert.assertEquals(one, one);
        Assert.assertNotEquals(one, two);
    }

    @Test
    public void precedesTest() {
        Assert.assertFalse(one.precedes(two));
        Assert.assertTrue(one.precedes(one));
    }

    @Test
    public void followTest() {
        Assert.assertTrue(one.follows(two));
        Assert.assertTrue(one.follows(one));
    }

    @Test
    public void upperRightTest() {
        Assert.assertEquals(one.upperRight(two),new Vector2d(3,8));
    }

    @Test
    public void lowerLeftTest() {
        Assert.assertEquals(one.lowerLeft(two), new Vector2d(-2,8));
    }

    @Test
    public void addTest() {
        Assert.assertEquals(one.add(two), new Vector2d(1,16));
    }

    @Test
    public void subtract() {
        Assert.assertEquals(one.subtract(two), new Vector2d(5,0));
    }

    @Test
    public void oppositeTest() {
        Assert.assertEquals(one.opposite(), new Vector2d(-3,-8));
    }
}
