package exceptions;

public class UserNameException extends ValidationException {
    public UserNameException(String userName) {
        super("ERROR: User Name " + userName + " is wrong");
    }
}