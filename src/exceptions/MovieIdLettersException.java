package exceptions;

public class MovieIdLettersException extends ValidationException {
    public MovieIdLettersException(String movieId) {
        super("ERROR: Movie Id letters " + movieId + " are wrong");
    }
}