package mavenBank.DataStore.services;

import Entities.*;
import mavenBank.DataStore.AccountType;
import mavenBank.DataStore.BankTransactionType;
import mavenBank.DataStore.CustomerRepo;
import mavenBank.DataStore.LoanStatus;
import mavenBank.Exceptions.MavenBankException;
import mavenBank.Exceptions.MavenBankInsufficientAmountException;
import mavenBank.Exceptions.MavenBankTransactionException;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService{

    @Override
    public long openAccount(Customer customer, AccountType typeofAccount) throws MavenBankException {
       long accountNumber = BigDecimal.ZERO.longValue();
        if(typeofAccount == AccountType.SAVINGSACCOUNT){
            accountNumber = openSavingsAccount(customer);
        }
        else if(typeofAccount == AccountType.CURRENTACCOUNT){
            accountNumber = OpenCurrentAccount(customer);
        }
        return accountNumber;
    }

    @Override
    public BigDecimal deposit(BigDecimal amount, long accountNumber) throws MavenBankException, MavenBankTransactionException {
        Account depositAccount = findAccount(accountNumber);
        validateTransactions(amount, depositAccount);

        BigDecimal newAmount = depositAccount.getBalance().add(amount);
        depositAccount.setBalance(newAmount);
        return newAmount;
    }

    @Override
    public long openSavingsAccount(Customer customer) throws MavenBankException {
        if(customer == null) {
            throw new MavenBankException("Customer or Type Can't be Null");
        }
        SavingsAccount newAccount = new SavingsAccount();
        if(accountTypeExists(customer, newAccount.getClass().getTypeName())){
            throw new MavenBankException("Account Type already Exists for this user, Thanks");
        }
        newAccount.setAccountNumber(BankService.generateAccountNumber());
        customer.getAccounts().add(newAccount);

        CustomerRepo.getCustomers().put(customer.getBVN(),customer);
        return newAccount.getAccountNumber();
    }

    @Override
    public long OpenCurrentAccount(Customer customer) throws MavenBankException {
        if(customer == null) {
            throw new MavenBankException("Customer Can't be Null");
        }
        CurrentAccount newAccount = new CurrentAccount();
        if(accountTypeExists(customer, newAccount.getClass().getTypeName())){
            throw new MavenBankException("Account Type already Exists for this user, Thanks");
        }
        newAccount.setAccountNumber(BankService.generateAccountNumber());
        customer.getAccounts().add(newAccount);

        CustomerRepo.getCustomers().put(customer.getBVN(),customer);
        return newAccount.getAccountNumber();
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

    @Override
    public BigDecimal withdraw(BigDecimal amount, long accountNumber) throws MavenBankTransactionException,MavenBankInsufficientAmountException, MavenBankException {
        Account availableAccount = findAccount(accountNumber);
        validateTransactions(amount, availableAccount);

        try{
            checkForSufficientBalance(amount, availableAccount);
        }catch (MavenBankInsufficientAmountException ex){
            this.applyForOverdraft(availableAccount);
            throw ex;//Rethrow
        }

        BigDecimal newBalance = debitAccount(amount, accountNumber);
        return newBalance;
    }

    @Override
    public void applyForOverdraft(Account account) {
        //TODO
    }

    @Override
    public LoanStatus applyForLoan() {
     return null;
    }

    @Override
    public void addBankTransaction(BankTransaction transaction, Account account) throws MavenBankException {
        if(transaction == null || account == null){
            throw new MavenBankTransactionException("Transaction and Account required");
        }
        if(transaction.getType() == BankTransactionType.DEPOSIT){
            deposit(transaction.getAmount(), account.getAccountNumber());
        }
        else if (transaction.getType() == BankTransactionType.WITHDRAW){
            withdraw(transaction.getAmount(), account.getAccountNumber());
        }
        account.getTransactions().add(transaction);
    }

    public void checkForSufficientBalance(BigDecimal amount, Account account) throws MavenBankInsufficientAmountException {
        if(amount.compareTo(account.getBalance()) > 0){
            throw new MavenBankInsufficientAmountException("Insufficient account balance");
        }
    }

    private BigDecimal debitAccount(BigDecimal amount, long accountNumber) throws MavenBankException {
        Account availableAccount = findAccount(accountNumber);
        BigDecimal newAmount = availableAccount.getBalance().subtract(amount);
        availableAccount.setBalance(newAmount);
        return newAmount;
    }
    private void validateTransactions(BigDecimal amount, Account account) throws MavenBankTransactionException, MavenBankInsufficientAmountException {
        if(amount.compareTo(BigDecimal.ZERO) < BigDecimal.ONE.intValue()){
            throw new MavenBankTransactionException("Invalid Amount");}
        if (account == null) {
            throw new MavenBankTransactionException("Account Not Found ensure you enter a valid account Number");
        }

    }

    public boolean accountTypeExists(Customer customer, String type){

        boolean checkIfItExist = false;
        for (Account account:customer.getAccounts()) {

            if (account.getClass().getTypeName() == type) {
                checkIfItExist = true;
                break;
            }
        }
        return checkIfItExist;
    }
}
