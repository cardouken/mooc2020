
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class B_ChangeHistoryTest {

    String klassName = "ChangeHistory";
    Reflex.ClassRef<Object> classRef;

    @Before
    public void setup() {
        classRef = Reflex.reflect(klassName);
    }

    @Test
    @Points("09-03.3")
    public void classExists() {
        assertTrue("The class " + s(klassName) + " must be public, meaning it is defined as\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("09-03.3")
    public void inheritsNothing() {
        Class c = ReflectionUtils.findClass(klassName);

        Class sc = c.getSuperclass();
        assertTrue("The ChangeHistory class shouldn't inherit any class!", sc.getName().equals("java.lang.Object"));
    }

    @Test
    @Points("09-03.3")
    public void noExtraObjectVariables() {
        sanitaryCheck(klassName, 1, "other object variables than the list for storing doubles");
    }

    @Test
    @Points("09-03.3")
    public void hasAConstructor() throws Throwable {
        newChangeHistory();
    }

    @Test
    @Points("09-03.3")
    public void hasMethodAdd() throws Throwable {
        String method = "add";
        String error = "write a method public void add(double tilanne) for the ChangeHistory class";

        Object o = newChangeHistory();

        assertTrue(error,
                classRef.method(o, method).returningVoid().taking(double.class).isPublic());

        String v = "ChangeHistory ch = new ChangeHistory();\n"
                + "ch.add(99);\n";

        classRef.method(o, method).returningVoid().taking(double.class).withNiceError(v).invoke(99.0);
    }

    @Test
    @Points("09-03.3")
    public void hasMethodClear() throws Throwable {
        String method = "clear";
        String error = "write a method public void clear() for the ChangeHistory class";

        Object o = newChangeHistory();

        assertTrue(error,
                classRef.method(o, method).returningVoid().takingNoParams().isPublic());

        String v = "ChangeHistory ch = new ChangeHistory();\n"
                + "ch.clear();\n";

        classRef.method(o, method).returningVoid().takingNoParams().withNiceError(v).invoke();

    }

    @Test
    @Points("09-03.3")
    public void hasToString() throws Throwable {
        Object ch = newChangeHistory();
        String e = "ChangeHistory ch = new ChangeHistory();\n"
                + "ch.toString();\n";

        assertFalse("write a toString method for the ChangeHistory class according to the explanation in the exercise description, "
                + "\nthat is by calling the toString of the list which is "
                + "an object variable of ChangeHistory", toString(ch, e).contains("@"));
    }

    private String toString(Object o, String v) throws Throwable {
        return classRef.method(o, "toString").returning(String.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("09-03.3")
    public void toStringForEmptyHistory() throws Throwable {
        Object ch = newChangeHistory();
        ArrayList<Double> list = new ArrayList<Double>();

        assertEquals("write a toString method for the ChangeHistory class according to the explanation in the exercise description,\n"
                + "that is by calling the toString of the list which is an object variable of ChangeHistory", list.toString(), ch.toString());
    }

    /*
     *
     */
    @Test
    @Points("09-03.3")
    public void toStringWorksWhenAdding1() throws Throwable {
        Object ch = newChangeHistory();

        String c = "check the code:\nch = new ChangeHistory();\n"
                + "ch.add(5.0);\n";

        add(ch, 5, c);
        ArrayList<Double> list = new ArrayList<Double>();
        list.add(5.0);

        c = "check the code:\nch = new ChangeHistory();\n"
                + "ch.add(5.0);\n"
                + "System.out.println(ch)";

        assertEquals(c, list.toString(), toString(ch, c));
    }

    @Test
    @Points("09-03.3")
    public void toStringWorksWhenAdding2() throws Throwable {
        Object ch = newChangeHistory();

        String c = "check the code: \n"
                + "ch = new ChangeHistory();\n"
                + "ch.add(5.0);\n"
                + "ch.add(12.0);\n"
                + "ch.add(4.0);\n";
        add(ch, 5, c);
        add(ch, 12, c);
        add(ch, 4, c);
        ArrayList<Double> list = new ArrayList<Double>();
        list.add(5.0);
        list.add(12.0);
        list.add(4.0);

        c = "check the code:\nch = new ChangeHistory();\nch.add(5.0);\nch.add(12.0);\n"
                + "ch.add(4.0);\nSystem.out.println(ch)\n";

        assertEquals(c, list.toString(), toString(ch, c));

        c = "check the code:\nch = new ChangeHistory();\nch.add(5.0);\nch.add(12.0);\n"
                + "ch.add(4.0);\nch.clear();\nSystem.out.println(ch)\n";

        clear(ch, "check the code:\nch = new ChangeHistory();\nch.add(5.0);\n"
                + "ch.add(12.0);\nch.add(4.0);\nch.clear()\n;");
        list.clear();

        assertEquals(c, list.toString(), toString(ch, c));
    }

    // ************
    @Test
    @Points("09-03.4")
    public void hasMethodMinValue() throws Throwable {
        String method = "minValue";
        String error = "write a method public double minValue() for the class ChangeHistory";

        Object o = newChangeHistory();

        assertTrue(error,
                classRef.method(o, method).returning(double.class).takingNoParams().isPublic());

        String v = "ChangeHistory ch = new ChangeHistory();\n"
                + "ch.add(99);\n"
                + "ch.minValue();\n";

        add(o, 99.0, v);

        assertEquals(v, 99.0, minValue(o, v), 0.01);
    }

    @Test
    @Points("09-03.4")
    public void methodMinValueNotModifyList() throws Throwable {
        String v = "ChangeHistory ch = new ChangeHistory();\n"
                + "ch.add(3);\n"
                + "ch.add(1);\n"
                + "ch.add(5);\n"
                + "ch.minValue();\n";
        Object o = newChangeHistory();

        add(o, 3.0, v);
        add(o, 1.0, v);
        add(o, 5.0, v);
        ArrayList<Double> list = new ArrayList<Double>();
        list.add(3.0);
        list.add(1.0);
        list.add(5.0);
        assertEquals(v, 1.0, minValue(o, v), 0.01);
        assertEquals(v + "System.out.println(ch);\nAre you modifying the list inside the minValue method?\n", list.toString(), toString(o, v));
    }

    @Test
    @Points("09-03.4")
    public void hasMethodMaxValue() throws Throwable {
        String method = "maxValue";
        String error = "write a method public double maxValue() for the ChangeHistory class";
        Object o = newChangeHistory();

        assertTrue(error,
                classRef.method(o, method).returning(double.class).takingNoParams().isPublic());

        String v = "ChangeHistory ch = new ChangeHistory();\n"
                + "ch.add(99);\n"
                + "ch.maxValue();\n";

        classRef.method(o, "add").returningVoid().taking(double.class).withNiceError(v).invoke(99.0);

        assertEquals(v, 99.0,
                classRef.method(o, method).returning(double.class).takingNoParams().withNiceError(v).invoke(), 0.01);
    }

    @Test
    @Points("09-03.4")
    public void methodMaxValueNotModifyList() throws Throwable {
        String v = "ChangeHistory ch = new ChangeHistory();\n"
                + "ch.add(3);\n"
                + "ch.add(5);\n"
                + "ch.add(1);\n"
                + "ch.maxValue();\n";
        Object o = newChangeHistory();

        add(o, 3.0, v);
        add(o, 5.0, v);
        add(o, 1.0, v);
        ArrayList<Double> list = new ArrayList<Double>();
        list.add(3.0);
        list.add(5.0);
        list.add(1.0);
        assertEquals(v, 5.0, maxValue(o, v), 0.01);
        assertEquals(v + "System.out.println(ch);\nAre you modifying the list inside the maxValue method?\n", list.toString(), toString(o, v));
    }

    @Test
    @Points("09-03.4")
    public void hasMethodAverage() throws Throwable {
        String method = "average";
        String error = "write a method public double average() for the ChangeHistory class";
        Object o = newChangeHistory();

        assertTrue(error,
                classRef.method(o, method).returning(double.class).takingNoParams().isPublic());

        String v = "ChangeHistory ch = new ChangeHistory();\n"
                + "ch.add(99);\n"
                + "ch.average();\n";

        classRef.method(o, "add").returningVoid().taking(double.class).withNiceError(v).invoke(99.0);

        assertEquals(v, 99.0,
                classRef.method(o, method).returning(double.class).takingNoParams().withNiceError(v).invoke(), 0.01);
    }

    /*
     *
     */
    @Test
    @Points("09-03.4")
    public void theValuesAreCalculatedCorrectly1() throws Throwable {
        String c = "check the code:\n"
                + "ch = new ChangeHistory();\nch.add(4.0);\nch.add(-1.0);\nch.add(3);\n";

        Object ch = newChangeHistory();
        add(ch, 4, c);
        add(ch, -1, c);
        add(ch, 3, c);

        assertEquals(c + "ch.minValue() ", -1, minValue(ch, c + "ch.minValue()"), 0.01);
        assertEquals(c + "ch.maxValue() ", 4, maxValue(ch, c + "ch.maxValue()"), 0.01);
        assertEquals(c + "ch.average() ", 2, average(ch, c + "ch.average()"), 0.01);
    }

    @Test
    @Points("09-03.4")
    public void theValuesAreCalculatedCorrectlyWhenClearing() throws Throwable {
        String c = "check the code:\nch = new ChangeHistory();\nch.add(7.0);\nch.clear();\nch.add(4.0);\nch.add(-1.0);\nch.add(3);\n";

        Object ch = newChangeHistory();
        add(ch, 7, c);
        clear(ch, "check the code:\nch = new ChangeHistory();\nch.add(7.0);\nch.clear();\n");
        add(ch, 4, c);
        add(ch, -1, c);
        add(ch, 3, c);

        assertEquals(c + "ch.minValue() ", -1, minValue(ch, c + "ch.minValue()"), 0.01);
        assertEquals(c + "ch.maxValue() ", 4, maxValue(ch, c + "ch.maxValue()"), 0.01);
        assertEquals(c + "ch.average() ", 2, average(ch, c + "ch.average()"), 0.01);
    }

    @Test
    @Points("09-03.4")
    public void theValuesAreCalculatedCorrectly2() throws Throwable {
        for (int i = 0; i < 5; i++) {
            ArrayList<Double> values = generateNumbers();

            Object ch = newChangeHistory();
            String vlues = "";
            for (Double value : values) {
                vlues += value + " ";
                add(ch, value, "when the values " + vlues + " were added to the ChangeHistory");
            }

            assertEquals("when the values " + values + " were added to the ChangeHistory - minValue ", Collections.min(values), minValue(ch, "when the values " + values + " were added to the ChangeHistory - minValue "), 0.01);
            assertEquals("when the values " + values + " were added to the ChangeHistory - maxValue ", Collections.max(values), maxValue(ch, "when the values " + values + " were added to the ChangeHistory - maxValue "), 0.01);
            assertEquals("when the values " + values + " were added to the ChangeHistory - average ", ka(values), average(ch, "when the values " + values + " were added to the ChangeHistory - average "), 0.01);
        }
    }

    /*
     *
     */
    private void add(Object o, double tilavuus, String v) throws Throwable {
        classRef.method(o, "add").returningVoid().taking(double.class).withNiceError(v).invoke(tilavuus);

    }

    private void clear(Object o, String v) throws Throwable {
        classRef.method(o, "clear").returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    private double minValue(Object o, String v) throws Throwable {
        return classRef.method(o, "minValue").returning(double.class).takingNoParams().withNiceError(v).invoke();
    }

    private double maxValue(Object o, String v) throws Throwable {
        return classRef.method(o, "maxValue").returning(double.class).takingNoParams().withNiceError(v).invoke();
    }

    private double average(Object o, String v) throws Throwable {
        return classRef.method(o, "average").returning(double.class).takingNoParams().withNiceError(v).invoke();
    }

    /*private double suurinMuutos(Object o, String v) throws Throwable {
        return classRef.method(o, "suurinMuutos").returning(double.class).takingNoParams().withNiceError(v).invoke();
    }

    private double varianssi(Object o, String v) throws Throwable {
        return classRef.method(o, "varianssi").returning(double.class).takingNoParams().withNiceError(v).invoke();
    }*/

    Random rand = new Random();

    private ArrayList<Double> generateNumbers() {
        ArrayList<Double> numbers = new ArrayList<Double>();

        int limit = 10 + rand.nextInt(10);

        for (int i = 0; i < limit; i++) {
            numbers.add(20.0 - rand.nextInt(30));
        }

        return numbers;
    }

    private double ka(ArrayList<Double> numbers) {
        double s = 0;
        for (Double number : numbers) {
            s += number;
        }
        return s / numbers.size();
    }

    /*
     *
     */
    private void sanitaryCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove the varaiable " + fieldName(field.toString(), s(klassName)) + " from the class" + s(klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the object variables of the the class should all be private. Fix them in the class " + s(klassName) + " found: " + fieldName(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + s(klassName) + " doesn't need " + m + ". Remove the extra ones", var <= n);
        }
    }

    private String fieldName(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    private String s(String klassName) {
        if (!klassName.contains(".")) {
            return klassName;
        }

        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }

    private Object newChangeHistory() throws Throwable {
        assertTrue("tee luokalle ChangeHistory konstruktori ChangeHistory()", classRef.constructor().takingNoParams().isPublic());

        Reflex.MethodRef0<Object, Object> ctor = classRef.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }
}
