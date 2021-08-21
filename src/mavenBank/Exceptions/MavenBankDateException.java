package mavenBank.Exceptions;

public class MavenBankDateException extends RuntimeException{
    public MavenBankDateException() {
    }

    public MavenBankDateException(String message) {
        super(message);
    }

    public MavenBankDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public MavenBankDateException(Throwable cause) {
        super(cause);
    }
}
