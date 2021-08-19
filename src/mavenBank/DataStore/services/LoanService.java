package mavenBank.DataStore.services;

import Entities.Account;
import Entities.Customer;
import Entities.LoanRequest;
import mavenBank.Exceptions.MavenBankLoanException;

public interface LoanService {
    public LoanRequest approveLoanRequest(Account loanAccount)throws MavenBankLoanException;
    public LoanRequest approveLoanRequest(Customer customer, Account accountSeekingForLoan)throws MavenBankLoanException;
}
