
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

@Points("08-10")
public class IOUTest {

    String classToTest = "IOU";
    Reflex.ClassRef<Object> classObjectToTest;

    @Before
    public void setup() {
        classObjectToTest = Reflex.reflect(classToTest);
    }

    @Test
    public void classIsPublic() {
        assertTrue("Class " + classToTest + " must be public, defined\npublic class " + classToTest + " {...\n}", classObjectToTest.isPublic());
    }

    @Test
    public void noExtraVariables() {
        sanitaryCheck(classToTest, 1, " HashMap<String, Double> for saving the debt amounts and who the debt is to");
    }

    @Test
    public void usesAHashMap() {
        Field[] variablesInClass = ReflectionUtils.findClass(classToTest).getDeclaredFields();
        assertTrue("Add to class " + classToTest + " object variable HashMap<String, Double> ", variablesInClass.length == 1);
        assertTrue("The class " + classToTest + " must have HashMap<String, Double> object variable", variablesInClass[0].toString().contains("HashMap"));
    }

    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = classObjectToTest.constructor().takingNoParams().withNiceError();
        assertTrue("Add to class " + classToTest + " public constructor: public " + classToTest + "()", ctor.isPublic());
        String errorMessage = "error caused by code: new IOU();";
        ctor.withNiceError(errorMessage).invoke();
    }

    public Object createNewIOU() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = classObjectToTest.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }

    @Test
    public void testSetSum() throws Throwable {
        String methodToTest = "setSum";

        Object IOUObject = createNewIOU();

        assertTrue("Add to class " + classToTest + " metod public void " + methodToTest + "(String toWhom, double amount) ",
                classObjectToTest.method(IOUObject, methodToTest)
                        .returningVoid().taking(String.class, double.class).isPublic());

        String v = "\nerror caused by: IOU myDebts = new IOU(); "
                + "v.setSum(\"Paul\", 5.0);";

        classObjectToTest.method(IOUObject, methodToTest)
                .returningVoid().taking(String.class, double.class).withNiceError(v).invoke("Paul", 5.0);
    }

    @Test
    public void testHowMuchDoIOweTo() throws Throwable {
        String methodToTest = "howMuchDoIOweTo";

        Object IOUObject = createNewIOU();

        assertTrue("Add to class " + classToTest + " metod public double " + methodToTest + "(String toWhom) ",
                classObjectToTest.method(IOUObject, methodToTest)
                        .returning(double.class).taking(String.class).isPublic());

        String v = "\nerror caused by: IOU myDebts = new IOU(); "
                + "myDebts.howMuchDoIOweTo(\"Paul\");";

        classObjectToTest.method(IOUObject, methodToTest)
                .returning(double.class).taking(String.class).withNiceError(v).invoke("Paul");
    }

    @Test
    public void testIOU() throws Exception {
        Object ioweyou = makeNewIOU();
        testsetSum(ioweyou, "Arthur", 919.83);
        testsetSum(ioweyou, "Matt", 32.1);
        testsetSum(ioweyou, "Joel", -5);
        IOUTest.this.testSetSum2(ioweyou, "Michael");
    }

    private void testsetSum(Object IOU, String toWhom, double amount) {
        testSetSum2(IOU, toWhom, amount);
        double amountOfDebt = howMuchDebt(IOU, toWhom);
        if (amountOfDebt <= (amount - 0.1) || amountOfDebt >= (amount + 0.1)) {
            fail("Debt of " + amount + " was owed to " + toWhom
                    + ", but the amount owed was printed to be: " + amountOfDebt);
        }
    }

    private void testSetSum2(Object IOU, String toWhom) {
        double amountOfDebt = howMuchDebt(IOU, toWhom);
        if (amountOfDebt != 0) {
            fail("There was no debt to " + toWhom
                    + ", but the amount owed was printed to be: " + amountOfDebt);
        }
    }

    private Object makeNewIOU() throws Exception {
        return Class.forName("IOU").getConstructor().newInstance();
    }

    private void testSetSum2(Object IOU, String toWhom, double amount) {
        Method methodToTest;
        try {
            methodToTest = IOU.getClass().getMethod("setSum", String.class, double.class);
        } catch (Exception e) {
            fail("IOU-class does not have the method: public void setSum(String toWhom, double amount).");
            return;
        }

        if (!methodToTest.getReturnType().equals(void.class)) {
            fail("IOU-class method setSum(String toWhom, double amount) should not return anything.");
            return;
        }

        try {
            methodToTest.invoke(IOU, toWhom, amount);
        } catch (Exception e) {
            fail("IOU-class setSum-method caused an error: " + e.toString());
        }
    }

    private double howMuchDebt(Object IOU, String toWhom) {
        Method methodToTest;
        try {
            methodToTest = IOU.getClass().getMethod("howMuchDoIOweTo", String.class);
        } catch (Exception e) {
            fail("IOU-class does not have the method: public double howMuchDoIOweTo(String toWhom).");
            return -1;
        }

        if (!methodToTest.getReturnType().equals(double.class)) {
            fail("IOU-class method howMuchDoIOweTo(String toWhom) should return a double.");
            return -1;
        }

        try {
            return (Double) methodToTest.invoke(IOU, toWhom);
        } catch (java.lang.reflect.InvocationTargetException e) {
            fail("Make sure that the program does not try to change null-references to value type variables.");
            return -1;
        } catch (Exception e) {
            fail("IOU-class howMuchDoIOweTo-method caused an error: " + e.toString());
            return -1;
        }
    }

    private void sanitaryCheck(String nameOfClass, int howManyVariablesShouldHave, String explanationOfNeededVariables) throws SecurityException {
        Field[] variablesInClass = ReflectionUtils.findClass(nameOfClass).getDeclaredFields();

        for (Field variable : variablesInClass) {
            assertFalse("You do not need \"static variables\", remove from class" + nameOfClass + " variable " + field(variable.toString(), nameOfClass), variable.toString().contains("static") && !variable.toString().contains("final"));
            assertTrue("All class variables should be private, but class " + nameOfClass + " has: " + field(variable.toString(), nameOfClass), variable.toString().contains("private"));
        }

        if (variablesInClass.length > 1) {
            int var = 0;
            for (Field variable : variablesInClass) {
                if (!variable.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("You do not need " + nameOfClass + " other variables than " + explanationOfNeededVariables + ", remove the extras", var <= howManyVariablesShouldHave);
        }
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
