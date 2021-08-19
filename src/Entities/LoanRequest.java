package Entities;

import mavenBank.DataStore.LoanStatus;
import mavenBank.DataStore.LoanType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Loan {
    private BigDecimal amount;
    private LoanType typeOfLoan;
    private LocalDate applyDate;
    private  LocalDate startDate;
    private int tenor;
    private double interest;
    private LoanStatus status;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LoanType getTypeOfLoan() {
        return typeOfLoan;
    }

    public void setTypeOfLoan(LoanType typeOfLoan) {
        this.typeOfLoan = typeOfLoan;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getTenor() {
        return tenor;
    }

    public void setTenor(int tenor) {
        this.tenor = tenor;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }
}
