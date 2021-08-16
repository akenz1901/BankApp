package mavenBank.DataStore.services;

import mavenBank.AccountType;
import mavenBank.Customer;
import mavenBank.Exceptions.MavenBankException;

public interface AccountService {
    long openAccount(Customer customer, AccountType typeofAccount) throws MavenBankException;
}
