# 🎬 CineMatch - Intelligent Movie Recommendation System

<div align="center">

![Java](https://img.shields.io/badge/Java-11+-orange?style=for-the-badge&logo=java)
![JUnit](https://img.shields.io/badge/JUnit-5.9.3-green?style=for-the-badge&logo=junit5)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)
![Build](https://img.shields.io/badge/Build-Passing-success?style=for-the-badge)

**A robust, genre-based movie recommendation engine with comprehensive validation and testing**

[Features](#-features) • [Installation](#-installation) • [Usage](#-usage) • [Testing](#-testing) • [Documentation](#-documentation)

</div>

---

## 📖 About

**CineMatch** is an intelligent movie recommendation system developed as part of the Software Testing course (CSE337s) at Ain Shams University. The system analyzes user preferences and generates personalized movie recommendations based on genre matching, with a strong emphasis on input validation, error handling, and comprehensive unit testing.

### 🎯 Project Goals

- Build a production-ready recommendation engine with strict validation rules
- Implement comprehensive unit testing achieving 90+ test cases
- Apply software testing methodologies (white box, black box, data flow, integration)
- Demonstrate best practices in Java development and test-driven development

---

## ✨ Features

### Core Functionality
- 🎭 **Genre-Based Recommendations** - Intelligent movie suggestions based on liked genres
- ✅ **Strict Input Validation** - Comprehensive validation for all input data
- 🔍 **Duplicate Detection** - Prevents duplicate movie recommendations
- 📊 **Multi-Genre Support** - Handles movies with multiple genres seamlessly
- 🚫 **Error-First Approach** - Detects and reports the first validation error encountered

### Validation Rules
- **Movie Titles**: Must have each word starting with a capital letter
- **Movie IDs**: Capital letters from title + 3 unique digits (e.g., "The Dark Knight" → "TDK123")
- **User Names**: Alphabetic characters and spaces only, cannot start with space
- **User IDs**: Exactly 9 alphanumeric characters, starts with digits, optionally ends with one letter
- **Uniqueness**: All user IDs must be unique across the system

### Technical Highlights
- 🏗️ **Clean Architecture** - Separation of concerns with models, validators, parsers, and services
- 🧪 **90+ Unit Tests** - Comprehensive test coverage using JUnit 5
- 🐛 **Bug Tracking** - Integration with Jira/Bugzilla for issue management
- 📝 **Test Documentation** - Detailed test case templates and reports

---

## 🏗️ Architecture

```
CineMatch/
├── src/
│   ├── models/              # Data models (Movie, User, UserRecommendation)
│   ├── exceptions/          # Custom exception hierarchy
│   ├── validators/          # Business logic validation
│   ├── parsers/             # File parsing and validation orchestration
│   ├── services/            # Recommendation engine and file writing
│   └── MovieRecommendationApp.java
├── test/
│   ├── validators/          # Validator unit tests
│   ├── parsers/             # Parser unit tests
│   └── services/            # Service unit tests
├── movies.txt               # Input: Movie database
├── users.txt                # Input: User preferences
└── recommendations.txt      # Output: Generated recommendations
```

### Design Patterns
- **Separation of Concerns**: Clear separation between validation, parsing, and business logic
- **Exception Hierarchy**: Custom exceptions for detailed error reporting
- **Data Structures**: Efficient HashMap and HashSet usage for O(1) lookups
- **Single Responsibility**: Each class has one clear purpose

---

## 🚀 Installation

### Prerequisites
- Java 11 or higher
- JUnit 5.9.3 (for testing)
- Maven (optional, for dependency management)
- Git

### Clone Repository
```bash
git clone https://github.com/yourusername/cinematch.git
cd cinematch
```

### Option 1: Using IntelliJ IDEA (Recommended)
1. Open IntelliJ IDEA
2. File → Open → Select the `cinematch` folder
3. Right-click on `src` folder → Mark Directory as → Sources Root
4. Right-click on `test` folder → Mark Directory as → Test Sources Root
5. IntelliJ will automatically detect and download JUnit dependencies

### Option 2: Using Maven
```bash
mvn clean install
mvn test
```

### Option 3: Manual Compilation
```bash
# Create bin directory
mkdir bin

# Compile source files
javac -d bin src/models/*.java src/exceptions/*.java src/validators/*.java src/parsers/*.java src/services/*.java src/MovieRecommendationApp.java

# Run the application
java -cp bin MovieRecommendationApp
```

---

## 💻 Usage

### Input File Formats

#### movies.txt
```
The Dark Knight,TDK123
action,thriller
Inception,I456
action,sci-fi,thriller
The Shawshank Redemption,TSR789
drama
```

**Format Rules:**
- Line 1: Movie Title, Movie ID (comma-separated)
- Line 2: Genres (comma-separated)
- Repeat for each movie

#### users.txt
```
John Smith,123456789
TDK123,I456
Alice Johnson,987654321
TSR789
```

**Format Rules:**
- Line 1: User Name, User ID (comma-separated)
- Line 2: Liked Movie IDs (comma-separated)
- Repeat for each user

### Running the Application

```bash
# Ensure movies.txt and users.txt are in the project root
java -cp bin MovieRecommendationApp
```

### Output

#### Success Case (recommendations.txt)
```
John Smith,123456789
Inception,The Godfather,Interstellar
Alice Johnson,987654321
The Dark Knight,The Godfather
```

#### Error Case (recommendations.txt)
```
ERROR: Movie Title the dark knight is wrong
```

---

## 🧪 Testing

### Running Tests

#### IntelliJ IDEA
```
Right-click on test/ directory → Run 'All Tests'
```

#### Maven
```bash
mvn test
```

#### Command Line
```bash
javac -cp "lib/*:bin" -d test-bin test/**/*.java
java -jar lib/junit-platform-console-standalone.jar --class-path "bin:test-bin" --scan-class-path
```

### Test Coverage

| Test Suite | Tests | Coverage |
|------------|-------|----------|
| MovieValidator | 20 | Title format, ID validation, digit uniqueness |
| UserValidator | 25 | Name format, ID validation, uniqueness checking |
| MovieParser | 12 | File parsing, error detection, format validation |
| UserParser | 18 | File parsing, duplicate detection, format validation |
| RecommendationEngine | 8 | Genre matching, deduplication, edge cases |
| RecommendationWriter | 11 | File output, error reporting |
| **Total** | **94** | **Comprehensive coverage** |

### Test Results
```
✅ All 94 tests passing
✅ 100% validation coverage
✅ Edge cases handled
✅ Error scenarios tested
```

---

## 📊 Validation Examples

### Valid Inputs ✅
```java
// Movie Title
"The Dark Knight"              // ✅ Each word capitalized
"Inception"                    // ✅ Single word capitalized

// Movie ID
"TDK123"                       // ✅ TDK from title + 3 unique digits
"I456"                         // ✅ Single capital + unique digits

// User Name
"John Smith"                   // ✅ Alphabetic + space
"Alice"                        // ✅ Single word

// User ID
"123456789"                    // ✅ 9 digits
"12345678A"                    // ✅ 8 digits + 1 letter
```

### Invalid Inputs ❌
```java
// Movie Title
"the Dark Knight"              // ❌ Lowercase first letter
"The dark Knight"              // ❌ Lowercase middle word

// Movie ID
"ABC123"                       // ❌ Wrong letters (should be TDK)
"TDK111"                       // ❌ Non-unique digits
"TDKX123"                      // ❌ Extra letter after prefix

// User Name
" John Smith"                  // ❌ Starts with space
"John123"                      // ❌ Contains numbers
"John@Smith"                   // ❌ Special characters

// User ID
"12345678"                     // ❌ Too short (8 chars)
"1234567890"                   // ❌ Too long (10 chars)
"A12345678"                    // ❌ Starts with letter
"1234567AB"                    // ❌ Two letters at end
```

---

## 🐛 Known Issues & Bug Fixes

### Fixed Bugs

#### Bug #1: Wrong Exception Type for Extra Letters in Movie ID
**Status**: ✅ Fixed

**Description**: When validating movie ID "TDKX123" for title "The Dark Knight", the validator was throwing `MovieIdNumbersException` instead of `MovieIdLettersException`.

**Root Cause**: Validation only checked if ID starts with correct letters, but didn't verify the remaining part starts with a digit.

**Fix**: Added condition to check if first character of numbers section is a letter:
```java
if (!movieId.startsWith(expectedLetters) || Character.isLetter(numbersPart.charAt(0))) {
    throw new MovieIdLettersException(movieId);
}
```

**Test Case**: TC-MV-014

---

## 📚 Documentation

### Key Classes

#### Models
- **Movie**: Represents a movie with title, ID, and genres
- **User**: Represents a user with name, ID, and liked movies
- **UserRecommendation**: Encapsulates recommendation results

#### Validators
- **MovieValidator**: Validates movie titles and IDs
- **UserValidator**: Validates user names and IDs

#### Parsers
- **MovieParser**: Parses and validates movies.txt
- **UserParser**: Parses and validates users.txt

#### Services
- **RecommendationEngine**: Generates genre-based recommendations
- **RecommendationWriter**: Writes output to file

### Algorithm: Recommendation Generation

```
For each user:
    1. Get all liked movie IDs
    2. For each liked movie:
        a. Get all genres of that movie
        b. For each genre:
            - Find all movies in that genre
            - Add to recommendation set
    3. Remove already-liked movies from recommendations
    4. Convert movie IDs to titles
    5. Return unique recommendations
```

**Time Complexity**: O(U × L × G × M) where:
- U = number of users
- L = average liked movies per user
- G = average genres per movie
- M = average movies per genre

**Space Complexity**: O(M) for storing movie and genre mappings

---

## 👥 Team & Contributions

**Course**: CSE337s - Software Testing, Fall 2025  
**Institution**: Ain Shams University - Faculty of Engineering  
**Program**: Computer & Systems Engineering

### Development Team
- Implementation: Group of 6 students
- Testing: Comprehensive JUnit test suite
- Documentation: Full test case documentation

### Contribution Guidelines
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Write tests for your changes
4. Ensure all tests pass
5. Commit your changes (`git commit -m 'Add AmazingFeature'`)
6. Push to the branch (`git push origin feature/AmazingFeature`)
7. Open a Pull Request

---

## 🎓 Academic Context

This project demonstrates:
- ✅ **Unit Testing**: 94 comprehensive test cases using JUnit 5
- ✅ **Test-Driven Development**: Tests written alongside implementation
- ✅ **Bug Tracking**: Using Jira/Bugzilla for issue management
- ✅ **Documentation**: Test case templates and technical documentation
- 🔄 **White Box Testing**: Code coverage and path analysis (Phase 2)
- 🔄 **Black Box Testing**: Specification-based testing (Phase 2)
- 🔄 **Data Flow Testing**: Variable usage analysis (Phase 2)
- 🔄 **Integration Testing**: Component interaction testing (Phase 2)

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- Course instructors and TAs for project guidance
- JUnit team for the excellent testing framework
- Open source community for inspiration and tools

---

## 📞 Contact & Support

**Have questions or found a bug?**
- 📧 Open an issue on GitHub
- 💬 Contact the development team
- 📚 Check the documentation in `/docs`

---

<div align="center">

**⭐ If you find this project helpful, please consider giving it a star! ⭐**

Made with ❤️ by CSE Students at Ain Shams University

</div>
