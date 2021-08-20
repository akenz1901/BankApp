package mavenBank.engines;

import Entities.Account;
import Entities.Customer;
import mavenBank.Exceptions.MavenBankLoanException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LoanEngineByRelationLength implements LoanEngine{
    @Override
    public BigDecimal calculateAmountAutoApprove(Customer customer, Account accountSeekingLoan) throws MavenBankLoanException {
        validateLoanRequest(customer, accountSeekingLoan);

        //calculate loan amount
        BigDecimal oneMonthPercentage = BigDecimal.valueOf(0.00);
        BigDecimal fiveMonthPercentage = BigDecimal.valueOf(0.02);
        BigDecimal sixMonthPercentage = BigDecimal.valueOf(0.04);
        BigDecimal twelveMonthPercentage = BigDecimal.valueOf(0.06);
        BigDecimal twentyThreeMonthPercentage = BigDecimal.valueOf(0.08);
        BigDecimal twentyFourMonthPercentage = BigDecimal.valueOf(0.1);

        long period = ChronoUnit.MONTHS.between(customer.getRelationshipStartDate(), LocalDateTime.now());

        BigDecimal totalCustomerBalance = BigDecimal.ZERO;
        if (customer.getAccounts().size() > BigDecimal.ONE.intValue()) {
            for (Account customerAccount : customer.getAccounts()) {
                totalCustomerBalance = totalCustomerBalance.add(customerAccount.getBalance());
            }
        }
        BigDecimal loanAmountApproveAutomatically;
        if ( period <= 2 && period > 0){
            loanAmountApproveAutomatically = totalCustomerBalance.multiply(oneMonthPercentage);
        }else if ( period > 2 && period <= 5){
            loanAmountApproveAutomatically = totalCustomerBalance.multiply(fiveMonthPercentage);
        }else if(period > 5 && period <=11 ){
            loanAmountApproveAutomatically = totalCustomerBalance.multiply(sixMonthPercentage);
        }else if(period > 11 && period <= 17){
            loanAmountApproveAutomatically = totalCustomerBalance.multiply(twelveMonthPercentage);
        }else if(period > 17  && period <= 23){
            loanAmountApproveAutomatically = totalCustomerBalance.multiply(twentyThreeMonthPercentage);
        }else
            loanAmountApproveAutomatically = totalCustomerBalance.multiply(twentyFourMonthPercentage);
        return loanAmountApproveAutomatically;
    }
}
