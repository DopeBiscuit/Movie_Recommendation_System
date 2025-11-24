package validators;

import exceptions.MovieTitleException;
import exceptions.MovieIdLettersException;
import exceptions.MovieIdNumbersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieValidatorTest {
    private MovieValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new MovieValidator();
    }

    // ==================== Title Validation Tests ====================

    @Test
    public void testValidTitle_AllWordsCapitalized() {
        assertDoesNotThrow(() -> validator.validateTitle("The Dark Knight"));
    }

    @Test
    public void testValidTitle_SingleWord() {
        assertDoesNotThrow(() -> validator.validateTitle("Inception"));
    }

    @Test
    public void testValidTitle_MultipleWords() {
        assertDoesNotThrow(() -> validator.validateTitle("The Shawshank Redemption"));
    }

    @Test
    public void testInvalidTitle_LowercaseFirstLetter() {
        assertThrows(MovieTitleException.class,
                () -> validator.validateTitle("the Dark Knight"));
    }

    @Test
    public void testInvalidTitle_LowercaseMiddleWord() {
        assertThrows(MovieTitleException.class,
                () -> validator.validateTitle("The dark Knight"));
    }

    @Test
    public void testInvalidTitle_Null() {
        assertThrows(MovieTitleException.class,
                () -> validator.validateTitle(null));
    }

    @Test
    public void testInvalidTitle_Empty() {
        assertThrows(MovieTitleException.class,
                () -> validator.validateTitle(""));
    }

    @Test
    public void testInvalidTitle_OnlySpaces() {
        assertThrows(MovieTitleException.class,
                () -> validator.validateTitle("   "));
    }

    // ==================== Movie ID Validation Tests ====================

    @Test
    public void testValidMovieId_ThreeCapitals() {
        assertDoesNotThrow(() ->
                validator.validateMovieId("The Dark Knight", "TDK123"));
    }

    @Test
    public void testValidMovieId_SingleCapital() {
        assertDoesNotThrow(() ->
                validator.validateMovieId("Inception", "I456"));
    }

    @Test
    public void testValidMovieId_MultipleCapitals() {
        assertDoesNotThrow(() ->
                validator.validateMovieId("The Shawshank Redemption", "TSR789"));
    }

    @Test
    public void testInvalidMovieId_WrongLetters() {
        assertThrows(MovieIdLettersException.class,
                () -> validator.validateMovieId("The Dark Knight", "ABC123"));
    }

    @Test
    public void testInvalidMovieId_MissingLetters() {
        assertThrows(MovieIdLettersException.class,
                () -> validator.validateMovieId("The Dark Knight", "TD123"));
    }

    @Test
    public void testInvalidMovieId_ExtraLetters() {
        assertThrows(MovieIdLettersException.class,
                () -> validator.validateMovieId("The Dark Knight", "TDKX123"));
    }

    @Test
    public void testInvalidMovieId_NonUniqueNumbers() {
        assertThrows(MovieIdNumbersException.class,
                () -> validator.validateMovieId("The Dark Knight", "TDK111"));
    }

    @Test
    public void testInvalidMovieId_TwoSameDigits() {
        assertThrows(MovieIdNumbersException.class,
                () -> validator.validateMovieId("The Dark Knight", "TDK112"));
    }

    @Test
    public void testInvalidMovieId_LessThanThreeDigits() {
        assertThrows(MovieIdNumbersException.class,
                () -> validator.validateMovieId("The Dark Knight", "TDK12"));
    }

    @Test
    public void testInvalidMovieId_MoreThanThreeDigits() {
        assertThrows(MovieIdNumbersException.class,
                () -> validator.validateMovieId("The Dark Knight", "TDK1234"));
    }

    @Test
    public void testInvalidMovieId_LetterInNumbers() {
        assertThrows(MovieIdNumbersException.class,
                () -> validator.validateMovieId("The Dark Knight", "TDK12A"));
    }

    @Test
    public void testValidMovieId_EdgeCase_AllUniqueDigits() {
        assertDoesNotThrow(() ->
                validator.validateMovieId("Finding Nemo", "FN012"));
    }
}