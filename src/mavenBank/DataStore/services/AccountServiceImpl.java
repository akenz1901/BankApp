package mavenBank.DataStore.services;

import mavenBank.Account;
import mavenBank.AccountType;
import mavenBank.Customer;
import mavenBank.DataStore.CustomerRepo;
import mavenBank.Exceptions.MavenBankException;
import mavenBank.Exceptions.MavenBankTransactionException;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService{
    @Override
    public long openAccount(Customer customer, AccountType typeofAccount) throws MavenBankException {
        if(customer == null || typeofAccount==null) {
            throw new MavenBankException("Customer or Type Can't be Null");
        }

        if(accountTypeExists(customer, typeofAccount)){
            throw new MavenBankException("Account Type already Exists for this user, Thanks");
        }
        Account newAccount = new Account();
        newAccount.setTypeOfAccount(AccountType.SAVINGS);
        newAccount.setAccountNumber(BankService.generateAccountNumber());
        customer.getAccounts().add(newAccount);

        CustomerRepo.getCustomers().put(customer.getBVN(),customer);
        return newAccount.getAccountNumber();
    }

    @Override
    public BigDecimal deposit(BigDecimal amount, long accountNumber) throws MavenBankException, MavenBankTransactionException {
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new MavenBankTransactionException("Invalid Amount");
        }
        BigDecimal balance = BigDecimal.ZERO;
        Account depositAccount = findAccount(accountNumber);
        depositAccount.getBalance().add(amount);
        return amount;
    }

    @Override
    public Account findAccount(Customer customer, long accountNumber)throws MavenBankException {

        return null;
    }

    @Override
    public Account findAccount(long accountNumber) throws MavenBankException{
        boolean accountFound = false;
        Account foundAccount = null;
        for (Customer customer: CustomerRepo.getCustomers().values()) {
            for (Account account:customer.getAccounts()){
                if(account.getAccountNumber() == accountNumber){
                    foundAccount = account;
                    accountFound = true;
                    break;
                }
                if(accountFound)
                    break;
            }

        }
        return foundAccount;
        }

    public boolean accountTypeExists(Customer customer, AccountType typeofAccount){

        boolean checkIfItExist = false;
        for (Account account:customer.getAccounts()) {

            if (account.getTypeOfAccount().equals(typeofAccount)) {
                checkIfItExist = true;
                break;
            }
        }
        return checkIfItExist;
    }
}
