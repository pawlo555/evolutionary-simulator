import SimulationClasses.Animal;
import Utilities.AnimalComparator;
import SimulationClasses.JungleMap;
import Utilities.Vector2d;
import org.junit.Assert;
import org.junit.Test;

import java.util.TreeSet;

public class ComparatorTest {

    JungleMap map = new JungleMap();
    Vector2d position = new Vector2d(10,10);
    Animal animal1 = new Animal(position,map);
    Animal animal2 = new Animal(position,map);

    @Test
    public void comparableTest() {
        animal1.EatGrass(10);
        System.out.println(animal1.toString());
        System.out.println(animal2.toString());
        AnimalComparator comp = new AnimalComparator();
        TreeSet<Animal> set = new TreeSet<>(comp);
        set.add(animal1);
        set.add(animal2);
        Assert.assertTrue(comp.compare(animal1,animal2) >= 0);
        Assert.assertEquals(set.last(), animal1);
        set.remove(animal1);
        Assert.assertEquals(set.size(), 1);

    }
}
