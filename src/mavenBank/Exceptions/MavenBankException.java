package mavenBank.Exceptions;

public class MavenBankException extends Exception{
    public MavenBankException(){
        super();
    }
    public MavenBankException(String message) {
        super(message);
    }

    public MavenBankException(String message, Throwable cause) {
        super(message, cause);
    }

    public MavenBankException(Throwable cause) {
        super(cause);
    }
}
