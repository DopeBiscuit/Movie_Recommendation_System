package exceptions;

public class UserIdException extends ValidationException {
    public UserIdException(String userId) {
        super("ERROR: User Id " + userId + " is wrong");
    }
}