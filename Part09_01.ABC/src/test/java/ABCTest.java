
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class ABCTest {

    String personClassName = "Person";
    String studentClassName = "Student";
    String teacherClassName = "Teacher";

    @Test
    @Points("09-01.1")
    public void classesABC() {
        Reflex.reflect("A").requirePublic();
        Reflex.reflect("B").requirePublic();
        Reflex.reflect("C").requirePublic();
    }

    @Test
    @Points("09-01.1")
    public void methodsABC() {
        Reflex.reflect("A").method("a").returningVoid().takingNoParams().requirePublic();
        Reflex.reflect("B").method("b").returningVoid().takingNoParams().requirePublic();
        Reflex.reflect("C").method("c").returningVoid().takingNoParams().requirePublic();
    }

    @Test
    @Points("09-01.1")
    public void onlyMethodsABC() {
        onlyOneMethod("A");
        onlyOneMethod("B");
        onlyOneMethod("C");
    }

    @Test
    @Points("09-01.2")
    public void bInheritsA() {
        Class c = ReflectionUtils.findClass("B");
        Class sc = c.getSuperclass();
        assertTrue("Class B must inherit class A", sc.getName().equals("A"));

    }

    @Test
    @Points("09-01.2")
    public void cInheritsB() {
        Class c = ReflectionUtils.findClass("C");
        Class sc = c.getSuperclass();
        assertTrue("Class C must inherit class B", sc.getName().equals("B"));
    }

    private void onlyOneMethod(String className) {
        int metoditLkm = Reflex.reflect(className).cls().getDeclaredMethods().length;
        assertTrue("Class " + className + " may only have one method defined in it. Currently there are " + metoditLkm, 1 == metoditLkm);
    }
}
