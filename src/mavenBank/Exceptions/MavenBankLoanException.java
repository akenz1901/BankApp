package mavenBank.Exceptions;

public class MavenBankLoanException extends MavenBankException{
    public MavenBankLoanException() {
    }

    public MavenBankLoanException(String message) {
        super(message);
    }

    public MavenBankLoanException(String message, Throwable cause) {
        super(message, cause);
    }

    public MavenBankLoanException(Throwable cause) {
        super(cause);
    }
}
