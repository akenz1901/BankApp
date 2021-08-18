package mavenBank.Exceptions;

public class MavenBankInsufficientAmountException extends MavenBankTransactionException{
    public MavenBankInsufficientAmountException() {
    }

    public MavenBankInsufficientAmountException(String message) {
        super(message);
    }

    public MavenBankInsufficientAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public MavenBankInsufficientAmountException(Throwable cause) {
        super(cause);
    }
}
