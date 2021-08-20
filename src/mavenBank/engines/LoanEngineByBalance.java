package mavenBank.engines;

import Entities.Account;
import Entities.Customer;
import mavenBank.DataStore.LoanRequestStatus;
import mavenBank.Exceptions.MavenBankLoanException;

import java.math.BigDecimal;

public class LoanEngineByBalance implements LoanEngine {
    @Override
    public BigDecimal calculateAmountAutoApprove(Customer customer, Account account) throws MavenBankLoanException {
        validateLoanRequest(customer, account);
        BigDecimal totalVolumeBalance = BigDecimal.valueOf(0.2);

        BigDecimal totalCustomerBalance = BigDecimal.ZERO;
        if (customer.getAccounts().size() > BigDecimal.ONE.intValue()) {
            for (Account customerAccount : customer.getAccounts()) {
                totalCustomerBalance = totalCustomerBalance.add(customerAccount.getBalance());
            }
        }
        BigDecimal loanAmountApproveAutomatically = BigDecimal.ZERO;
        if (totalCustomerBalance.intValue() > BigDecimal.ZERO.intValue()){
            loanAmountApproveAutomatically = totalCustomerBalance.multiply(totalVolumeBalance);
        }

        return loanAmountApproveAutomatically;
    }
}
