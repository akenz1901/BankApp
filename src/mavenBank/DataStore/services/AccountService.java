package mavenBank.DataStore.services;

import mavenBank.Account;
import mavenBank.AccountType;
import mavenBank.Customer;
import mavenBank.Exceptions.MavenBankException;

import java.math.BigDecimal;

public interface AccountService {
    long openAccount(Customer customer, AccountType typeofAccount) throws MavenBankException;
    BigDecimal deposit(BigDecimal decimal, long accountNumber) throws MavenBankException;

    Account findAccount(Customer customer, long accountNumber) throws MavenBankException;
    Account findAccount(long accountNumber) throws MavenBankException;
}
