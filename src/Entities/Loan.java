package Entities;

import mavenBank.DataStore.LoanStatus;
import mavenBank.DataStore.LoanType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Loan {
    private BigDecimal amount;
    private LoanType typeOfLoan;
    private  LocalDate startDate;
    private int tenor;
    private double interestRate;
    private LoanStatus status;
}
