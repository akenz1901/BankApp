package Entities;

import mavenBank.DataStore.BankTransactionType;
import mavenBank.DataStore.services.BankService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BankTransaction {
    private long id;
    private LocalDateTime dateTime;
    private BankTransactionType type;
    private BigDecimal amount;

    public BankTransaction(BankTransactionType type, BigDecimal tXAmount){
        this.id = BankService.generateId();
        this.dateTime = LocalDateTime.now();
        this.type = type;
        amount = tXAmount;
    }
    public long getTransactionId() {
        return id;
    }

    public void setTransactionId(long transactionId) {
        this.id = transactionId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BankTransactionType getType() {
        return type;
    }

    public void setType(BankTransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
