package services;

import models.UserRecommendation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RecommendationWriterTest {
    private RecommendationWriter writer;
    private String testOutputFile;

    @BeforeEach
    public void setUp() {
        writer = new RecommendationWriter();
        testOutputFile = "test_output.txt";
    }

    @AfterEach
    public void tearDown() {
        // Clean up test file
        File file = new File(testOutputFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testWriteRecommendations_SingleUser() throws IOException {
        List<UserRecommendation> recommendations = Arrays.asList(
                new UserRecommendation("John Smith", "123456789",
                        Arrays.asList("Inception", "The Godfather"))
        );

        writer.writeRecommendations(testOutputFile, recommendations);

        // Read and verify
        List<String> lines = readFile(testOutputFile);
        assertEquals(2, lines.size());
        assertEquals("John Smith,123456789", lines.get(0));
        assertEquals("Inception,The Godfather", lines.get(1));
    }

    @Test
    public void testWriteRecommendations_MultipleUsers() throws IOException {
        List<UserRecommendation> recommendations = Arrays.asList(
                new UserRecommendation("John Smith", "123456789",
                        Arrays.asList("Inception", "The Godfather")),
                new UserRecommendation("Alice Johnson", "987654321",
                        Arrays.asList("The Dark Knight"))
        );

        writer.writeRecommendations(testOutputFile, recommendations);

        List<String> lines = readFile(testOutputFile);
        assertEquals(4, lines.size());
        assertEquals("John Smith,123456789", lines.get(0));
        assertEquals("Inception,The Godfather", lines.get(1));
        assertEquals("Alice Johnson,987654321", lines.get(2));
        assertEquals("The Dark Knight", lines.get(3));
    }

    @Test
    public void testWriteRecommendations_NoRecommendations() throws IOException {
        List<UserRecommendation> recommendations = Arrays.asList(
                new UserRecommendation("Bob Williams", "123456780",
                        new ArrayList<>())
        );

        writer.writeRecommendations(testOutputFile, recommendations);

        List<String> lines = readFile(testOutputFile);
        assertEquals(2, lines.size());
        assertEquals("Bob Williams,123456780", lines.get(0));
        assertEquals("", lines.get(1));
    }

    @Test
    public void testWriteRecommendations_SingleMovie() throws IOException {
        List<UserRecommendation> recommendations = Arrays.asList(
                new UserRecommendation("Test User", "111111111",
                        Arrays.asList("Inception"))
        );

        writer.writeRecommendations(testOutputFile, recommendations);

        List<String> lines = readFile(testOutputFile);
        assertEquals(2, lines.size());
        assertEquals("Test User,111111111", lines.get(0));
        assertEquals("Inception", lines.get(1));
    }

    @Test
    public void testWriteRecommendations_EmptyList() throws IOException {
        List<UserRecommendation> recommendations = new ArrayList<>();

        writer.writeRecommendations(testOutputFile, recommendations);

        List<String> lines = readFile(testOutputFile);
        assertEquals(0, lines.size());
    }

    @Test
    public void testWriteError_SimpleMessage() throws IOException {
        String errorMessage = "ERROR: Movie Title invalid is wrong";

        writer.writeError(testOutputFile, errorMessage);

        List<String> lines = readFile(testOutputFile);
        assertEquals(1, lines.size());
        assertEquals(errorMessage, lines.get(0));
    }

    @Test
    public void testWriteError_MovieTitleError() throws IOException {
        String errorMessage = "ERROR: Movie Title the dark knight is wrong";

        writer.writeError(testOutputFile, errorMessage);

        List<String> lines = readFile(testOutputFile);
        assertEquals(1, lines.size());
        assertEquals(errorMessage, lines.get(0));
    }

    @Test
    public void testWriteError_MovieIdLettersError() throws IOException {
        String errorMessage = "ERROR: Movie Id letters ABC123 are wrong";

        writer.writeError(testOutputFile, errorMessage);

        List<String> lines = readFile(testOutputFile);
        assertEquals(1, lines.size());
        assertEquals(errorMessage, lines.get(0));
    }

    @Test
    public void testWriteError_UserNameError() throws IOException {
        String errorMessage = "ERROR: User Name John123 is wrong";

        writer.writeError(testOutputFile, errorMessage);

        List<String> lines = readFile(testOutputFile);
        assertEquals(1, lines.size());
        assertEquals(errorMessage, lines.get(0));
    }

    @Test
    public void testWriteError_UserIdError() throws IOException {
        String errorMessage = "ERROR: User Id 12345 is wrong";

        writer.writeError(testOutputFile, errorMessage);

        List<String> lines = readFile(testOutputFile);
        assertEquals(1, lines.size());
        assertEquals(errorMessage, lines.get(0));
    }

    @Test
    public void testWriteRecommendations_UserWithLetterInId() throws IOException {
        List<UserRecommendation> recommendations = Arrays.asList(
                new UserRecommendation("Test User", "12345678A",
                        Arrays.asList("Inception"))
        );

        writer.writeRecommendations(testOutputFile, recommendations);

        List<String> lines = readFile(testOutputFile);
        assertEquals(2, lines.size());
        assertEquals("Test User,12345678A", lines.get(0));
    }

    // Helper method to read file
    private List<String> readFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }
}