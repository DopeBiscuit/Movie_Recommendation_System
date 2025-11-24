package exceptions;

public class MovieIdNumbersException extends ValidationException {
    public MovieIdNumbersException(String movieId) {
        super("ERROR: Movie Id numbers " + movieId + " aren't unique");
    }
}