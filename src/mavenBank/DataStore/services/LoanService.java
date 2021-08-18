package mavenBank.DataStore.services;

import Entities.Account;
import Entities.Loan;
import mavenBank.Exceptions.MavenBankLoanException;

public interface LoanService {
    public Loan approveLoan(Account loanAccount)throws MavenBankLoanException;
}
