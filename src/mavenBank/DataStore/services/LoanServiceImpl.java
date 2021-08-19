package mavenBank.DataStore.services;

import Entities.Account;
import Entities.Customer;
import Entities.LoanRequest;
import mavenBank.DataStore.LoanRequestStatus;
import mavenBank.Exceptions.MavenBankLoanException;

import java.math.BigDecimal;

public class LoanServiceImpl implements LoanService{

    @Override
    public LoanRequest approveLoanRequest(Account accountSeekingLoan)throws MavenBankLoanException {
        if(accountSeekingLoan == null){
            throw new MavenBankLoanException("An account is required for processing");
        }if(accountSeekingLoan.getAccountLoanRequest() == null){
            throw new MavenBankLoanException("No Loan provided for processing");
        }
        LoanRequest theLoanRequest = accountSeekingLoan.getAccountLoanRequest();
        theLoanRequest.setStatus(decideOnLoanRequest(accountSeekingLoan));

        return theLoanRequest;
    }

    @Override
    public LoanRequest approveLoanRequest(Customer customer, Account accountSeekingForLoan) throws MavenBankLoanException {
        LoanRequestStatus decision = decideOnLoanRequestWithCustomerBalance(customer, accountSeekingForLoan);
        LoanRequest theLoanRequest = accountSeekingForLoan.getAccountLoanRequest();
        theLoanRequest.setStatus(decision);

        if (decision != LoanRequestStatus.APPROVED){
            theLoanRequest = approveLoanRequest(accountSeekingForLoan);
        }
        return theLoanRequest;
    }

    private LoanRequestStatus decideOnLoanRequestWithAccountBalance(Account accountSeekingLoan) throws MavenBankLoanException {
        LoanRequestStatus decision = LoanRequestStatus.PENDING;
        LoanRequest theLoanRequest = accountSeekingLoan.getAccountLoanRequest();
        BigDecimal accountBalancePercentage = BigDecimal.valueOf(0.2);
        BigDecimal loanApprovedAutomatically = accountSeekingLoan.getBalance().multiply(accountBalancePercentage);
        if(theLoanRequest.getAmount().compareTo(loanApprovedAutomatically)<BigDecimal.ZERO.intValue()){
            decision = LoanRequestStatus.APPROVED;
        }
        return decision;
    }
    private LoanRequestStatus decideOnLoanRequestWithCustomerBalance(Customer customer, Account accountSeekingLoan) throws MavenBankLoanException {
        LoanRequestStatus decision = LoanRequestStatus.PENDING;
        BigDecimal relationShipVolume = BigDecimal.valueOf(0.2);

        BigDecimal totalCustomerBalance = BigDecimal.ZERO;
        if (customer.getAccounts().size() > BigDecimal.ONE.intValue()) {
            for (Account customerAccount : customer.getAccounts()) {
                totalCustomerBalance = totalCustomerBalance.add(customerAccount.getBalance());
            }
        }
        BigDecimal loanAmountApproveAutomatically = totalCustomerBalance.multiply(relationShipVolume);
        if (accountSeekingLoan.getAccountLoanRequest().getAmount().compareTo(loanAmountApproveAutomatically) < BigDecimal.ZERO.intValue()){
            decision = LoanRequestStatus.APPROVED;
    }
        return decision;
    }
    private LoanRequestStatus decideOnLoanRequest(Account accountSeekingLoan) throws MavenBankLoanException {
        LoanRequestStatus decision = decideOnLoanRequestWithAccountBalance(accountSeekingLoan);

        return decision;
    }
}
