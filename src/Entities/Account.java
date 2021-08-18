package Entities;

import java.math.BigDecimal;

public abstract class Account {
    private long accountNumber;
    private String pin;
    private BigDecimal balance = BigDecimal.ZERO;
    private Loan accountLoan;

    public Loan getAccountLoan() {
        return accountLoan;
    }

    public void setAccountLoan(Loan accountLoan) {
        this.accountLoan = accountLoan;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
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
