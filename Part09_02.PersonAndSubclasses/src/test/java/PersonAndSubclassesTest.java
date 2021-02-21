
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class PersonAndSubclassesTest {

    String personClassName = "Person";
    String studentClassName = "Student";
    String teacherClassName = "Teacher";

    @Test
    @Points("09-02.1")
    public void classPerson() {
        String className = personClassName;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(className);

        assertTrue("Class " + s(className) + " must be public, so it must be defined as \n"
                + "public class " + className + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("09-02.1")
    public void PersonHasNoExtraVariables() {
        String className = personClassName;
        sanityCheck(className, 2, "variables for name and address");
    }

    @Test
    @Points("09-02.1")
    public void personConstructorHasTwoVariables() throws Throwable {
        newPerson("Pekka", "Mannerheimintie");
    }

    @Test
    @Points("09-02.1")
    public void personToStringOverwritten() throws Throwable {
        Object h = newPerson("Pekka", "Mannerheimintie");

        assertFalse("write the toString method for the class Person", h.toString().contains("@"));
    }

    /*
     *
     */
    @Test
    @Points("09-02.1")
    public void personToStringOkay() throws Throwable {
        Object h = newPerson("Pekka", "Mannerheimintie");

        String[] aa = h.toString().split("\n");

        assertEquals("make sure the toString method in the Person class prints the exact string given in the task instructions\n"
                + "are there any \\n line breaks in the string provided by your toString method?", 2, h.toString().split("\n").length);

        assertTrue("make sure the toString method in the Person class prints the exact string given in the task instructions\n"
                + "the second row must have two spaces at front!", h.toString().split("\n")[1].startsWith("  "));
        assertFalse("make sure the toString method in the Person class prints the exact string given in the task instructions\n"
                + "the second row must have two spaces at front!", h.toString().split("\n")[1].startsWith("   "));

        assertTrue("make sure the toString method in the Person class prints the exact string given in the task instructions\n"
                + "leave no extra characters at the end of the rows", h.toString().split("\n")[0].endsWith("a"));
        assertTrue("make sure the toString method in the Person class prints the exact string given in the task instructions\n"
                + "leave no extra characters at the end of the rows", h.toString().split("\n")[1].endsWith("e"));

        String[] hlot = {"Pekka Mikkola;Mannerheimintie", "Joni Salmi;Korso;",
            "Esko Ukkonen;Westend"};

        for (String hlo : hlot) {
            String[] nimiOs = hlo.split(";");
            Object hh = newPerson(nimiOs[0], nimiOs[1]);
            assertTrue("make sure the toString method in the Person class prints the exact string given in the task instructions", hh.toString().contains(nimiOs[0]));
            assertTrue("make sure the toString method in the Person class prints the exact string given in the task instructions", hh.toString().contains(nimiOs[1]));
        }

    }

    //********************
    @Test
    @Points("09-02.2")
    public void classStudent() {
        String className = studentClassName;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(className);

        assertTrue("Class " + s(className) + " must be public, so it must be defined as\n"
                + "public class " + className + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("09-02.2")
    public void studentHasNoExtraVariables() {
        String className = studentClassName;
        sanityCheck(className, 1, "Study credit variable\n"
                + "name and address are inherited in the superclass. They can be used similarly to the"
                + "example where Engine inherits the class Part!");
    }

    @Test
    @Points("09-02.2")
    public void studentInheritsPerson() {
        Class c = ReflectionUtils.findClass(studentClassName);

        Class sc = c.getSuperclass();
        assertTrue("Student class must inherit the Person class", sc.getName().equals(personClassName));
    }

    @Test
    @Points("09-02.2")
    public void studentConstructorAcceptsTwoParameters() throws Throwable {
        newStudent("Ollie", "6381 Hollywood Blvd. Los Angeles 90028");
    }

    @Test
    @Points("09-02.2")
    public void studentHasCreditsMethod() throws Throwable {
        String method = "credits";
        String error = "add method public int credits() to Student class";

        String className = studentClassName;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(className);

        Object o = newStudent("Pekka", "Korso");

        assertTrue(error,
                classRef.method(o, method).returning(int.class).takingNoParams().isPublic());

        String v = "Student s = new Student(\"Pekka\",\"Korso\");\n"
                + "s.credits();\n";

        assertEquals(0, (int) classRef.method(o, method)
                .returning(int.class)
                .takingNoParams().withNiceError("Error was caused by\n" + v).invoke());
    }

    @Test
    @Points("09-02.2")
    public void studentHasStudyMethod() throws Throwable {
        String method = "study";
        String error = "add method void study() to Student class";

        String className = studentClassName;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(className);

        Object o = newStudent("Pekka", "Korso");

        assertTrue(error,
                classRef.method(o, method).returningVoid().takingNoParams().isPublic());

        String v = "Student s = new Student(\"Pekka\",\"Korso\");\n"
                + "s.study();\n";
        classRef.method(o, method).returningVoid().takingNoParams().withNiceError("error was caused by\n" + v).invoke();
    }

    @Test
    @Points("09-02.2")
    public void creditsIncrease() throws Throwable {
        String k = "Student s = new Student(\"Ollie\", \"6381 Hollywood Blvd. Los Angeles 90028\"); s.credits(); ";
        Object s = newStudent("Ollie", "6381 Hollywood Blvd. Los Angeles 90028");
        assertEquals("Test with the code " + k, 0, credits(s));

        k = "Student s = new Student(\"Ollie\", \"6381 Hollywood Blvd. Los Angeles 90028\"); s.study(); s.credits(); ";
        study(s);
        assertEquals("Test with the code " + k, 1, credits(s));

        k = "Student s = new Student(\"Ollie\", \"6381 Hollywood Blvd. Los Angeles 90028\"); s.study(); s.study(); s.study(); s.credits(); ";
        study(s);
        study(s);
        assertEquals("Test with the code " + k, 3, credits(s));
    }

    // **************
    @Test
    @Points("09-02.3")
    public void studentToStringOkay() throws Throwable {
        Object h = newStudent("Ollie", "6381 Hollywood Blvd. Los Angeles 90028");
        assertEquals("make sure the toString method in the Student class prints the exact string given in the task instructions\n"
                + "does your toString method provide two \\n line breaks \\n?", 3, h.toString().split("\n").length);

        assertTrue("make sure the toString method in the Student class prints the exact string given in the task instructions\n"
                + "the second row should start with two spaces!", h.toString().split("\n")[1].startsWith("  "));
        assertFalse("make sure the toString method in the Student class prints the exact string given in the task instructions\n"
                + "the second row should start with two spaces!", h.toString().split("\n")[1].startsWith("   "));
        assertTrue("make sure the toString method in the Student class prints the exact string given in the task instructions\n"
                + "the third rows should start with two spaces!", h.toString().split("\n")[2].startsWith("  "));
        assertFalse("make sure the toString method in the Student class prints the exact string given in the task instructions\n"
                + "the third rows should start with two spaces!", h.toString().split("\n")[2].startsWith("   "));

        String k = "Student s = new Student(\"Ollie\",\"6381 Hollywood Blvd. Los Angeles 90028\"); System.out.print(s)";

        String aa = h.toString();

        assertTrue("make sure the toString method in the Student class prints the exact string given in the task instructions\n"
                + "test with code " + k, h.toString().contains("Ollie"));
        assertTrue("make sure the toString method in the Student class prints the exact string given in the task instructions\n"
                + "test with code " + k, h.toString().contains("6381 Hollywood Blvd. Los Angeles 90028"));
        assertTrue("make sure the toString method in the Student class prints the exact string given in the task instructions\n"
                + "test with code " + k, h.toString().contains("credits 0"));

        k = "Student s = new Student(\"Ollie\",\"6381 Hollywood Blvd. Los Angeles 90028\"); s.study(); s.study(); System.out.print(s)";

        study(h);
        study(h);

        assertTrue("make sure that Student class' toString method provides the exact string specified by task instructions\n"
                + "test with code " + k, h.toString().contains("credits 2"));
    }

    // **************
    @Test
    @Points("09-02.4")
    public void classTeacher() {
        String className = teacherClassName;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(className);

        assertTrue("Class " + s(className) + " must be public, so it needs to be defined as\n"
                + "public class " + className + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("09-02.4")
    public void teacherHasNoExtraVariables() {
        String className = teacherClassName;
        sanityCheck(className, 1, "salary variable\n"
                + "Name and address are inherited from the superclass.");
    }

    @Test
    @Points("09-02.4")
    public void teacherInheritsPerson() {
        Class c = ReflectionUtils.findClass(teacherClassName);

        Class sc = c.getSuperclass();
        assertTrue("Teacher class must inherit the Person class", sc.getName().equals(personClassName));
    }

    @Test
    @Points("09-02.4")
    public void teacherConstructorHasThreeParameters() throws Throwable {
        newTeacher("Joel Kaasinen", "Haagantie 123", 980);
    }

    @Test
    @Points("09-02.4")
    public void teacherToStringOkay() throws Throwable {
        Object h = newTeacher("Joel Kaasinen", "Haagantie 123", 980);
        assertEquals("make sure the toString method in the Teacher class provides the exact string given in the task instructions\n"
                + "does your toString method provide two \\n line breaks?", 3, h.toString().split("\n").length);

        assertTrue("make sure the toString method in the Teacher class provides the exact string given in the task instructions\n"
                + "the second row should start with two spaces!", h.toString().split("\n")[1].startsWith("  "));
        assertFalse("make sure the toString method in the Teacher class provides the exact string given in the task instructions\n"
                + "the second row should start with two spaces!", h.toString().split("\n")[1].startsWith("   "));
        assertTrue("make sure the toString method in the Teacher class provides the exact string given in the task instructions\n"
                + "the third rows should start with two spaces!", h.toString().split("\n")[2].startsWith("  "));
        assertFalse("make sure the toString method in the Teacher class provides the exact string given in the task instructions\n"
                + "the third rows should start with two spaces!", h.toString().split("\n")[2].startsWith("   "));

        String k = "Teacher t = new Teacher(\"Joel Kaasinen\",\"Haagantie 123\", 980); System.out.print(t)";

        String aa = h.toString();

        assertTrue("make sure the toString method in the Teacher class provides the exact string given in the task instructions\n"
                + "test with code " + k, h.toString().contains("Joel Kaasinen"));
        assertTrue("make sure the toString method in the Teacher class provides the exact string given in the task instructions\n"
                + "test with code " + k, h.toString().contains("Haagantie 123"));
        assertTrue("make sure the toString method in the Teacher class provides the exact string given in the task instructions\n"
                + "testing with code " + k + " should print a row\n  salary 980 euro/month", h.toString().contains("salary 980 euro/month"));
    }
    // **************

    @Test
    @Points("09-02.5")
    public void printPersonsWithMethodInMain() throws Throwable {
        String method = "printPersons";

        String error = "Write a method public static void " + method + "(ArrayList<Person> persons) into the Main class";

        assertTrue(error, Reflex.reflect(Main.class).staticMethod(method).returningVoid().taking(ArrayList.class).isPublic());

        String v = "ArrayList<Person> list = new ArrayList<Person>();\n"
                + "list.add( new Student(\"Pekka\",\"Korso\") );\n"
                + "printPersons(list)\n";

        ArrayList list = new ArrayList();
        list.add(newStudent("Pekka", "Korso"));

        Reflex.reflect(Main.class).staticMethod(method).returningVoid().taking(ArrayList.class).withNiceError().invoke(list);

    }

    public void print(ArrayList list) throws Throwable {
        String method = "printPersons";

        Reflex.reflect(Main.class).staticMethod(method).returningVoid().taking(ArrayList.class).withNiceError().invoke(list);
    }

    @Test
    @Points("09-02.5")
    public void printPersonsWorks1() throws Exception, Throwable {
        MockInOut io = new MockInOut("");

        ArrayList a = new ArrayList();
        a.add(newStudent("Ollie", "6381 Hollywood Blvd. Los Angeles 90028"));
        a.add(newTeacher("Mikael Nousiainen", "Leppavaara", 3850));
        print(a);
        String output = io.getOutput();
        String v = "Does the method printPersons work as intended?\n"
                + "new Student((\"Ollie\", \"6381 Hollywood Blvd. Los Angeles 90028\") and new Teacher(\"Mikael Nousiainen\", \"Leppavaara\", 3850) were added to the list\n"
                + "and the method was invoked. Print was as follows: " + output;

        assertTrue(v, output.contains("Ollie"));
        assertTrue(v, output.contains("6381 Hollywood Blvd. Los Angeles 90028"));
        assertTrue(v, output.contains("Leppavaara"));
        assertTrue(v, output.contains("Mikael Nousiainen"));
        assertFalse(v, output.contains("Oskari"));
        assertFalse(v, output.contains("Domus Academica"));
        assertFalse(v, output.contains("Korso"));
        assertFalse(v, output.contains("Pekka Mikkola"));
    }

    @Test
    @Points("09-02.5")
    public void printPersonsWorks2() throws Exception, Throwable {
        MockInOut io = new MockInOut("");
        ArrayList a = new ArrayList();
        a.add(newStudent("Oskari", "Domus Academica"));
        a.add(newTeacher("Pekka Mikkola", "Korso", 1105));
        print(a);
        String output = io.getOutput();
        String v = "Does the method printPersons work as intended?\n"
                + "new Student(\"Oskari\", \"Domus Academica\") and "
                + "new Teacher(\"Pekka Mikkola\", \"Korso\", 1105)\n were added to the list, then the method was invoked. Print was as follows: " + output;

        assertTrue(v, output.contains("Oskari"));
        assertTrue(v, output.contains("Domus Academica"));
        assertTrue(v, output.contains("Korso"));
        assertTrue(v, output.contains("Pekka Mikkola"));
        assertFalse(v, output.contains("Ollie"));
        assertFalse(v, output.contains("6381 Hollywood Blvd. Los Angeles 90028"));
        assertFalse(v, output.contains("Leppavaara"));
        assertFalse(v, output.contains("Mikael Nousiainen"));
    }

    /*
     *
     */
    private Object newPerson(String name, String address) throws Throwable {
        String className = personClassName;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(className);

        assertTrue("Write the following constructor to the Person class\npublic Person(String name, String address)", classRef.constructor().taking(String.class, String.class).isPublic());

        Reflex.MethodRef2<Object, Object, String, String> ctor = classRef.constructor().taking(String.class, String.class).withNiceError();
        return ctor.invoke(name, address);
    }

    private Object newStudent(String name, String address) throws Throwable {
        String className = studentClassName;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(className);

        assertTrue("Write the following constructor for the Student class\n Student(String name, String address)", classRef.constructor().taking(String.class, String.class).isPublic());

        Reflex.MethodRef2<Object, Object, String, String> ctor = classRef.constructor().taking(String.class, String.class).withNiceError();
        return ctor.invoke(name, address);

    }

    private Object newTeacher(String name, String address, int salary) throws Throwable {
        String className = teacherClassName;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(className);

        assertTrue("Write the following constructor for the Teacher class\npublic Teacher(String name, String address, int salary)",
                classRef.constructor().taking(String.class, String.class, int.class).isPublic());

        Reflex.MethodRef3<Object, Object, String, String, Integer> ctor = classRef
                .constructor().taking(String.class, String.class, int.class).withNiceError();
        return ctor.invoke(name, address, salary);
    }

    private void study(Object olio) throws Throwable {
        String className = studentClassName;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(className);
        classRef.method(olio, "study").returningVoid().takingNoParams().invoke();
    }

    private int credits(Object obj) throws Throwable {
        String className = studentClassName;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(className);

        return classRef.method(obj, "credits").returning(int.class).takingNoParams().invoke();
    }

    private String toS(String[] nimiOs) {
        return nimiOs[0] + "\n  " + nimiOs[1];
    }

    /*
     *
     */
    private void sanityCheck(String className, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(className).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("You don't need 'static variables', please see class " + s(className) + " and remove variable " + field(field.toString(), s(className)), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("All the variables in the class should be spefied as private. Class " + s(className) + " has: " + field(field.toString(), className), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("Class " + s(className) + " only needs " + m + ", please remove any extra variables ", var <= n);
        }
    }

    private String field(String toString, String className) {
        return toString.replace(className + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    private String s(String className) {
        if (!className.contains(".")) {
            return className;
        }

        return className.substring(className.lastIndexOf(".") + 1);
    }
}
