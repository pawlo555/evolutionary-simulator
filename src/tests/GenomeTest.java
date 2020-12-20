import Utilities.MoveDirection;
import org.junit.Test;
import org.junit.Assert;
import Utilities.Genome;

public class GenomeTest {

    Genome genome1 = new Genome();
    Genome genome2 = new Genome();
    Genome genome3 = new Genome(genome1, genome2);

    @Test
    public void drawSingleGeneTest() {
        Assert.assertTrue(genome1.DrawSingleGen() instanceof MoveDirection);
        Assert.assertTrue(genome2.DrawSingleGen() instanceof MoveDirection);
        Assert.assertTrue(genome3.DrawSingleGen() instanceof MoveDirection);
    }

    @Test
    public void GenomeLengthTest() {
        Assert.assertEquals(genome1.toString().length(), 32);
        Assert.assertEquals(genome2.toString().length(), 32);
        Assert.assertEquals(genome3.toString().length(), 32);
    }
}
