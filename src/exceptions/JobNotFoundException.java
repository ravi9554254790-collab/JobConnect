package exceptions;

public class JobNotFoundException extends Exception {

    public JobNotFoundException(String message) {
        super(message);
    }
}