package mavenBank.DataStore.services;

import Entities.Account;
import mavenBank.DataStore.AccountType;
import Entities.Customer;
import mavenBank.DataStore.LoanStatus;
import mavenBank.Exceptions.MavenBankException;

import java.math.BigDecimal;

public interface AccountService {
    long openAccount(Customer customer, AccountType typeofAccount) throws MavenBankException;
    BigDecimal deposit(BigDecimal decimal, long accountNumber) throws MavenBankException;

    long openSavingsAccount(Customer customer)throws MavenBankException;
    long OpenCurrentAccount(Customer customer)throws MavenBankException;

    Account findAccount(Customer customer, long accountNumber) throws MavenBankException;
    Account findAccount(long accountNumber) throws MavenBankException;

    BigDecimal withdraw(BigDecimal decimal, long accountNumber) throws MavenBankException;
    void applyForOverdraft(Account account);
    LoanStatus applyForLoan();
}
