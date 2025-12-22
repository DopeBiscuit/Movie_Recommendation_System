package parsers;

import models.Movie;
import exceptions.ValidationException;
import exceptions.MovieTitleException;
import exceptions.MovieIdLettersException;
import exceptions.MovieIdNumbersException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MovieParserTest {
    private MovieParser parser;
    private String testFile;

    @BeforeEach
    public void setUp() {
        parser = new MovieParser();
        testFile = "test_movies.txt";
    }

    @AfterEach
    public void tearDown() {
        File file = new File(testFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testParseMovies_ValidSingleMovie() throws Exception {
        createTestFile(
                "The Dark Knight,TDK123",
                "action,thriller"
        );

        List<Movie> movies = parser.parseMovies(testFile);

        assertEquals(1, movies.size());
        assertEquals("The Dark Knight", movies.get(0).getTitle());
        assertEquals("TDK123", movies.get(0).getId());
        assertEquals(2, movies.get(0).getGenres().size());
        assertTrue(movies.get(0).getGenres().contains("action"));
        assertTrue(movies.get(0).getGenres().contains("thriller"));
    }

    @Test
    public void testParseMovies_MultipleMovies() throws Exception {
        createTestFile(
                "The Dark Knight,TDK123",
                "action,thriller",
                "Inception,I456",
                "action,sci-fi,thriller"
        );

        List<Movie> movies = parser.parseMovies(testFile);

        assertEquals(2, movies.size());
        assertEquals("The Dark Knight", movies.get(0).getTitle());
        assertEquals("Inception", movies.get(1).getTitle());
    }

    @Test
    public void testParseMovies_SingleGenre() throws Exception {
        createTestFile(
                "The Shawshank Redemption,TSR789",
                "drama"
        );

        List<Movie> movies = parser.parseMovies(testFile);

        assertEquals(1, movies.size());
        assertEquals(1, movies.get(0).getGenres().size());
        assertEquals("drama", movies.get(0).getGenres().get(0));
    }

    @Test
    public void testParseMovies_InvalidTitle_Lowercase() throws Exception{
        createTestFile(
                "the dark knight,TDK123",
                "action,thriller"
        );

        assertThrows(MovieTitleException.class,
                () -> parser.parseMovies(testFile));
    }

    @Test
    public void testParseMovies_InvalidMovieId_WrongLetters() throws Exception {
        createTestFile(
                "The Dark Knight,ABC123",
                "action,thriller"
        );

        assertThrows(MovieIdLettersException.class,
                () -> parser.parseMovies(testFile));
    }

    @Test
    public void testParseMovies_InvalidMovieId_NonUniqueNumbers()throws Exception {
        createTestFile(
                "The Dark Knight,TDK111",
                "action,thriller"
        );

        assertThrows(MovieIdNumbersException.class,
                () -> parser.parseMovies(testFile));
    }

    @Test
    public void testParseMovies_MissingGenresLine() throws Exception{
        createTestFile(
                "The Dark Knight,TDK123"
        );

        assertThrows(ValidationException.class,
                () -> parser.parseMovies(testFile));
    }

    

    @Test
    public void testParseMovies_InvalidFormat_NoComma()throws Exception {
        createTestFile(
                "The Dark Knight TDK123",
                "action,thriller"
        );

        assertThrows(ValidationException.class,
                () -> parser.parseMovies(testFile));
    }

    @Test
    public void testParseMovies_EmptyFile() throws Exception {
        createTestFile();

        List<Movie> movies = parser.parseMovies(testFile);

        assertEquals(0, movies.size());
    }

    @Test
    public void testParseMovies_WithWhitespace() throws Exception {
        createTestFile(
                "  The Dark Knight  ,  TDK123  ",
                "  action  ,  thriller  "
        );

        List<Movie> movies = parser.parseMovies(testFile);

        assertEquals(1, movies.size());
        assertEquals("The Dark Knight", movies.get(0).getTitle());
        assertEquals("TDK123", movies.get(0).getId());
        assertEquals("action", movies.get(0).getGenres().get(0));
    }

    @Test
    public void testParseMovies_ErrorInSecondMovie() throws Exception{
        createTestFile(
                "The Dark Knight,TDK123",
                "action,thriller",
                "inception,I456",  // Invalid: lowercase first letter
                "action,sci-fi"
        );

        assertThrows(MovieTitleException.class,
                () -> parser.parseMovies(testFile));
    }

    @Test
    public void testParseMovies_MultipleGenres() throws Exception {
        createTestFile(
                "Inception,I456",
                "action,sci-fi,thriller,mystery"
        );

        List<Movie> movies = parser.parseMovies(testFile);

        assertEquals(1, movies.size());
        assertEquals(4, movies.get(0).getGenres().size());
    }

    // Helper method to create test file
    private void createTestFile(String... lines) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }
    @Test
    public void testParseMovies_FileNotFound() {
        assertThrows(IOException.class, 
            () -> parser.parseMovies("does_not_exist.txt"));
    }
}