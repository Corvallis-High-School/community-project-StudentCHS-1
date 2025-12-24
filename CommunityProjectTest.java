import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.lang.reflect.*;

/**
 * Test suite for the Community Project Assignment
 * Tests all requirements specified in Requirements.md
 */
public class CommunityProjectTest {
    
    private Class<?> projectClass;
    private static final int MIN_INSTANCE_VARIABLES = 3;
    
    @BeforeEach
    public void setUp() {
        try {
            projectClass = Class.forName("CommunityProject");
        } catch (ClassNotFoundException e) {
            fail("CommunityProject class not found");
        }
    }
    
    // ==================== R#1: Instance Variables (3 points) ====================
    
    @Test
    @DisplayName("R#1: Class has at least 3 instance variables")
    public void testInstanceVariables() {
        Field[] allFields = projectClass.getDeclaredFields();
        int instanceVarCount = 0;
        
        for (Field field : allFields) {
            int modifiers = field.getModifiers();
            // Count non-static fields as instance variables
            if (!Modifier.isStatic(modifiers)) {
                instanceVarCount++;
            }
        }
        
        assertTrue(instanceVarCount >= MIN_INSTANCE_VARIABLES, 
            String.format("Expected at least %d instance variables, found %d", 
                MIN_INSTANCE_VARIABLES, instanceVarCount));
    }
    
    @Test
    @DisplayName("R#1: Instance variables are private")
    public void testInstanceVariablesArePrivate() {
        Field[] allFields = projectClass.getDeclaredFields();
        int instanceVarCount = 0;
        
        for (Field field : allFields) {
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                assertTrue(Modifier.isPrivate(modifiers), 
                    "Instance variable '" + field.getName() + "' should be private");
                instanceVarCount++;
            }
        }
        
        assertTrue(instanceVarCount >= MIN_INSTANCE_VARIABLES, 
            "Need at least " + MIN_INSTANCE_VARIABLES + " instance variables to test");
    }
    
    // ==================== R#2: Constructor (3 points) ====================
    
    @Test
    @DisplayName("R#2: Class has a constructor with at least 3 parameters")
    public void testConstructorExists() {
        Constructor<?>[] constructors = projectClass.getConstructors();
        boolean hasValidConstructor = false;
        
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() >= 3) {
                hasValidConstructor = true;
                break;
            }
        }
        
        assertTrue(hasValidConstructor, 
            "Class must have at least one constructor with at least 3 parameters");
    }
    
    @Test
    @DisplayName("R#2: Constructor properly initializes instance variables")
    public void testConstructorInitialization() throws Exception {
        Constructor<?>[] constructors = projectClass.getConstructors();
        Constructor<?> validConstructor = null;
        
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() >= 3) {
                validConstructor = constructor;
                break;
            }
        }
        
        assertNotNull(validConstructor, "No valid constructor found");
        
        // Create test values based on parameter types
        Object[] testValues = createTestValues(validConstructor.getParameterTypes());
        Object instance = validConstructor.newInstance(testValues);
        
        // Verify at least 3 instance variables are set
        Field[] fields = projectClass.getDeclaredFields();
        int initializedCount = 0;
        
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                Object value = field.get(instance);
                if (value != null && !value.equals(0) && !value.equals(0.0) && !value.equals(false)) {
                    initializedCount++;
                }
            }
        }
        
        assertTrue(initializedCount >= 3, 
            "Constructor should initialize at least 3 instance variables");
    }
    
    // ==================== R#3: Print Method (3 points) ====================
    
    @Test
    @DisplayName("R#3: Class has a print() method")
    public void testPrintMethodExists() {
        boolean hasPrintMethod = false;
        
        try {
            Method printMethod = projectClass.getMethod("print");
            assertEquals(void.class, printMethod.getReturnType(), 
                "print() method should return void");
            hasPrintMethod = true;
        } catch (NoSuchMethodException e) {
            fail("Class must have a print() method with no parameters");
        }
        
        assertTrue(hasPrintMethod);
    }
    
    @Test
    @DisplayName("R#3: print() method uses System.out.println")
    public void testPrintMethodPrints() throws Exception {
        Object instance = createTestInstance();
        Method printMethod = projectClass.getMethod("print");
        
        // Capture System.out
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        try {
            printMethod.invoke(instance);
            String output = outputStream.toString();
            assertTrue(output.length() > 0, 
                "print() method should output something to System.out");
        } finally {
            System.setOut(originalOut);
        }
    }
    
    // ==================== R#4: Accessor Methods (3 points) ====================
    
    @Test
    @DisplayName("R#4: Class has at least 3 getter methods")
    public void testGetterMethodsExist() {
        Method[] methods = projectClass.getMethods();
        int getterCount = 0;
        
        for (Method method : methods) {
            String methodName = method.getName();
            if ((methodName.startsWith("get") || methodName.startsWith("is")) && 
                method.getParameterCount() == 0 &&
                method.getReturnType() != void.class &&
                !Modifier.isStatic(method.getModifiers())) {
                getterCount++;
            }
        }
        
        assertTrue(getterCount >= MIN_INSTANCE_VARIABLES, 
            String.format("Expected at least %d getter methods, found %d", 
                MIN_INSTANCE_VARIABLES, getterCount));
    }
    
    @Test
    @DisplayName("R#4: Getter methods return correct values")
    public void testGetterMethodsWork() throws Exception {
        Object instance = createTestInstance();
        Method[] methods = projectClass.getMethods();
        int workingGetters = 0;
        
        for (Method method : methods) {
            String methodName = method.getName();
            if ((methodName.startsWith("get") || methodName.startsWith("is")) && 
                method.getParameterCount() == 0 &&
                method.getReturnType() != void.class &&
                !Modifier.isStatic(method.getModifiers())) {
                
                Object value = method.invoke(instance);
                // Getter should return a non-null value (assuming constructor initialized it)
                assertNotNull(value, 
                    "Getter method " + methodName + "() should return a non-null value");
                workingGetters++;
            }
        }
        
        assertTrue(workingGetters >= MIN_INSTANCE_VARIABLES, 
            "At least " + MIN_INSTANCE_VARIABLES + " working getter methods required");
    }
    
    // ==================== R#5: Mutator Methods (3 points) ====================
    
    @Test
    @DisplayName("R#5: Class has at least 3 setter methods")
    public void testSetterMethodsExist() {
        Method[] methods = projectClass.getMethods();
        int setterCount = 0;
        
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("set") && 
                method.getParameterCount() == 1 &&
                method.getReturnType() == void.class &&
                !Modifier.isStatic(method.getModifiers())) {
                setterCount++;
            }
        }
        
        assertTrue(setterCount >= MIN_INSTANCE_VARIABLES, 
            String.format("Expected at least %d setter methods, found %d", 
                MIN_INSTANCE_VARIABLES, setterCount));
    }
    
    @Test
    @DisplayName("R#5: Setter methods properly update instance variables")
    public void testSetterMethodsWork() throws Exception {
        Object instance = createTestInstance();
        Method[] methods = projectClass.getMethods();
        int workingSetters = 0;
        
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("set") && 
                method.getParameterCount() == 1 &&
                method.getReturnType() == void.class &&
                !Modifier.isStatic(method.getModifiers())) {
                
                Class<?> paramType = method.getParameterTypes()[0];
                Object testValue = createTestValue(paramType);
                
                // Call setter
                method.invoke(instance, testValue);
                
                // Try to find corresponding getter
                String getterName = "get" + methodName.substring(3);
                try {
                    Method getter = projectClass.getMethod(getterName);
                    Object retrievedValue = getter.invoke(instance);
                    assertEquals(testValue, retrievedValue, 
                        "Setter " + methodName + "() should update the value returned by " + getterName + "()");
                    workingSetters++;
                } catch (NoSuchMethodException e) {
                    // If no matching getter, just verify setter doesn't throw exception
                    workingSetters++;
                }
            }
        }
        
        assertTrue(workingSetters >= MIN_INSTANCE_VARIABLES, 
            "At least " + MIN_INSTANCE_VARIABLES + " working setter methods required");
    }
    
    // ==================== R#6: toString() Method (3 points) ====================
    
    @Test
    @DisplayName("R#6: Class has a toString() method")
    public void testToStringMethodExists() {
        try {
            Method toStringMethod = projectClass.getMethod("toString");
            assertEquals(String.class, toStringMethod.getReturnType(), 
                "toString() method should return a String");
        } catch (NoSuchMethodException e) {
            fail("Class must have a toString() method");
        }
    }
    
    @Test
    @DisplayName("R#6: toString() returns all instance variable information")
    public void testToStringReturnsInfo() throws Exception {
        Object instance = createTestInstance();
        Method toStringMethod = projectClass.getMethod("toString");
        
        String result = (String) toStringMethod.invoke(instance);
        
        assertNotNull(result, "toString() should not return null");
        assertTrue(result.length() > 10, 
            "toString() should return meaningful information (more than 10 characters)");
        
        // Verify toString includes data from instance variables
        Field[] fields = projectClass.getDeclaredFields();
        int fieldsRepresented = 0;
        
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                Object value = field.get(instance);
                if (value != null && result.contains(value.toString())) {
                    fieldsRepresented++;
                }
            }
        }
        
        assertTrue(fieldsRepresented >= 2, 
            "toString() should include information from at least 2 instance variables");
    }
    
    // ==================== R#7: Additional Method (2 points) ====================
    
    @Test
    @DisplayName("R#7: Class has at least one additional method with a parameter")
    public void testAdditionalMethodExists() {
        Method[] methods = projectClass.getMethods();
        boolean hasAdditionalMethod = false;
        
        for (Method method : methods) {
            String methodName = method.getName();
            // Exclude standard methods (getters, setters, toString, print, Object methods)
            if (!methodName.startsWith("get") && 
                !methodName.startsWith("set") && 
                !methodName.startsWith("is") &&
                !methodName.equals("toString") &&
                !methodName.equals("print") &&
                !methodName.equals("hashCode") &&
                !methodName.equals("equals") &&
                !methodName.equals("clone") &&
                !methodName.equals("finalize") &&
                !methodName.equals("getClass") &&
                !methodName.equals("notify") &&
                !methodName.equals("notifyAll") &&
                !methodName.equals("wait") &&
                method.getParameterCount() >= 1 &&
                !Modifier.isStatic(method.getModifiers())) {
                hasAdditionalMethod = true;
                break;
            }
        }
        
        assertTrue(hasAdditionalMethod, 
            "Class must have at least one additional method that takes at least one parameter");
    }
    
    @Test
    @DisplayName("R#7: Additional method executes without errors")
    public void testAdditionalMethodWorks() throws Exception {
        Object instance = createTestInstance();
        Method[] methods = projectClass.getMethods();
        boolean methodExecuted = false;
        
        for (Method method : methods) {
            String methodName = method.getName();
            if (!methodName.startsWith("get") && 
                !methodName.startsWith("set") && 
                !methodName.startsWith("is") &&
                !methodName.equals("toString") &&
                !methodName.equals("print") &&
                !methodName.equals("hashCode") &&
                !methodName.equals("equals") &&
                !methodName.equals("clone") &&
                !methodName.equals("finalize") &&
                !methodName.equals("getClass") &&
                !methodName.equals("notify") &&
                !methodName.equals("notifyAll") &&
                !methodName.equals("wait") &&
                method.getParameterCount() >= 1 &&
                !Modifier.isStatic(method.getModifiers())) {
                
                Object[] params = createTestValues(method.getParameterTypes());
                try {
                    method.invoke(instance, params);
                    methodExecuted = true;
                    break;
                } catch (Exception e) {
                    fail("Additional method " + methodName + "() threw an exception: " + e.getCause());
                }
            }
        }
        
        assertTrue(methodExecuted, "Additional method should execute without errors");
    }
    
    // ==================== R#8: Main Method Testing (10 points) ====================
    
    @Test
    @DisplayName("R#8: Main method exists in CommunityProject or Main class")
    public void testMainMethodExists() {
        boolean hasMain = false;
        
        // Check CommunityProject class
        try {
            Method mainMethod = projectClass.getMethod("main", String[].class);
            if (Modifier.isStatic(mainMethod.getModifiers()) && 
                Modifier.isPublic(mainMethod.getModifiers())) {
                hasMain = true;
            }
        } catch (NoSuchMethodException e) {
            // Try Main class
            try {
                Class<?> mainClass = Class.forName("Main");
                Method mainMethod = mainClass.getMethod("main", String[].class);
                if (Modifier.isStatic(mainMethod.getModifiers()) && 
                    Modifier.isPublic(mainMethod.getModifiers())) {
                    hasMain = true;
                }
            } catch (Exception ex) {
                // Main method not found in either class
            }
        }
        
        assertTrue(hasMain, "Must have a public static void main(String[] args) method");
    }
    
    @Test
    @DisplayName("R#8: Main method creates at least 2 objects and produces output")
    public void testMainMethodProducesOutput() throws Exception {
        Method mainMethod = null;
        
        // Find main method
        try {
            mainMethod = projectClass.getMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                Class<?> mainClass = Class.forName("Main");
                mainMethod = mainClass.getMethod("main", String[].class);
            } catch (Exception ex) {
                fail("No main method found");
            }
        }
        
        // Capture System.out
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        try {
            mainMethod.invoke(null, (Object) new String[]{});
            String output = outputStream.toString();
            
            assertTrue(output.length() > 50, 
                "Main method should produce substantial output (testing at least 2 objects)");
        } finally {
            System.setOut(originalOut);
        }
    }
    
    // ==================== Helper Methods ====================
    
    /**
     * Creates a test instance of the CommunityProject class using reflection
     */
    private Object createTestInstance() throws Exception {
        Constructor<?>[] constructors = projectClass.getConstructors();
        
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() >= 3) {
                Object[] params = createTestValues(constructor.getParameterTypes());
                return constructor.newInstance(params);
            }
        }
        
        // If no parameterized constructor, try no-arg constructor
        try {
            return projectClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new Exception("Cannot create test instance - no valid constructor found");
        }
    }
    
    /**
     * Creates test values for an array of parameter types
     */
    private Object[] createTestValues(Class<?>[] types) {
        Object[] values = new Object[types.length];
        
        for (int i = 0; i < types.length; i++) {
            values[i] = createTestValue(types[i]);
        }
        
        return values;
    }
    
    /**
     * Creates a test value for a specific type
     */
    private Object createTestValue(Class<?> type) {
        if (type == String.class) {
            return "TestValue" + System.currentTimeMillis();
        } else if (type == int.class || type == Integer.class) {
            return 42;
        } else if (type == double.class || type == Double.class) {
            return 3.14;
        } else if (type == boolean.class || type == Boolean.class) {
            return true;
        } else if (type == long.class || type == Long.class) {
            return 100L;
        } else if (type == float.class || type == Float.class) {
            return 2.5f;
        } else if (type == char.class || type == Character.class) {
            return 'A';
        } else if (type == byte.class || type == Byte.class) {
            return (byte) 1;
        } else if (type == short.class || type == Short.class) {
            return (short) 10;
        } else {
            // For unknown types, try to create instance
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                return null;
            }
        }
    }
}
