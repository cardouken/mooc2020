
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

public class A_ProductWarehouseTest {

    String klassName = "ProductWarehouse";
    Reflex.ClassRef<Object> classRef;

    @Before
    public void setup() {
        classRef = Reflex.reflect(klassName);
    }

    @Test
    @Points("09-03.1")
    public void classExists() {
        assertTrue("The class " + s(klassName) + " must be public, meaning it is defined as\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("09-03.1")
    public void inheritsWarehouseClass() {
        Class c = ReflectionUtils.findClass(klassName);

        Class sc = c.getSuperclass();
        assertTrue("The class ProductWarehouse should inherit the Warehouse class", sc.getName().equals("Warehouse"));
    }

    @Test
    @Points("09-03.1")
    public void hasConstructorTakingTwoParameters() throws Throwable {
        newProductWarehouse("grain", 10);
    }

    @Test
    @Points("09-03.1")
    public void noExtraObjectVariables() {
        sanitaryCheck(klassName, 1, "exept for the name of the product, since the ProductWarehouse inherits the balance and capacity of Warehouse");
    }

    /*
     *
     */
    @Test
    @Points("09-03.1")
    public void methodGetName() throws Throwable {
        String method = "getName";
        String error = "write a method public String getName() for the ProductWarehouse class";

        Object o = newProductWarehouse("beer", 10);

        assertTrue(error,
                classRef.method(o, method).returning(String.class).takingNoParams().isPublic());

        String v = "ProductWarehouse p = new ProductWarehouse(\"beer\",\"10\");\n"
                + "p.getName();\n";

        assertEquals(v, "beer", classRef.method(o, method)
                .returning(String.class)
                .takingNoParams().withNiceError("the code that caused the error:\n" + v).invoke());

        o = newProductWarehouse("juice", 7);

        assertTrue(error,
                classRef.method(o, method).returning(String.class).takingNoParams().isPublic());

        v = "ProductWarehouse p = new ProductWarehouse(\"juice\",\"7\");\n"
                + "p.getName();\n";

        assertEquals(v, "juice", classRef.method(o, method)
                .returning(String.class)
                .takingNoParams().withNiceError("the code that caused the error:\n" + v).invoke());

    }

    //**********
    @Test
    @Points("09-03.2")
    public void methodSetName() throws Throwable {
        String method = "setName";
        String error = "write a method public void setName(String nimi) for the class ProductWarehouse";

        Object o = newProductWarehouse("beer", 10);

        assertTrue(error,
                classRef.method(o, method).returningVoid().taking(String.class).isPublic());

        String v = "ProductWarehouse p = new ProductWarehouse(\"beer\",\"10\");\n"
                + "p.setName(\"bier\");\n";

        classRef.method(o, method).returningVoid().taking(String.class).withNiceError(v).invoke("bier");

        v = "ProductWarehouse p = new ProductWarehouse(\"beer\",\"10\");\n"
                + "p.setName(\"bier\");\n"
                + "p.getName()";

        assertEquals(v, "bier", classRef.method(o, "getName")
                .returning(String.class)
                .takingNoParams().withNiceError("the code that caused the error:\n" + v).invoke());

        Method m = null;
        try {
            Class c = ReflectionUtils.findClass(klassName);
            m = ReflectionUtils.requireMethod(c, void.class, method, String.class);
        } catch (Throwable p) {
            fail(error);
        }
        assertTrue(error, m.toString().contains("public"));
        assertFalse(error, m.toString().contains("static"));
    }

    @Test
    @Points("09-03.2")
    public void ProductWarehouseHasOwnToString() throws Throwable {
        Object pw = newProductWarehouse("chocolate", 10.0);
        String str = pw.toString();
        assertTrue("override the toString method of Warehouse in the ProductWarehouse class according to the example in the exercise description", str.contains("chocolate"));
    }

    @Test
    @Points("09-03.2")
    public void ProductWarehouseToStringWorks1() throws Throwable {
        Object pw = newProductWarehouse("chocolate", 10.0);
        ((Warehouse) pw).addToWarehouse(5);
        String str = pw.toString();
        String e = "does the toString() of ProductWarehouse work according to the example? ";
        String c = "try \npw = new ProductWarehouse(\"chocolate\",10); \n"
                + "pw.addToWarehouse(5); \n"
                + "System.out.print(pw);\n";
        assertEquals(e + "\n" + c + "\n", "chocolate: balance = 5.0, space left 5.0", str);
    }

    @Test
    @Points("09-03.2")
    public void ProductWarehouseToStringWorks2() throws Throwable {
        Object pw = newProductWarehouse("mustard", 15.0);
        ((Warehouse) pw).addToWarehouse(10);
        String str = pw.toString();
        String e = "toimiiko Tuotevaraston toString esimerkin ohjeen mukaan? ";
        String c = "try \npw = new ProductWarehouse(\"mustard\",15); pw.addToWarehouse(10);\n"
                + "System.out.print(pw);\n";
        assertEquals(e + "\n" + c + "\n", "mustard: balance = 10.0, space left 5.0", str);
    }

    /*
     *
     */
    private Object newProductWarehouse(String productName, double capacity) throws Throwable {
        assertTrue("write a constructor ProductWarehouse(String productName, double capacity) for the ProductWarehouse class", classRef.constructor().taking(String.class, double.class).isPublic());

        Reflex.MethodRef2<Object, Object, String, Double> ctor = classRef.constructor().taking(String.class, double.class).withNiceError();
        return ctor.invoke(productName, capacity);
    }

    /*
     *
     */
    private void sanitaryCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove the varaiable " + fieldName(field.toString(), s(klassName)) + " from the class " + s(klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the object variables of the the class should all be private. Fix them in the class " + s(klassName) + ", found: " + fieldName(field.toString(), klassName), field.toString().contains("private"));
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
