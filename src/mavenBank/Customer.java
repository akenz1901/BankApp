package mavenBank;

import java.util.ArrayList;

public class Customer {
    private long BVN;
    private String firstName;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private ArrayList<Account> accounts = new ArrayList<>();

    public ArrayList<Account> getAccounts() {
        return accounts;
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
