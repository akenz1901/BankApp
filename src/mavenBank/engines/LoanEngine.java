package mavenBank.engines;

import Entities.Account;
import Entities.Customer;
import mavenBank.Exceptions.MavenBankLoanException;

import java.math.BigDecimal;

public interface LoanEngine {

    BigDecimal calculateAmountAutoApprove(Customer customer, Account accountSeekingLoan) throws MavenBankLoanException;

    default void validateLoanRequest(Customer customer, Account accountSeekingLoan) throws MavenBankLoanException {
        if (customer == null) {
            throw new MavenBankLoanException("No Customer provided for loan request");
        }
        validateLoanRequest(accountSeekingLoan);
    }
    default void validateLoanRequest(Account accountSeekingLoan)throws MavenBankLoanException{
        if(accountSeekingLoan == null){
            throw new MavenBankLoanException("An account is required for processing");
        }if(accountSeekingLoan.getAccountLoanRequest() == null){
            throw new MavenBankLoanException("No Loan provided for processing");
        }
    }
}
