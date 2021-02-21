
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.*;
import static org.junit.Assert.*;

public class C_ProductWarehouseWithHistoryTest {

    String klassName = "ProductWarehouseWithHistory";
    Reflex.ClassRef<Object> classRef;

    @Before
    public void setup() {
        classRef = Reflex.reflect(klassName);
    }

    @Test
    @Points("09-03.5")
    public void classExists() {
        assertTrue("The class " + s(klassName) + " must be public, meaning it is defined as\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("09-03.5")
    public void inheritsProductWarehouseClass() {
        Class c = ReflectionUtils.findClass(klassName);

        Class sc = c.getSuperclass();
        assertTrue("The class ProductWarehouseWithHistory should inherit the ProductWarehouse class", sc.getName().equals("ProductWarehouse"));
    }

    @Test
    @Points("09-03.5")
    public void hasConstructorTakingThreeParameters() throws Throwable {
        newProductWarehouseWithHistory("grain", 10, 5);
    }

    @Test
    @Points("09-03.5")
    public void theInitialBalanceIsSetAsTheBalanceOfTheWarehouse() throws Throwable {
        Warehouse w = (Warehouse) newProductWarehouseWithHistory("grain", 10, 5);
        Assert.assertEquals("Make sure you are setting the balance of the warehouse when calling the constructor of ProductWarehouseWithHistory.", 5, w.getBalance(), 0.01);
        Assert.assertEquals("Make sure you are setting the capacity of the warehouse when calling the constructor of ProductWarehouseWithHistory.", 10, w.getCapacity(), 0.01);
    }

    @Test
    @Points("09-03.5")
    public void noExtraObjectVariables() {
        sanitaryCheck(klassName, 1, "other object variables than ChangeHistory");
    }

    /*
     *
     */
    @Test
    @Points("09-03.5")
    public void hasMethodHistory() throws Throwable {
        String method = "history";
        String error = "write a method public String history() for the class ProductWarehouseWithHistory";

        Object o = newProductWarehouseWithHistory("beer", 10, 2);

        assertTrue(error,
                classRef.method(o, method).returning(String.class).takingNoParams().isPublic());

        String v = "ProductWarehouseWithHistory pwh = new ProductWarehouseWithHistory(\"beer\", 10, 2);\n"
                + "pwh.history();\n";

        assertEquals(v, "[2.0]",
                classRef.method(o, method).returning(String.class).takingNoParams().withNiceError(v).invoke());

        o = newProductWarehouseWithHistory("milk", 100, 99);

        v = "ProductWarehouseWithHistory pwh = new ProductWarehouseWithHistory(\"milk\", 100, 99);\n"
                + "pwh.history();\n";

        assertEquals(v, "[99.0]",
                classRef.method(o, method).returning(String.class).takingNoParams().withNiceError(v).invoke());

    }

    // ************
    @Test
    @Points("09-03.6")
    public void hasMethodAddToWarehouse() throws Throwable {
        String method = "addToWarehouse";
        String error = "write a method public void addToWarehouse(double maara) for the class ProductWarehouseWithHistory";

        Object o = newProductWarehouseWithHistory("beer", 10, 2);

        assertTrue(error,
                classRef.method(o, method).returningVoid().taking(double.class).isPublic());

        String v = "ProductWarehouseWithHistory pwh = new ProductWarehouseWithHistory(\"beer\", 10, 2);\n"
                + "pwh.addToWarehouse(3);\n";

        classRef.method(o, method).returningVoid().taking(double.class).withNiceError(v).invoke(3.0);
    }

    @Test
    @Points("09-03.6")
    public void hasMethodTakeFromWarehouse() throws Throwable {
        String method = "takeFromWarehouse";
        String error = "write a method public double takeFromWarehouse(double maara) for the class ProductWarehouseWithHistory";

        Object o = newProductWarehouseWithHistory("beer", 10, 2);

        assertTrue(error,
                classRef.method(o, method).returning(double.class).taking(double.class).isPublic());

        String v = "ProductWarehouseWithHistory pwh = new ProductWarehouseWithHistory(\"beer\", 10, 2);\n"
                + "pwh.takeFromWarehouse(1);\n";

        assertEquals(v, 1.0, (double) classRef.method(o, method).returning(double.class).taking(double.class).withNiceError(v).invoke(1.0), 0.01);
    }

    @Test
    @Points("09-03.6")
    public void addingAndTakingWorks() throws Throwable {
        Warehouse pwh = (Warehouse) (newProductWarehouseWithHistory("coffee", 10, 5));

        String code = "check the code:\npwh = new ProductWarehouseWithHistory(\"coffee\",10,5);\npwh.addToWarehouse(5);\npwh.getBalance() ";

        addToWarehouse(pwh, 5, code);

        assertEquals("does the addToWarehouse method in ProductWarehouseWithHistory call the overridden method?\ncheck the code:\n"
                + code, 10, pwh.getBalance(), 0.01);

        takeFromWarehouse(pwh, 7, code);
        code = "check the code:\npwh = new ProductWarehouseWithHistory(\"coffee\",10,5);\npwh.addToWarehouse(5);\npwh.takeFromWarehouse(7);\npwh.getBalance() ";

        assertEquals("does the takeFromWarehouse method in ProductWarehouseWithHistory call the overridden method?\ncheck the code:\n"
                + code, 3, pwh.getBalance(), 0.01);
    }

    @Test
    @Points("09-03.6")
    public void addingAndTakingAffectsTheHistory1() throws Throwable {
        Warehouse pwh = (Warehouse) (newProductWarehouseWithHistory("coffee", 10, 5));

        String code = "check the code:\n"
                + "pwh = new ProductWarehouseWithHistory(\"coffee\",10,5);\npwh.addToWarehouse(5);\npwh.history() ";

        addToWarehouse(pwh, 5, code);

        assertEquals("remember to update the change history when calling the method addToWarehouse! \ncheck the code:\n"
                + code, "[5.0, 10.0]", history(pwh, code));

        takeFromWarehouse(pwh, 7, code);

        code = "check the code:\n"
                + "pwh = new ProductWarehouseWithHistory(\"coffee\",10,5);\npwh.addToWarehouse(5);\npwh.takeFromWarehouse(7);\npwh.history() ";

        assertEquals("remember to update the change history when calling "
                + "the methods addToWarehouse and takeFromWarehouse! \ncheck the code:\n"
                + code, "[5.0, 10.0, 3.0]", history(pwh, code));
    }

    @Test
    @Points("09-03.6")
    public void addingAndTakingAffectsTheHistory2() throws Throwable {
        Warehouse pwh = (Warehouse) (newProductWarehouseWithHistory("coffee", 10, 5));

        String code = "check the code:\n"
                + "pwh = new ProductWarehouseWithHistory(\"coffee\",10,5);\npwh.addToWarehouse(1);\npwh.addToWarehouse(1);\npwh.addToWarehouse(1);\npwh.addToWarehouse(1);\npwh.history() ";

        addToWarehouse(pwh, 1, code);
        addToWarehouse(pwh, 1, code);
        addToWarehouse(pwh, 1, code);
        addToWarehouse(pwh, 1, code);

        assertEquals("remember to update the change history when calling "
                + "the methods addToWarehouse and takeFromWarehouse! \ncheck the code:\n"
                + code, "[5.0, 6.0, 7.0, 8.0, 9.0]", history(pwh, code));
        takeFromWarehouse(pwh, 3, code);
        takeFromWarehouse(pwh, 3, code);
        takeFromWarehouse(pwh, 3, code);

        code = "check the code:\n"
                + "pwh = new ProductWarehouseWithHistory(\"coffee\",10,5);\npwh.addToWarehouse(1);\npwh.addToWarehouse(1);\npwh.addToWarehouse(1);\npwh.addToWarehouse(1);\npwh.takeFromWarehouse(3);\npwh.takeFromWarehouse(3);\npwh.takeFromWarehouse(3);  pwh.history() ";

        assertEquals("remember to update the change history when calling "
                + "the methods addToWarehouse and takeFromWarehouse! \ncheck the code:\n"
                + code, "[5.0, 6.0, 7.0, 8.0, 9.0, 6.0, 3.0, 0.0]", history(pwh, code));
    }

    @Test
    @Points("09-03.6")
    public void takeFromWarehouseReturnsTheAmountTaken() throws Throwable {
        Warehouse pwh = (Warehouse) (newProductWarehouseWithHistory("coffee", 10, 5));

        String code = "Make sure you're not returning more than the warehouse actually contains. check the code:\n"
                + "pwh = new ProductWarehouseWithHistory(\"coffee\",10,5);\npwh.takeFromWarehouse(7); ";

        double taken = takeFromWarehouse(pwh, 7, code);
        assertEquals(code, 5, taken, 0.1);
    }

    //*********
    @Test
    @Points("09-03.7")
    public void hasMethodPrintAnalysis() throws Throwable {
        String method = "printAnalysis";
        String error = "write a method public void printAnalysis() for the class ProductWarehouseWithHistory";

        Object o = newProductWarehouseWithHistory("beer", 10, 2);

        assertTrue(error,
                classRef.method(o, method).returningVoid().takingNoParams().isPublic());

        String v = "ProductWarehouseWithHistory pwh = new ProductWarehouseWithHistory(\"beer\", 10, 2);\n"
                + "pwh.addToWarehouse(5);\n";

        addToWarehouse(o, 5, v);

        classRef.method(o, method).returningVoid().takingNoParams().withNiceError(v).invoke();

    }

    /*
     *
     */
    @Test
    @Points("09-03.7")
    public void printAnalysisContainsTheDesiredLines() throws Throwable {
        MockInOut io = new MockInOut("");
        Object v = newProductWarehouseWithHistory("beer", 10, 5);

        String k = "ProductWarehouseWithHistory pwh = new ProductWarehouseWithHistory(\"beer\", 10, 2);\n"
                + "pwh.printAnalysis();\n";

        printAnalysis(v, k);
        String[] lines = io.getOutput().split("\n");
        String searchedFor = "Product:";
        String line = find(lines, searchedFor);
        assertTrue("Calling the printAnalysis method should print a line containing the string \"" + searchedFor + "\"", line != null);
        searchedFor = "History:";
        line = find(lines, searchedFor);
        assertTrue("Calling the printAnalysis method should print a line containing the string \"" + searchedFor + "\"", line != null);
        searchedFor = "Largest amount of product:";
        line = find(lines, searchedFor);
        assertTrue("Calling the printAnalysis method should print a line containing the string \"" + searchedFor + "\"", line != null);
        searchedFor = "Smallest amount of product:";
        line = find(lines, searchedFor);
        assertTrue("Calling the printAnalysis method should print a line containing the string \"" + searchedFor + "\"", line != null);
        searchedFor = "Average:";
        line = find(lines, searchedFor);
        assertTrue("Calling the printAnalysis method should print a line containing the string \"" + searchedFor + "\"", line != null);
    }

    @Test
    @Points("09-03.7")
    public void printAnalysisWorks1() throws Throwable {
        MockInOut io = new MockInOut("");
        Object v = newProductWarehouseWithHistory("coffee", 10, 2);

        String k = "Is printAnalysis() working correctly? Running the code:\n"
                + "tv = new ProductWarehouseWithHistory(\"coffee\", 10, 2);\n"
                + "pwh.addToWarehouse(4);\npwh.takeFromWarehouse(2);\n"
                + "pwh.printAnalysis()\n prints the line \"";

        addToWarehouse(v, 4, k);
        takeFromWarehouse(v, 2, k);

        printAnalysis(v, k);
        String[] lines = io.getOutput().split("\n");

        String searchedFor = "History:";
        String line = find(lines, searchedFor);
        assertTrue(k + line + "\"", line.contains("[2.0, 6.0, 4.0]"));

        searchedFor = "Product:";
        line = find(lines, searchedFor);
        assertTrue(k + line + "\"", line.contains("coffee"));

        searchedFor = "Largest amount of product:";
        line = find(lines, searchedFor);
        assertTrue(k + line + "\"", line.contains("6"));

        searchedFor = "Smallest amount of product:";
        line = find(lines, searchedFor);
        assertTrue(k + line + "\"", line.contains("2"));

        searchedFor = "Average:";
        line = find(lines, searchedFor);
        assertTrue(k + line + "\"", line.contains("4"));

    }

    @Test
    @Points("09-03.7")
    public void printAnalysisWorks2() throws Throwable {
        MockInOut io = new MockInOut("");
        Object v = newProductWarehouseWithHistory("coffee", 10, 2);
        String k = "Is printAnalysis() working correctly? Running the code: \n"
                + "pwh = new ProductWarehouseWithHistory(\"coffee\", 10, 2);\n"
                + "pwh.addToWarehouse(4);\npwh.takeFromWarehouse(2);\npwh.printAnalysis()\n"
                + "prints the line \"";

        addToWarehouse(v, 4, k);
        takeFromWarehouse(v, 2, k);
        addToWarehouse(v, 6, k);
        takeFromWarehouse(v, 2, k);

        printAnalysis(v, k);
        String[] lines = io.getOutput().split("\n");

        String searchedFor = "History:";
        String line = find(lines, searchedFor);
        assertTrue(k + line + "\"", line.contains("[2.0, 6.0, 4.0, 10.0, 8.0]"));

        searchedFor = "Product:";
        line = find(lines, searchedFor);
        assertTrue(k + line + "\"", line.contains("coffee"));

        searchedFor = "Largest amount of product:";
        line = find(lines, searchedFor);
        assertTrue(k + line + "\"", line.contains("10"));

        searchedFor = "Smallest amount of product:";
        line = find(lines, searchedFor);
        assertTrue(k + line + "\"", line.contains("2"));

        searchedFor = "Average:";
        line = find(lines, searchedFor);
        assertTrue(k + line + "\"", line.contains("6"));
    }

    /*
     *
     */
    private Object newProductWarehouseWithHistory(String productName, double capacity, double initialBalance) throws Throwable {
        assertTrue("write a constructor ProductWarehouseWithHistory(String productName, double capacity, double initialBalance) for the ProductWarehouseWithHistory class", classRef.constructor().taking(String.class, double.class, double.class).isPublic());

        String v = "\nThe code that caused the error:\n new ProductWarehouseWithHistory(\"" + productName + "\"," + capacity + "," + initialBalance + ");";

        Reflex.MethodRef3<Object, Object, String, Double, Double> ctor = classRef.constructor().taking(String.class, double.class, double.class).withNiceError();
        return ctor.withNiceError(v).invoke(productName, capacity, initialBalance);

    }

    private String history(Object o, String v) throws Throwable {
        return classRef.method(o, "history").returning(String.class).takingNoParams().withNiceError(v).invoke();
    }

    private double takeFromWarehouse(Object o, double maara, String v) throws Throwable {
        return classRef.method(o, "takeFromWarehouse").returning(double.class)
                .taking(double.class).withNiceError(v).invoke(maara);
    }

    private void printAnalysis(Object o, String v) throws Throwable {
        classRef.method(o, "printAnalysis").returningVoid()
                .takingNoParams().withNiceError(v).invoke();
    }

    private void addToWarehouse(Object o, double maara, String v) throws Throwable {
        classRef.method(o, "addToWarehouse").returningVoid()
                .taking(double.class).withNiceError(v).invoke(maara);
    }

    private String find(String[] lines, String searchedFor) {
        for (String line : lines) {
            if (line.contains(searchedFor)) {
                return line;
            }
        }

        return null;
    }

    /*
     *
     */
    private void sanitaryCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove the varaiable " + fieldName(field.toString(), s(klassName)) + " from the class " + s(klassName) , field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the object variables of the the class should all be private. Fix them in the class " + s(klassName) + " found: " + fieldName(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("you don't need object variables for the " + s(klassName) + " class " + m + ". Remove the extra ones", var <= n);
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
}
