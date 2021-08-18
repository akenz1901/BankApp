package mavenBank.Exceptions;

public class MavenBankInsuffiencientAmountException extends MavenBankTransactionException{
    public MavenBankInsuffiencientAmountException() {
    }

    public MavenBankInsuffiencientAmountException(String message) {
        super(message);
    }

    public MavenBankInsuffiencientAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public MavenBankInsuffiencientAmountException(Throwable cause) {
        super(cause);
    }
}
