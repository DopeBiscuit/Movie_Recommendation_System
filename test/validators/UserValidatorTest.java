package validators;

import exceptions.UserNameException;
import exceptions.UserIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {
    private UserValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new UserValidator();
    }

    // ==================== User Name Validation Tests ====================

    @Test
    public void testValidUserName_SingleWord() {
        assertDoesNotThrow(() -> validator.validateUserName("John"));
    }

    @Test
    public void testValidUserName_TwoWords() {
        assertDoesNotThrow(() -> validator.validateUserName("John Smith"));
    }

    @Test
    public void testValidUserName_MultipleWords() {
        assertDoesNotThrow(() -> validator.validateUserName("John Michael Smith"));
    }

    @Test
    public void testValidUserName_WithSpaces() {
        assertDoesNotThrow(() -> validator.validateUserName("Alice Johnson"));
    }

    @Test
    public void testInvalidUserName_StartsWithSpace() {
        assertThrows(UserNameException.class,
                () -> validator.validateUserName(" John Smith"));
    }

    @Test
    public void testInvalidUserName_ContainsNumbers() {
        assertThrows(UserNameException.class,
                () -> validator.validateUserName("John123"));
    }

    @Test
    public void testInvalidUserName_ContainsSpecialChars() {
        assertThrows(UserNameException.class,
                () -> validator.validateUserName("John@Smith"));
    }

    @Test
    public void testInvalidUserName_ContainsDash() {
        assertThrows(UserNameException.class,
                () -> validator.validateUserName("John-Smith"));
    }

    @Test
    public void testInvalidUserName_Null() {
        assertThrows(UserNameException.class,
                () -> validator.validateUserName(null));
    }

    @Test
    public void testInvalidUserName_Empty() {
        assertThrows(UserNameException.class,
                () -> validator.validateUserName(""));
    }

    // ==================== User ID Validation Tests ====================

    @Test
    public void testValidUserId_AllDigits() {
        assertDoesNotThrow(() -> validator.validateUserId("123456789"));
    }

    @Test
    public void testValidUserId_EightDigitsOneLetter() {
        assertDoesNotThrow(() -> validator.validateUserId("12345678A"));
    }

    @Test
    public void testValidUserId_EightDigitsLowercaseLetter() {
        assertDoesNotThrow(() -> validator.validateUserId("12345678a"));
    }

    @Test
    public void testInvalidUserId_TooShort() {
        assertThrows(UserIdException.class,
                () -> validator.validateUserId("12345678"));
    }

    @Test
    public void testInvalidUserId_TooLong() {
        assertThrows(UserIdException.class,
                () -> validator.validateUserId("1234567890"));
    }

    @Test
    public void testInvalidUserId_StartsWithLetter() {
        assertThrows(UserIdException.class,
                () -> validator.validateUserId("A12345678"));
    }

    @Test
    public void testInvalidUserId_TwoLettersAtEnd() {
        assertThrows(UserIdException.class,
                () -> validator.validateUserId("1234567AB"));
    }

    @Test
    public void testInvalidUserId_LetterInMiddle() {
        assertThrows(UserIdException.class,
                () -> validator.validateUserId("1234A6789"));
    }

    @Test
    public void testInvalidUserId_Null() {
        assertThrows(UserIdException.class,
                () -> validator.validateUserId(null));
    }

    @Test
    public void testInvalidUserId_OnlyLetters() {
        assertThrows(UserIdException.class,
                () -> validator.validateUserId("ABCDEFGHI"));
    }

    @Test
    public void testInvalidUserId_SpecialCharacters() {
        assertThrows(UserIdException.class,
                () -> validator.validateUserId("12345678@"));
    }

    // ==================== Unique User ID Tests ====================

    @Test
    public void testUniqueUserId_NotInSet() {
        Set<String> existingIds = new HashSet<>();
        existingIds.add("123456789");
        existingIds.add("987654321");

        assertDoesNotThrow(() ->
                validator.validateUniqueUserId("111111111", existingIds));
    }

    @Test
    public void testUniqueUserId_AlreadyExists() {
        Set<String> existingIds = new HashSet<>();
        existingIds.add("123456789");
        existingIds.add("987654321");

        assertThrows(UserIdException.class,
                () -> validator.validateUniqueUserId("123456789", existingIds));
    }

    @Test
    public void testUniqueUserId_EmptySet() {
        Set<String> existingIds = new HashSet<>();

        assertDoesNotThrow(() ->
                validator.validateUniqueUserId("123456789", existingIds));
    }

    @Test
    public void testUniqueUserId_WithLetter() {
        Set<String> existingIds = new HashSet<>();
        existingIds.add("12345678A");

        assertThrows(UserIdException.class,
                () -> validator.validateUniqueUserId("12345678A", existingIds));
    }
}