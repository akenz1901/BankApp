package mavenBank.DataStore.services;

import Entities.Account;
import Entities.Loan;
import mavenBank.DataStore.LoanStatus;
import mavenBank.Exceptions.MavenBankLoanException;

import java.math.BigDecimal;

public class LoanServiceImpl implements LoanService{

    @Override
    public Loan approveLoan(Account accountSeekingLoan)throws MavenBankLoanException {
        if(accountSeekingLoan == null){
            throw new MavenBankLoanException("An account is required for processing");
        }if(accountSeekingLoan.getAccountLoan() == null){
            throw new MavenBankLoanException("No Loan provided for processing");
        }
        Loan theLoan = accountSeekingLoan.getAccountLoan();
        theLoan.setStatus(decideOnLoan(accountSeekingLoan));

        return theLoan;
    }
    private LoanStatus decideOnLoan(Account accountSeekingLoan) throws MavenBankLoanException {
        LoanStatus decision = LoanStatus.PENDING;
        Loan theLoan = accountSeekingLoan.getAccountLoan();
        BigDecimal accountBalancePercentage = BigDecimal.valueOf(0.2);
        BigDecimal loanApprovedAutomatically = accountSeekingLoan.getBalance().multiply(accountBalancePercentage);
        if(theLoan.getAmount().compareTo(loanApprovedAutomatically)<BigDecimal.ZERO.intValue()){
            decision = LoanStatus.APPROVED;
        }
        return decision;
    }
}
