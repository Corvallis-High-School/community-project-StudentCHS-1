# Testing Guide for Community Project

This document explains how to use the autograding tests for this assignment.

## Files Included

- **CommunityProjectTest.java** - JUnit 6 test suite that validates all 8 requirements
- **.github/workflows/classroom.yml** - GitHub Actions workflow for automatic grading
- **.github/classroom/autograding.json** - Autograding configuration
- **run_tests.bat** - Windows script to run tests locally

## Test Coverage

The test suite covers all requirements from Requirements.md:

| Requirement | Tests | Points |
|-------------|-------|--------|
| R#1: Instance Variables | 2 tests (existence, privacy) | 3 |
| R#2: Constructor | 2 tests (existence, initialization) | 3 |
| R#3: Print Method | 2 tests (existence, output) | 3 |
| R#4: Accessor Methods | 2 tests (existence, functionality) | 3 |
| R#5: Mutator Methods | 2 tests (existence, functionality) | 3 |
| R#6: toString() Method | 2 tests (existence, content) | 3 |
| R#7: Additional Method | 2 tests (existence, execution) | 2 |
| R#8: Main Method | 2 tests (existence, output) | 10 |
| **TOTAL** | **16 tests** | **30** |

## Running Tests Locally

### Prerequisites

You need JUnit 6 JAR files in a `lib` folder:
1. Create a `lib` folder in your project directory
2. Download these files into the `lib` folder:
   - [junit-jupiter-api-6.0.1.jar](https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/6.0.1/junit-jupiter-api-6.0.1.jar)
   - [junit-jupiter-engine-6.0.1.jar](https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-engine/6.0.1/junit-jupiter-engine-6.0.1.jar)
   - [junit-platform-console-standalone-6.0.1.jar](https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/6.0.1/junit-platform-console-standalone-6.0.1.jar)

### Windows

Option 1: Use the batch script
```batch
run_tests.bat
```

Option 2: Manual commands
```batch
javac -cp .;lib/* *.java
java -jar lib/junit-platform-console-standalone-6.0.1.jar execute --class-path .;lib/junit-jupiter-api-6.0.1.jar --scan-class-path
```

### Mac/Linux

```bash
javac -cp .:lib/* *.java
java -jar lib/junit-platform-console-standalone-6.0.1.jar execute --class-path .:lib/junit-jupiter-api-6.0.1.jar --scan-class-path
```

## GitHub Classroom Autograding

When you push your code to GitHub, the autograding workflow will automatically:

1. Check out your code
2. Set up Java 17
3. Download JUnit dependencies
4. Compile your code
5. Run all tests
6. Report results with points earned

You can see the results in the "Actions" tab of your GitHub repository.

## Understanding Test Results

### Passing Tests ✅
- Green checkmark indicates the requirement is met
- Points are awarded

### Failing Tests ❌
- Red X indicates the requirement is not met
- Error message explains what's missing
- No points awarded for that test

## Common Issues

### "Class not found" errors
- Make sure your class is named exactly `CommunityProject`
- Check that the file is saved as `CommunityProject.java`

### "Method not found" errors
- Verify method names match exactly (e.g., `print()`, `toString()`)
- Check parameter types and counts

### "No valid constructor found"
- Ensure you have a constructor with at least 3 parameters
- Constructor must be public

### Tests compile but fail
- Read the error message carefully
- The test will tell you what it expected vs. what it found

## Test Details

### Reflection-Based Testing
The tests use Java Reflection to inspect your class structure without requiring specific implementation details. This means:
- You can name your instance variables anything you want
- You can choose any data types
- You can implement methods in any way that meets the requirements

### What the Tests Check

**Instance Variables (R#1)**
- Counts non-static fields
- Verifies they are private

**Constructor (R#2)**
- Checks for constructor with 3+ parameters
- Verifies it initializes instance variables

**Print Method (R#3)**
- Verifies method exists with correct signature
- Checks that it outputs to System.out

**Accessor Methods (R#4)**
- Counts methods starting with "get" or "is"
- Verifies they return non-null values
- Checks they have no parameters

**Mutator Methods (R#5)**
- Counts methods starting with "set"
- Verifies they accept one parameter
- Tests that setters update values (when getters exist)

**toString() Method (R#6)**
- Checks method exists and returns String
- Verifies output includes instance variable data

**Additional Method (R#7)**
- Finds non-standard methods with parameters
- Verifies it executes without errors

**Main Method (R#8)**
- Checks for public static void main(String[] args)
- Verifies it produces substantial output

## Tips for Success

1. **Run tests frequently** - Don't wait until you're done to test
2. **Read test names** - They tell you exactly what's being checked
3. **Fix one requirement at a time** - Start with R#1 and work through R#8
4. **Use meaningful names** - Makes your code easier to understand
5. **Test your own code** - Run your Main method to verify everything works

## Getting Help

If tests fail and you don't understand why:
1. Read the error message carefully
2. Check the specific requirement in Requirements.md
3. Verify your code matches the requirement
4. Run the test locally to see detailed output
5. Ask your teacher for clarification

## Grading

- GitHub Classroom will automatically calculate your score
- Maximum possible: 30 points
- Each requirement has specific point values
- All tests must pass to receive full credit
