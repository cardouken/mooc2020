
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

@Points("08-12")
public class SimpleDateTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "SimpleDate";

    @Before
    public void findClass() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void hasMethodHashCode() throws Throwable {
        String method = "hashCode";

        SimpleDate object = new SimpleDate(1, 1, 2011);

        assertTrue("write a method public int " + method + "() for the class " + klassName,
                klass.method(object, method).returning(int.class).takingNoParams().isPublic());
    }

    @Test
    public void twoEqualDatesAlwaysHaveSameHashCode() {
        HashMap<Integer, ArrayList<SimpleDate>> dates = new HashMap<>();

        for (int year = 1999; year <= 2012; year++) {
            for (int month = 1; month <= 12; month++) {
                for (int day = 1; day <= 31; day++) {

                    SimpleDate d = new SimpleDate(day, month, year);
                    SimpleDate d2 = new SimpleDate(day, month, year);

                    assertTrue("The hashCode of two equal dates should always be the same.\nNow the dates " + d + " and " + d2 + " had different hashCodes.", d.hashCode() == d2.hashCode());
                }
            }
        }
    }

    @Test
    public void enoughDifferentHashCodesBetweenYears1900And2100() {
        HashMap<Integer, ArrayList<SimpleDate>> dates = new HashMap<>();

        for (int year = 1900; year <= 2100; year++) {
            for (int month = 1; month <= 12; month++) {
                for (int day = 1; day <= 31; day++) {
                    SimpleDate d = new SimpleDate(day, month, year);

                    if (!dates.containsKey(d.hashCode())) {
                        dates.put(d.hashCode(), new ArrayList<>());
                    }

                    dates.get(d.hashCode()).add(d);
                    if (dates.get(d.hashCode()).size() > 20) {
                        fail("Your hashCode implementation returns values that are too similar.\nThe date " + d + " has the same hashCode as the dates:\n" + dates.get(d.hashCode()));
                    }
                }
            }
        }
    }
}
