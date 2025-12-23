package validators;

import exceptions.MovieTitleException;
import exceptions.MovieIdLettersException;
import exceptions.MovieIdNumbersException;
import java.util.Set;
import java.util.HashSet;

public class MovieValidator {

    private Set<String> existingMovieIds = new HashSet<>();

    public void validateTitle(String title) throws MovieTitleException {
        if (title == null || title.trim().isEmpty()) {
            throw new MovieTitleException(title);
        }

        String[] words = title.split("\\s+");
        for (String word : words) {
            if (word.isEmpty() || !Character.isUpperCase(word.charAt(0))) {
                throw new MovieTitleException(title);
            }
        }
    }

    public void validateMovieId(String title, String movieId)
            throws MovieIdLettersException, MovieIdNumbersException {

        String expectedLetters = extractCapitalLetters(title);

        String numbersPart = movieId.substring(expectedLetters.length());

        if (!movieId.startsWith(expectedLetters) ||
                (!numbersPart.isEmpty() && Character.isLetter(numbersPart.charAt(0)))) {
            throw new MovieIdLettersException(movieId);
        }

        if (numbersPart.length() != 3) {
            throw new MovieIdNumbersException(movieId);
        }

        for (char c : numbersPart.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new MovieIdNumbersException(movieId);
            }
        }

        if (existingMovieIds.contains(numbersPart)) {
            throw new MovieIdNumbersException(movieId);
        }
        else {
            existingMovieIds.add(numbersPart);
        }
    }

    private String extractCapitalLetters(String title) {
        StringBuilder capitals = new StringBuilder();
        for (char c : title.toCharArray()) {
            if (Character.isUpperCase(c)) {
                capitals.append(c);
            }
        }
        return capitals.toString();
    }
}