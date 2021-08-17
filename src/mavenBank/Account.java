package mavenBank;

import java.math.BigDecimal;

public class Account {
    private long accountNumber;
    private AccountType typeOfAccount;
    private String pin;
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(long accountNumber, AccountType typeOfAccount) {
        this.accountNumber = accountNumber;
        this.typeOfAccount = typeOfAccount;
    }
    public Account(long accountNumber, AccountType typeOfAccount, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.typeOfAccount = typeOfAccount;
        this.balance = balance;
    }
    public Account(){
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(AccountType typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
