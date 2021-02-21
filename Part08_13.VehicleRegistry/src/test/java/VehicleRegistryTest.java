
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;

public class VehicleRegistryTest {

    String klassName = "VehicleRegistry";
    Reflex.ClassRef<Object> klass;

    @Test
    @Points("08-13.1")
    public void noExtraVariablesInLicensePlate() {
        cleanlinessCheck("LicensePlate", 2, "the instance variables for country and license number that were already implemented");
    }

    @Points("08-13.1")
    @Test
    public void equalsMethodForLicensePlate() {
        testEquals("FI", "ABC-123", "FI", "ABC-123", true);
        testEquals("FI", "ABC-123", "FI", "ABC-122", false);
        testEquals("FI", "ABC-123", "F", "ABC-123", false);
        testEquals("D", "B IFK-333", "D", "B IFK-333", true);
        testEquals("D", "B IFK-33", "D", "B IFK-333", false);
        testEquals("D", "B IFK-33", "G", "B IFK-333", false);
    }

    @Points("08-13.1")
    @Test
    public void licensePlateHashCode() {
        testHash("FI", "ABC-123", "FI", "ABC-123");
        testHash("D", "B IFK-333", "D", "B IFK-333");
        testHash("FI", "TUX-100", "FI", "TUX-100");
        testHash("FI", "UKK-999", "FI", "UKK-999");

        LicensePlate r1 = new LicensePlate("FI", "AAA-111");
        LicensePlate r2 = new LicensePlate("B", "ZZ-22 A");
        LicensePlate r3 = new LicensePlate("QQ", "joopajoo");
        assertFalse("your hashCode method seems to return the same value for all licensePlates " + r1.hashCode(),
                r1.hashCode() == r2.hashCode() && r2.hashCode() == r3.hashCode());
    }

    @Points("08-13.2")
    @Test
    public void classIsPublic() {
        klass = Reflex.reflect(klassName);
        assertTrue("The " + klassName + " class must be public, i.e, it must be declared as:\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("08-13.2")
    public void noExtraVariables() {
        cleanlinessCheck(klassName, 1, "one instance variable with the type HashMap<LicensePlate, String>, which stores the vehicles info ");
    }

    @Test
    @Points("08-13.2")
    public void hasAHashMap() {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();
        assertTrue("For the " + klassName + " class add an intance variable with the type: HashMap<LicensePlate, String>", fields.length == 1);
        assertTrue("The class " + klassName + " must have an instance variable with the HashMap<LicensePlate, String>-type", fields[0].toString().contains("HashMap"));
    }

    @Test
    @Points("08-13.2")
    public void noParamsConstructor() throws Throwable {
        klass = Reflex.reflect(klassName);
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("For the " + klassName + " class, define the public constructor: public " + klassName + "()", ctor.isPublic());
        String v = "this error was caused by trying to run: new VehicleRegistry();";
        ctor.withNiceError(v).invoke();
    }

    @Test
    @Points("08-13.2")
    public void addMethod() throws Throwable {
        String method = "add";

        Object object = create();

        assertTrue("For the " + klassName + " class, implement the method:public boolean " + method + "(LicensePlate licenseNumber, String owner) ",
                klass.method(object, method)
                .returning(boolean.class).taking(LicensePlate.class, String.class).isPublic());

        String v = "\nThis error was caused by trying:\n VehicleRegistry vr = new VehicleRegistry(); "
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");";

        assertEquals("VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");",
                true, klass.method(object, method)
                .returning(boolean.class).taking(LicensePlate.class, String.class).
                withNiceError(v).invoke(new LicensePlate("FI", "AAA-111"), "Arto"));

        assertEquals("Trying to add a dublicate to the registry should return false\n"
                + "VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");",
                false, klass.method(object, method)
                .returning(boolean.class).taking(LicensePlate.class, String.class).invoke(new LicensePlate("FI", "AAA-111"), "Arto"));

        assertEquals("Trying to add a dublicate to the registry should return false\n"
                + "VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Pekka\");",
                false, klass.method(object, method)
                .returning(boolean.class).taking(LicensePlate.class, String.class).invoke(new LicensePlate("FI", "AAA-111"), "Pekka"));
    }

    @Test
    @Points("08-13.2")
    public void getMethod() throws Throwable {
        String method = "get";

        Object object = create();

        assertTrue("For the " + klassName + " class, implement the method public String " + method + "(LicensePlate licensePlate)",
                klass.method(object, method)
                .returning(String.class).taking(LicensePlate.class).isPublic());

        String v = "\nThis error was caused by trying:"
                + "\n VehicleRegistry vr = new VehicleRegistry(); "
                + "vr.search(new LicensePlate(\"FI\", \"AAA-111\"));";

        assertEquals("Trying search for a plate not in the regisrty should return null\n"
                + "VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.search(new LicensePlate(\"FI\", \"AAA-111\"));",
                null, klass.method(object, method)
                .returning(String.class).
                taking(LicensePlate.class).withNiceError(v).
                invoke(new LicensePlate("FI", "AAA-111")));

        add(object, "FI", "AAA-111", "Arto");

        assertEquals("Seaching for a plate that has been added to the registry should return its owner\n"
                + "VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "vr.search( new LicensePlate(\"FI\", \"AAA-111\"));",
                "Arto", klass.method(object, method)
                .returning(String.class).taking(LicensePlate.class).invoke(new LicensePlate("FI", "AAA-111")));

        add(object, "FI", "XX-999", "Arto");
        assertEquals("Seaching for a plate that has been added to the registry should return its owner\n"
                + "VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "vr.add( new LicensePlate(\"FI\", \"XX-999\"), \"Arto\");\n"
                + "vr.search( new LicensePlate(\"FI\", \"AAA-111\"));",
                "Arto", klass.method(object, method)
                .returning(String.class).taking(LicensePlate.class).invoke(new LicensePlate("FI", "AAA-111")));

        assertEquals("Seaching for a plate that has been added to the registry should return its owner\n"
                + "VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "vr.add( new LicensePlate(\"FI\", \"XX-999\"), \"Arto\");\n"
                + "vr.search( new LicensePlate(\"FI\", \"XX-999\"));",
                "Arto", klass.method(object, method)
                .returning(String.class).taking(LicensePlate.class).invoke(new LicensePlate("FI", "XX-999")));
    }

    @Test
    @Points("08-13.2")
    public void removeMethod() throws Throwable {
        String method = "remove";

        Object object = create();

        assertTrue("For the " + klassName + " class, implement the metohd: public boolean " + method + "(LicensePlate licensePlate) ",
                klass.method(object, method)
                .returning(boolean.class).taking(LicensePlate.class).isPublic());

        String v = "\nThis error was caused by trying:\n VehicleRegistry vr = new VehicleRegistry(); "
                + "vr.remove( new LicensePlate(\"FI\", \"AAA-111\"));";

        assertEquals("When called with a nonexistant plate, the remove method should return false. Check with:\n"
                + "VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.remove( new LicensePlate(\"FI\", \"AAA-111\"));",
                false, klass.method(object, method)
                .returning(boolean.class).taking(LicensePlate.class).withNiceError(v).invoke(new LicensePlate("FI", "AAA-111")));

        add(object, "FI", "AAA-111", "Arto");

        assertEquals("removing a plate that is in the registry should return true\n"
                + "VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "vr.remove( new LicensePlate(\"FI\", \"AAA-111\"));",
                true, klass.method(object, method)
                .returning(boolean.class).taking(LicensePlate.class).invoke(new LicensePlate("FI", "AAA-111")));
    }

    @Test
    @Points("08-13.2")
    public void addSearchRemove() throws Throwable {
        Object object = create();

        add(object, "FI", "AAA-111", "Arto");
        add(object, "FI", "BBB-222", "Pekka");

        String o = get(object, "FI", "AAA-111");

        String v = "VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "vr.add( new LicensePlate(\"FI\", \"BBB-222\"), \"Pekka\");\n";

        assertEquals(v + "vr.search(new LicensePlate(\"FI\", \"AAA-111\"));", "Arto", o);

        o = get(object, "FI", "BBB-222");

        assertEquals(v + "vr.search(new LicensePlate(\"FI\", \"BBB-222\"));\n", "Pekka", o);

        remove(object, "FI", "AAA-111");
        o = get(object, "FI", "AAA-111");

        assertEquals(v + "vr.remove(new LicensePlate(\"FI\", \"AAA-111\"));\n"
                + "vr.search(new LicensePlate(\"FI\", \"AAA-111\"));\n", null, o);
    }

    @Test
    @Points("08-13.3")
    public void printLicensePlatesMethod() throws Throwable {
        String method = "printLicensePlates";
        MockInOut io = new MockInOut("");
        Object object = create();

        add(object, "FI", "AAA-111", "Arto");
        add(object, "FI", "BBB-222", "Pekka");
        add(object, "FI", "CCC-333", "Jukka");

        String v = "VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "vr.add( new LicensePlate(\"FI\", \"BBB-222\"), \"Pekka\");\n"
                + "vr.add( new LicensePlate(\"FI\", \"CCC-333\"), \"Jukka\");\n"
                + "vr.printLicensePlates()";

        assertTrue("For the " + klassName + " class, implement the method: public void " + method + "() ",
                klass.method(object, method)
                .returningVoid().takingNoParams().isPublic());

        klass.method(object, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();

        String out = io.getOutput();
        assertTrue("The following code should have printed 3 lines:\n"+v+"\n instead you printed\n"+out,out.split("\n").length>2);

        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, out.contains("AAA-111"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, out.contains("BBB-222"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, out.contains("CCC-333"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, !out.contains("Arto"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, !out.contains("Pekka"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, !out.contains("Jukka"));
    }

    @Test
    @Points("08-13.3")
    public void printLicensePlatesMethod2() throws Throwable {
        String method = "printLicensePlates";
        MockInOut io = new MockInOut("");
        Object object = create();

        add(object, "FI", "AAA-111", "Arto");
        add(object, "FI", "BBB-222", "Pekka");
        add(object, "FI", "CCC-333", "Arto");

        String v = "VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "vr.add( new LicensePlate(\"FI\", \"BBB-222\"), \"Pekka\");\n"
                + "vr.add( new LicensePlate(\"FI\", \"CCC-333\"), \"Arto\");\n"
                + "vr.printLicensePlates()";

        assertTrue("For the " + klassName + " class, implement the method public void " + method + "() ",
                klass.method(object, method)
                .returningVoid().takingNoParams().isPublic());

        klass.method(object, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();

        String out = io.getOutput();

        assertTrue("The following code should have printed 3 lines:\n"+v+"\nyou printed\n"+out,out.split("\n").length>2);

        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, out.contains("AAA-111"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, out.contains("BBB-222"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, out.contains("CCC-333"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, !out.contains("Arto"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, !out.contains("Pekka"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, !out.contains("Arto"));
    }

    @Test
    @Points("08-13.3")
    public void printOwners() throws Throwable {
        String method = "printOwners";
        MockInOut io = new MockInOut("");
        Object object = create();

        add(object, "FI", "AAA-111", "Arto");
        add(object, "FI", "BBB-222", "Pekka");
        add(object, "FI", "CCC-333", "Arto");

        String v = "VehicleRegistry vr = new VehicleRegistry(); \n"
                + "vr.add( new LicensePlate(\"FI\", \"AAA-111\"), \"Arto\");\n"
                + "vr.add( new LicensePlate(\"FI\", \"BBB-222\"), \"Pekka\");\n"
                + "vr.add( new LicensePlate(\"FI\", \"CCC-333\"), \"Arto\");\n"
                + "vr.printOwners()";

        assertTrue("For the " + klassName + " class, implement the method: public void " + method + "() ",
                klass.method(object, method)
                .returningVoid().takingNoParams().isPublic());

        klass.method(object, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();

        String out = io.getOutput();

        assertTrue("The following code should have printed 2 lines:\n"+v+"\nyou printed\n"+out,out.split("\n").length>1);

        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, !out.contains("AAA-111"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, !out.contains("BBB-222"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, !out.contains("CCC-333"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, out.contains("Arto"));
        assertTrue("The print is incorrect with the code: \n"+v+"\nyou printed:\n"+out, out.contains("Pekka"));
        String jaa = out.substring( out.indexOf("Arto")+1 );
        assertFalse("The same name should not be repeated, print was incorrect when trying: "
                + "\n"+v+"\nyou printed:\n"+out, jaa.contains("Arto"));
    }

    /*
     *
     */
    public Object create() throws Throwable {
        klass = Reflex.reflect(klassName);
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    private void testEquals(String m1, String r1, String m2, String r2, boolean expected) {
        LicensePlate rr1 = new LicensePlate(m1, r1);
        LicensePlate rr2 = new LicensePlate(m2, r2);

        String v = "LicensePlate r1 = new LicensePlate(\"" + m1 + "\", \"" + r1 + "\");\n"
                + "LicensePlate r2 = new LicensePlate(\"" + m2 + "\", \"" + r2 + "\");\n"
                + "r1.equals(r2)";
        assertEquals(v, expected, rr1.equals(rr2));
    }

    private void testHash(String m1, String r1, String m2, String r2) {
        LicensePlate rr1 = new LicensePlate(m1, r1);
        LicensePlate rr2 = new LicensePlate(m2, r2);

        String v = "LicensePlate r1 = new LicensePlate(\"" + m1 + "\", \"" + r1 + "\");\n"
                + "LicensePlate r2 = new LicensePlate(\"" + m2 + "\", \"" + r2 + "\");\n"
                + "r2.hashCode() == r2.HashCode()";
        assertEquals(v, true, rr1.hashCode() == rr2.hashCode());
    }

    private void cleanlinessCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", From " + klassName + " class, remove the variable " + field(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("All instance variable for the class should be private but " + klassName + " has the instance variable: " + field(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("HTe " + klassName + "-class only need " + m + ", remove surplus", var <= n);
        }
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    private void add(Object olio, String maa, String rek, String om) throws Throwable {
        klass.method(olio, "add")
                .returning(boolean.class).taking(LicensePlate.class, String.class).invoke(new LicensePlate(maa, rek), om);
    }

    private void remove(Object olio, String maa, String rek) throws Throwable {
        klass.method(olio, "remove")
                .returning(boolean.class).taking(LicensePlate.class).invoke(new LicensePlate(maa, rek));
    }

    private String get(Object olio, String f, String r) throws Throwable {
        return klass.method(olio, "get")
                .returning(String.class).
                taking(LicensePlate.class).
                invoke(new LicensePlate(f, r));
    }
}
