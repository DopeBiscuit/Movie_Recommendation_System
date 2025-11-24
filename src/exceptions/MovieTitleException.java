package exceptions;

public class MovieTitleException extends ValidationException {
    public MovieTitleException(String title) {
        super("ERROR: Movie Title " + title + " is wrong");
    }
}