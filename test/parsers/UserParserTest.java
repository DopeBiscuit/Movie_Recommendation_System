package parsers;

import models.User;
import exceptions.ValidationException;
import exceptions.UserNameException;
import exceptions.UserIdException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserParserTest {
    private UserParser parser;
    private String testFile;

    @BeforeEach
    public void setUp() {
        parser = new UserParser();
        testFile = "test_users.txt";
    }

    @AfterEach
    public void tearDown() {
        File file = new File(testFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testParseUsers_ValidSingleUser() throws Exception {
        createTestFile(
                "John Smith,123456789",
                "TDK123,I456"
        );

        List<User> users = parser.parseUsers(testFile);

        assertEquals(1, users.size());
        assertEquals("John Smith", users.get(0).getName());
        assertEquals("123456789", users.get(0).getId());
        assertEquals(2, users.get(0).getLikedMovieIds().size());
        assertTrue(users.get(0).getLikedMovieIds().contains("TDK123"));
        assertTrue(users.get(0).getLikedMovieIds().contains("I456"));
    }

    @Test
    public void testParseUsers_MultipleUsers() throws Exception {
        createTestFile(
                "John Smith,123456789",
                "TDK123,I456",
                "Alice Johnson,987654321",
                "TSR789"
        );

        List<User> users = parser.parseUsers(testFile);

        assertEquals(2, users.size());
        assertEquals("John Smith", users.get(0).getName());
        assertEquals("Alice Johnson", users.get(1).getName());
    }

    @Test
    public void testParseUsers_UserWithLetterInId() throws Exception {
        createTestFile(
                "Bob Williams,12345678A",
                "FN012"
        );

        List<User> users = parser.parseUsers(testFile);

        assertEquals(1, users.size());
        assertEquals("12345678A", users.get(0).getId());
    }

    @Test
    public void testParseUsers_SingleLikedMovie() throws Exception {
        createTestFile(
                "Alice Johnson,987654321",
                "TSR789"
        );

        List<User> users = parser.parseUsers(testFile);

        assertEquals(1, users.size());
        assertEquals(1, users.get(0).getLikedMovieIds().size());
        assertEquals("TSR789", users.get(0).getLikedMovieIds().get(0));
    }

    @Test
    public void testParseUsers_MultipleLikedMovies() throws Exception {
        createTestFile(
                "Test User,111111111",
                "TDK123,I456,TSR789,FN012"
        );

        List<User> users = parser.parseUsers(testFile);

        assertEquals(1, users.size());
        assertEquals(4, users.get(0).getLikedMovieIds().size());
    }

    @Test
    public void testParseUsers_InvalidName_StartsWithSpace() {
        createTestFile(
                " John Smith,123456789",
                "TDK123"
        );

        assertThrows(UserNameException.class,
                () -> parser.parseUsers(testFile));
    }

    @Test
    public void testParseUsers_InvalidName_ContainsNumbers() {
        createTestFile(
                "John123,123456789",
                "TDK123"
        );

        assertThrows(UserNameException.class,
                () -> parser.parseUsers(testFile));
    }

    @Test
    public void testParseUsers_InvalidName_ContainsSpecialChars() {
        createTestFile(
                "John@Smith,123456789",
                "TDK123"
        );

        assertThrows(UserNameException.class,
                () -> parser.parseUsers(testFile));
    }

    @Test
    public void testParseUsers_InvalidId_TooShort() {
        createTestFile(
                "John Smith,12345678",
                "TDK123"
        );

        assertThrows(UserIdException.class,
                () -> parser.parseUsers(testFile));
    }

    @Test
    public void testParseUsers_InvalidId_TooLong() {
        createTestFile(
                "John Smith,1234567890",
                "TDK123"
        );

        assertThrows(UserIdException.class,
                () -> parser.parseUsers(testFile));
    }

    @Test
    public void testParseUsers_InvalidId_StartsWithLetter() {
        createTestFile(
                "John Smith,A12345678",
                "TDK123"
        );

        assertThrows(UserIdException.class,
                () -> parser.parseUsers(testFile));
    }

    @Test
    public void testParseUsers_InvalidId_TwoLettersAtEnd() {
        createTestFile(
                "John Smith,1234567AB",
                "TDK123"
        );

        assertThrows(UserIdException.class,
                () -> parser.parseUsers(testFile));
    }

    @Test
    public void testParseUsers_DuplicateUserId() {
        createTestFile(
                "John Smith,123456789",
                "TDK123",
                "Alice Johnson,123456789",  // Duplicate ID
                "TSR789"
        );

        assertThrows(UserIdException.class,
                () -> parser.parseUsers(testFile));
    }

    @Test
    public void testParseUsers_MissingLikedMoviesLine() {
        createTestFile(
                "John Smith,123456789"
        );

        assertThrows(ValidationException.class,
                () -> parser.parseUsers(testFile));
    }

    @Test
    public void testParseUsers_InvalidFormat_NoComma() {
        createTestFile(
                "John Smith 123456789",
                "TDK123"
        );

        assertThrows(ValidationException.class,
                () -> parser.parseUsers(testFile));
    }

    @Test
    public void testParseUsers_EmptyFile() throws Exception {
        createTestFile();

        List<User> users = parser.parseUsers(testFile);

        assertEquals(0, users.size());
    }

    //TODO: ask TA about this case
//    @Test
//    public void testParseUsers_WithWhitespace() throws Exception {
//        createTestFile(
//                "  John Smith  ,  123456789  ",
//                "  TDK123  ,  I456  "
//        );
//
//        List<User> users = parser.parseUsers(testFile);
//
//        assertEquals(1, users.size());
//        assertEquals("John Smith", users.get(0).getName());
//        assertEquals("123456789", users.get(0).getId());
//        assertEquals("TDK123", users.get(0).getLikedMovieIds().get(0));
//    }

    @Test
    public void testParseUsers_ErrorInSecondUser() {
        createTestFile(
                "John Smith,123456789",
                "TDK123",
                "Alice123,987654321",  // Invalid: name contains numbers
                "TSR789"
        );

        assertThrows(UserNameException.class,
                () -> parser.parseUsers(testFile));
    }

    @Test
    public void testParseUsers_ThreeUsers() throws Exception {
        createTestFile(
                "John Smith,123456789",
                "TDK123,I456",
                "Alice Johnson,987654321",
                "TSR789",
                "Bob Williams,123456780",
                "FN012,I678"
        );

        List<User> users = parser.parseUsers(testFile);

        assertEquals(3, users.size());
        assertEquals("John Smith", users.get(0).getName());
        assertEquals("Alice Johnson", users.get(1).getName());
        assertEquals("Bob Williams", users.get(2).getName());
    }

    // Helper method to create test file
    private void createTestFile(String... lines) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            fail("Failed to create test file: " + e.getMessage());
        }
    }
}