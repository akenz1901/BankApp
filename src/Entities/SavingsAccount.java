package Entities;

import java.math.BigDecimal;

public class SavingsAccount extends Account{

    public SavingsAccount(long accountNumber) {
        setAccountNumber(accountNumber);
    }
    public SavingsAccount(long accountNumber,BigDecimal balance) {
        setAccountNumber(accountNumber);
        setBalance(balance);
    }
    public SavingsAccount(){
    }
}
