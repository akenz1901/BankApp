package Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrentAccount extends Account{
    public CurrentAccount(long accountNumber) {
        this();
        setAccountNumber(accountNumber);
    }
    public CurrentAccount(long accountNumber, BigDecimal balance) {
        this(accountNumber);
        setBalance(balance);
    }
    public CurrentAccount(){
        setStartDate(LocalDateTime.now());
    }
}
