
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StorageFacilityTest {

    String klassName = "StorageFacility";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void classIsPublic() {
        assertTrue("The class " + klassName + " must be public, so it should be defined as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void emptyConstructor() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Define for the class " + s(klassName) + " a public constructor: "
                + "public " + s(klassName) + "()", ctor.isPublic());
        String e = "the error was caused by the code new " + klassName + "();";
        ctor.withNiceError(e).invoke();
    }

    public Object create() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }


    /*
     *
     */
    @Test
    @Points("08-15.1")
    public void addMethod() throws Throwable {
        String method = "add";

        Object object = create();

        assertTrue("create for the class " + klassName + " the method public void " + method + "(String storageUnit, String item) ",
                klass.method(object, method)
                        .returningVoid().taking(String.class, String.class).isPublic());

        String v = "\nThe code that caused the error: " + klassName + " unit = new " + klassName + "();\n"
                + "unit.add(\"a111\",\"ice skates\");";

        klass.method(object, method)
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("a111", "ice skates");
    }

    @Test
    @Points("08-15.1")
    public void contentsMethod() throws Throwable {
        String method = "contents";

        Object object = create();

        assertTrue("create for the class " + klassName + " the method public ArrayList<String> " + method + "(String storageUnit) ",
                klass.method(object, method)
                        .returning(ArrayList.class)
                        .taking(String.class)
                        .isPublic());

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).invoke("a111", "mouse");

        String e = "\nThe code that caused the error: \n"
                + klassName + " unit = new " + klassName + "();\n"
                + "unit.add(\"a111\",\"mouse\");\n"
                + "System.out.println(s." + method + "(\"a111\"));\n";

        ArrayList answer = new ArrayList();
        answer.add("mouse");

        assertEquals(e, answer, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("a111"));
    }

    @Test
    @Points("08-15.1")
    public void contentsMethodNoStorageUnit() throws Throwable {
        String method = "contents";

        Object object = create();

        String e = "\nThe code that caused the error: \n"
                + klassName + " unit = new " + klassName + "();\n"
                + "unit.add(\"a111\",\"mouse\");\n"
                + "System.out.println(s." + method + "(\"nonexistent\"));\n";

        assertNotNull(e, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("nonexistent"));
    }

    @Test
    @Points("08-15.1")
    public void contentsMethodTwoItemsInStorageUnit() throws Throwable {
        String method = "contents";

        Object object = create();

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).invoke("a111", "mouse");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).invoke("a111", "cheese");

        String e = "\nThe code that caused the error: \n"
                + klassName + " unit = new " + klassName + "();\n"
                + "unit.add(\"a111\",\"mouse\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.contents(\"a11\");\n";

        ArrayList answer = new ArrayList();
        answer.add("mouse");
        answer.add("cheese");

        assertEquals(e, answer, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("a111"));
    }

    @Test
    @Points("08-15.1")
    public void contentsMethodMultipleStorageUnits() throws Throwable {
        String method = "contents";

        Object object = create();

        String e = "\nThe code that caused the error: \n"
                + klassName + " unit = new " + klassName + "();\n"
                + "unit.add(\"a111\",\"mouse\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.add(\"b123\",\"projector\");\n"
                + "unit.add(\"g63\",\"ice skates\");\n"
                + "unit.contents(\"a111\");\n";

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "mouse");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("b123", "projector");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("g63", "ice skates");

        ArrayList answer = new ArrayList();
        answer.add("mouse");
        answer.add("cheese");

        assertEquals(e, answer, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("a111"));

        e = "\nThe code that caused the error: \n"
                + klassName + " unit = new " + klassName + "();\n"
                + "unit.add(\"a111\",\"mouse\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.add(\"b123\",\"projector\");\n"
                + "unit.add(\"g63\",\"ice skates\");\n"
                + "unit.contents(\"b123\");\n";

        answer = new ArrayList();
        answer.add("projector");
        assertEquals(e, answer, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("b123"));

        e = "\nThe code that caused the error: \n"
                + klassName + " unit = new " + klassName + "();\n"
                + "unit.add(\"a111\",\"mouse\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.add(\"b123\",\"projector\");\n"
                + "unit.add(\"g63\",\"ice skates\");\n"
                + "unit.contents(\"g63\");\n";

        assertNotNull(e, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("g63"));
    }

    /*
     *
     */
    @Test
    @Points("08-15.2")
    public void removeMethod() throws Throwable {
        String method = "remove";

        Object object = create();

        assertTrue("create for the class " + klassName + " the method public String " + method + "(String storageUnit, String item) ",
                klass.method(object, method)
                        .returningVoid()
                        .taking(String.class, String.class)
                        .isPublic());
    }

    @Test
    @Points("08-15.2")
    public void removeExisting() throws Throwable {

        Object object = create();

        String e = "\nThe code that caused the error: \n"
                + klassName + " unit = new " + klassName + "();\n"
                + "unit.add(\"a111\",\"mouse\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.add(\"b123\",\"projector\");\n"
                + "unit.add(\"g63\",\"ice skates\");\n"
                + "unit.remove(\"g63\",\"ice skates\");\n"
                + "unit.contents(\"g63\");\n";

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "mouse");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("b123", "projector");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("g63", "ice skates");

        klass.method(object, "remove")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("g63", "ice skates");

        assertNotNull(e, klass.method(object, "contents")
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("g63"));

        ArrayList answer = new ArrayList();

        assertEquals(e, answer, klass.method(object, "contents")
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("g63"));
    }

    @Test
    @Points("08-15.2")
    public void removeOnlyOneIfMultiple() throws Throwable {
        String method = "remove";

        Object object = create();

        String e = "\nThe code that caused the error: \n"
                + klassName + " unit = new " + klassName + "();\n"
                + "unit.add(\"a111\",\"mouse\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.add(\"b123\",\"projector\");\n"
                + "unit.add(\"g63\",\"ice skates\");\n"
                + "unit.remove(\"a111\",\"cheese\");\n"
                + "unit.contents(\"a111\");\n";

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "mouse");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("b123", "projector");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("g63", "ice skates");

        klass.method(object, method)
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");

        assertNotNull(e, klass.method(object, "contents")
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("a111"));

        ArrayList answer = new ArrayList();
        answer.add("mouse");
        answer.add("cheese");

        assertEquals(e, answer, klass.method(object, "contents")
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("a111"));
    }

    @Test
    @Points("08-15.2")
    public void multipleAdditionsContentsExaminationsAndRemovals() throws Throwable {

        Object object = create();

        String e = "\nThe code that caused the error: \n"
                + klassName + " unit = new " + klassName + "();\n"
                + "unit.add(\"a111\",\"mouse\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.add(\"b123\",\"projector\");\n"
                + "unit.add(\"g63\",\"ice skates\");\n"
                + "unit.remove(\"a111\",\"cheese\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.contents(\"a111\");\n";

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "mouse");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("b123", "projector");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("g63", "ice skates");
        klass.method(object, "remove")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");

        ArrayList answer = new ArrayList();
        answer.add("mouse");
        answer.add("cheese");
        answer.add("cheese");

        assertEquals(e, answer, klass.method(object, "contents")
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("a111"));
    }

    @Test
    @Points("08-15.2")
    public void storageUnitsMethod() throws Throwable {
        String method = "storageUnits";

        Object object = create();

        assertTrue("create for the class " + klassName + " the method public ArrayList<String> " + method + "() ",
                klass.method(object, method)
                        .returning(ArrayList.class)
                        .takingNoParams().isPublic());
    }

    @Test
    @Points("08-15.2")
    public void unitsOnList1() throws Throwable {
        String method = "storageUnits";

        Object object = create();

        String e = "\nThe code that caused the error: \n"
                + klassName + " unit = new " + klassName + "();\n"
                + "unit.add(\"a111\",\"mouse\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.add(\"b123\",\"projector\");\n"
                + "unit.add(\"g63\",\"ice skates\");\n"
                + "unit.remove(\"a111\",\"cheese\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.contents(\"a111\");\n"
                + "unit.storageUnits();\n";

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "mouse");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("b123", "projector");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("g63", "ice skates");
        klass.method(object, "remove")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");

        ArrayList answer = new ArrayList();
        answer.add("a111");
        answer.add("b123");
        answer.add("g63");

        assertTrue(e, answer.containsAll(klass.method(object, "storageUnits")
                .returning(ArrayList.class).takingNoParams().withNiceError(e).invoke()));

        assertTrue(e, klass.method(object, "storageUnits")
                .returning(ArrayList.class).takingNoParams().withNiceError(e).invoke().containsAll(answer));
    }
    
    @Test
    @Points("08-15.2")
    public void emptyUnitNotOnList() throws Throwable {
        String method = "storageUnits";

        Object object = create();

        String e = "\nThe code that caused the error: \n"
                + klassName + " unit = new " + klassName + "();\n"
                + "unit.add(\"a111\",\"mouse\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.add(\"a111\",\"cheese\");\n"
                + "unit.add(\"b123\",\"projector\");\n"
                + "unit.add(\"g63\",\"ice skates\");\n"
                + "unit.remove(\"b123\",\"projector\");\n"
                + "unit.storageUnits();\n";

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "mouse");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("a111", "cheese");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("b123", "projector");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("g63", "ice skates");
        klass.method(object, "remove")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("b123", "projector");

        ArrayList answer = new ArrayList();
        answer.add("a111");
        answer.add("g63");

        assertTrue(e, answer.containsAll(klass.method(object, "storageUnits")
                .returning(ArrayList.class).takingNoParams().withNiceError(e).invoke()));

        assertTrue(e, klass.method(object, "storageUnits")
                .returning(ArrayList.class).takingNoParams().withNiceError(e).invoke().containsAll(answer));
    }

    private String s(String klassName) {
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "").replace("java.io.", "");
    }
}
