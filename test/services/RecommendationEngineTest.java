package services;

import models.Movie;
import models.User;
import models.UserRecommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RecommendationEngineTest {
    private RecommendationEngine engine;
    private List<Movie> movies;

    @BeforeEach
    public void setUp() {
        // Create test movies
        movies = Arrays.asList(
                new Movie("The Dark Knight", "TDK123", Arrays.asList("action", "thriller")),
                new Movie("Inception", "I456", Arrays.asList("action", "sci-fi", "thriller")),
                new Movie("The Shawshank Redemption", "TSR789", Arrays.asList("drama")),
                new Movie("Finding Nemo", "FN012", Arrays.asList("animation", "adventure")),
                new Movie("The Godfather", "TG345", Arrays.asList("crime", "drama")),
                new Movie("Interstellar", "I678", Arrays.asList("sci-fi", "adventure", "drama"))
        );

        engine = new RecommendationEngine(movies);
    }

    @Test
    public void testRecommendations_SingleGenre() {
        // User likes TSR789 (drama)
        // Should recommend: TG345 (crime, drama), I678 (sci-fi, adventure, drama)
        User user = new User("John Smith", "123456789", Arrays.asList("TSR789"));
        UserRecommendation rec = engine.generateRecommendations(user);

        assertEquals("John Smith", rec.getUserName());
        assertEquals("123456789", rec.getUserId());
        assertEquals(2, rec.getRecommendedMovieTitles().size());
        assertTrue(rec.getRecommendedMovieTitles().contains("The Godfather"));
        assertTrue(rec.getRecommendedMovieTitles().contains("Interstellar"));
    }

    @Test
    public void testRecommendations_MultipleGenres() {
        // User likes TDK123 (action, thriller)
        // Should recommend: I456 (action, sci-fi, thriller) - overlaps in both genres
        User user = new User("Alice Johnson", "987654321", Arrays.asList("TDK123"));
        UserRecommendation rec = engine.generateRecommendations(user);

        assertEquals("Alice Johnson", rec.getUserName());
        assertTrue(rec.getRecommendedMovieTitles().contains("Inception"));
    }

    @Test
    public void testRecommendations_MultipleLikedMovies() {
        // User likes FN012 (animation, adventure) and I678 (sci-fi, adventure, drama)
        // Should recommend: TSR789 (drama), TG345 (drama) from drama genre
        User user = new User("Bob Williams", "123456780", Arrays.asList("FN012", "I678"));
        UserRecommendation rec = engine.generateRecommendations(user);

        assertEquals("Bob Williams", rec.getUserName());
        assertTrue(rec.getRecommendedMovieTitles().size() >= 2);
        assertTrue(rec.getRecommendedMovieTitles().contains("The Shawshank Redemption"));
        assertTrue(rec.getRecommendedMovieTitles().contains("The Godfather"));
    }

    @Test
    public void testRecommendations_NoRecommendations() {
        // User likes a movie with unique genre combination
        User user = new User("Test User", "111111111", Arrays.asList("TDK123", "I456", "TSR789", "FN012", "TG345", "I678"));
        UserRecommendation rec = engine.generateRecommendations(user);

        assertEquals("Test User", rec.getUserName());
        assertEquals(0, rec.getRecommendedMovieTitles().size());
    }

    @Test
    public void testRecommendations_InvalidMovieId() {
        // User likes non-existent movie
        User user = new User("Test User", "222222222", Arrays.asList("INVALID123"));
        UserRecommendation rec = engine.generateRecommendations(user);

        assertEquals("Test User", rec.getUserName());
        assertEquals(0, rec.getRecommendedMovieTitles().size());
    }

    @Test
    public void testRecommendations_DuplicatePrevention() {
        // User likes I456 (action, sci-fi, thriller)
        // Should recommend TDK123 but not I456 itself
        User user = new User("Test User", "333333333", Arrays.asList("I456"));
        UserRecommendation rec = engine.generateRecommendations(user);

        assertFalse(rec.getRecommendedMovieTitles().contains("Inception"));
        assertTrue(rec.getRecommendedMovieTitles().contains("The Dark Knight"));
    }

    @Test
    public void testRecommendations_MultiGenreOverlap() {
        // User likes I456 (action, sci-fi, thriller)
        // TDK123 appears in both action and thriller, but should only appear once
        User user = new User("Test User", "444444444", Arrays.asList("I456"));
        UserRecommendation rec = engine.generateRecommendations(user);

        long count = rec.getRecommendedMovieTitles().stream()
                .filter(title -> title.equals("The Dark Knight"))
                .count();

        assertEquals(1, count, "Movie should not be duplicated in recommendations");
    }

    @Test
    public void testRecommendations_EmptyLikedList() {
        User user = new User("Test User", "555555555", Arrays.asList());
        UserRecommendation rec = engine.generateRecommendations(user);

        assertEquals("Test User", rec.getUserName());
        assertEquals(0, rec.getRecommendedMovieTitles().size());
    }
}