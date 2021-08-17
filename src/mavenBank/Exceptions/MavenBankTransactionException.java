package mavenBank.Exceptions;

public class MavenBankTransactionException extends MavenBankException{
    public MavenBankTransactionException() {
    }

    public MavenBankTransactionException(String message) {
        super(message);
    }

    public MavenBankTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MavenBankTransactionException(Throwable cause) {
        super(cause);
    }
}
