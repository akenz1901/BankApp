package Entities;

import mavenBank.Exceptions.MavenBankDateException;
import mavenBank.Exceptions.MavenBankException;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private long BVN;
    private String firstName;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private LocalDateTime relationshipStartDate;
    private List<Account> accounts = new ArrayList<>();

    public List<Account> getAccounts() {
        return accounts;
    }

    public LocalDateTime getRelationshipStartDate() {
        return relationshipStartDate;
    }

    public void setRelationshipStartDate(LocalDateTime relationshipStartDate){
            validateRelationStartDate(relationshipStartDate);
        this.relationshipStartDate = relationshipStartDate;
    }
    private void validateRelationStartDate(LocalDateTime relationshipStartDate) throws MavenBankDateException {
        if (ChronoUnit.MONTHS.between(relationshipStartDate, LocalDateTime.now()) < 0){
            throw new MavenBankDateException("Enter a Valid Month value");
        }
    }

    private void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public long getBVN() {
        return BVN;
    }

    public void setBVN(long BVN) {
        this.BVN = BVN;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
