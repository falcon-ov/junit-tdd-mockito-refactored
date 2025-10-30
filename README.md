# Clean Code & Refactoring Project

This repository is a solution to an assignment on **Clean Code + Refactoring**, demonstrating the application of software engineering best practices, SOLID principles, and comprehensive code restructuring.

## Project Overview

This project is a refactored and enhanced version of the Skills Management System. The refactoring focused on improving code quality, maintainability, testability, and adherence to clean code principles.

## Key Refactoring Changes

### 1. Separation of Concerns

**Before:**
- Business logic mixed with data access
- Validation embedded in model classes (`isValid()` methods)

**After:**
- **DAO Layer** (`org.example.dao`) - dedicated database access objects
- **Validation Layer** (`org.example.validation`) - separate validators with clear responsibilities
- **Service Layer** - business logic extracted from models and moved to services

### 2. Introduction of Interfaces

**Added Interfaces:**
- `Validator<T>` - generic validation interface
- `Reader<T>` - generic file reading interface
- `SkillSetService` - service contract for dependency inversion

**Benefits:**
- Improved testability through dependency injection
- Easier to swap implementations (e.g., InMemory vs Database repository)
- Follows Dependency Inversion Principle (SOLID)

### 3. Validation Refactoring

**Before:**
```java
public boolean isValid() {
    return level > 0 && level <= 10 && person != null && skill != null;
}
```

**After:**
```java
public List<String> validate(SkillSet skillSet) {
    List<String> errors = new ArrayList<>();
    if (skillSet.getLevel() <= 0 || skillSet.getLevel() > 10)
        errors.add("Level must be between 1 and 10");
    // ... more validations with descriptive error messages
    return errors;
}
```

**Improvements:**
- Detailed error messages instead of boolean flags
- Composition of validators (SkillSetValidator uses PersonValidator and SkillValidator)
- Single Responsibility Principle - validators only validate

### 4. Business Logic Extraction

**Before:**
- `getRating()` and `isGood()` methods in SkillSet model

**After:**
- Methods moved to `SkillSetService`
- Models contain only data and behavior directly related to the data structure
- Follows Single Responsibility Principle

### 5. Database Integration

**New Components:**
- `DatabaseConnection` - centralized database connection management
- `SkillSetDAO` - SQL operations with PreparedStatements to prevent SQL injection
- `DatabaseSkillSetRepository` - repository implementation for database persistence
- `application.properties` - externalized configuration

**Features:**
- Support for both in-memory and database storage
- Easy switching between implementations via dependency injection
- Proper resource management with try-with-resources

### 6. CSV Import Functionality

**New Components:**
- `Reader<T>` interface - contract for data readers
- `CsvSkillSetReader` - CSV parsing with validation
- Resources-based file loading

**Features:**
- Reads CSV from resources directory
- Line-by-line error reporting
- Automatic validation during import
- Configurable delimiter and header handling

### 7. Comparator Pattern

**New Component:**
- `SkillSetComparator` - implements `Comparator<SkillSet>`

**Benefits:**
- Sorting logic separated from domain models
- Reusable comparison logic
- Follows Strategy Pattern

### 8. Package Restructuring

**New Package Organization:**
```
org.example/
├── dao/              # Data Access Objects
├── db/               # Database connection utilities
├── io/               # Input/Output operations
├── model/            # Domain models (cleaned, no business logic)
├── repository/       # Repository pattern implementations
│   └── impl/
├── service/          # Business logic
│   └── impl/
└── validation/       # Validation logic
```

**Benefits:**
- Clear separation of concerns
- Easy to navigate
- Follows package-by-feature approach

### 9. Error Handling Improvements

**Before:**
- Silent failures or generic exceptions

**After:**
- Descriptive error messages in validation
- Proper SQLException handling in DAO/Repository layers
- Informative exceptions with context (line numbers in CSV parsing)

### 10. Code Quality Improvements

**Naming Conventions:**
- Descriptive method names
- Clear variable names
- Consistent naming patterns

**Constants:**
- Magic strings replaced with named constants (e.g., `DELIMITER`, `COL_PERSON_NAME`)
- Configuration externalized to properties files

**DRY Principle:**
- Eliminated code duplication (e.g., `setPersonParams()`, `setSkillParams()` in DAO)
- Reusable validation components

## Architecture

The project follows a layered architecture:

1. **Presentation Layer** - Main class with console output
2. **Service Layer** - Business logic and orchestration
3. **Repository Layer** - Data access abstraction
4. **DAO Layer** - Database operations
5. **Model Layer** - Domain objects
6. **Validation Layer** - Input validation
7. **IO Layer** - File operations

## Technologies

- **Java 21**
- **JDBC** - Database connectivity
- **MySQL** - Database (configurable)
- **JUnit 5** - Testing framework
- **Mockito 5** - Mocking framework
- **Maven** - Build tool

## Configuration

Database configuration in `src/main/resources/application.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/testdb
db.user=root
db.password=admin
```

## Usage Example
```java
// Setup with in-memory repository
Validator<SkillSet> validator = new SkillSetValidator();
SkillSetRepository repository = new InMemorySkillSetRepository();
SkillSetService service = new SkillSetServiceImpl(repository, validator);

// Import data from CSV
service.importFromCsv();

// Sort by rating
List<SkillSet> list = repository.getAll();
Collections.sort(list, new SkillSetComparator(service));

// Use database repository instead
Connection conn = DatabaseConnection.getConnection();
SkillSetDAO dao = new SkillSetDAO(conn);
SkillSetRepository dbRepository = new DatabaseSkillSetRepository(dao);
SkillSetService dbService = new SkillSetServiceImpl(dbRepository, validator);
```

## Running Tests
```bash
mvn test
```

## SOLID Principles Applied

1. **Single Responsibility** - Each class has one reason to change
2. **Open/Closed** - Open for extension (interfaces), closed for modification
3. **Liskov Substitution** - Repository implementations are interchangeable
4. **Interface Segregation** - Small, focused interfaces (Validator, Reader, Repository)
5. **Dependency Inversion** - Depend on abstractions, not concrete implementations

## Clean Code Principles Applied

- Meaningful names
- Functions do one thing
- DRY (Don't Repeat Yourself)
- Proper error handling
- Small, focused classes
- Separation of concerns
- Consistent formatting
- Descriptive comments only where necessary
