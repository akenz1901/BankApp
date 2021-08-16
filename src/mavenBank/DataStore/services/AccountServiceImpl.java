package mavenBank.DataStore.services;

import mavenBank.Account;
import mavenBank.AccountType;
import mavenBank.Customer;
import mavenBank.DataStore.CustomerRepo;
import mavenBank.Exceptions.MavenBankException;

public class AccountServiceImpl implements AccountService{
    @Override
    public long openAccount(Customer customer, AccountType typeofAccount) throws MavenBankException {
        if(customer == null || typeofAccount==null) {
            throw new MavenBankException("Customer or Type Can't be Null");
        }

        if(accountTypeExists(customer, typeofAccount)){
            throw new MavenBankException("Account Type already Exists for this user, Thanks");
        }
        Account newAccount = new Account();
        newAccount.setTypeOfAccount(AccountType.SAVINGS);
        newAccount.setAccountNumber(BankService.generateAccountNumber());
        customer.getAccounts().add(newAccount);

        CustomerRepo.getCustomers().put(customer.getBVN(),customer);
        return newAccount.getAccountNumber();
    }
    public boolean accountTypeExists(Customer customer, AccountType typeofAccount){

        boolean checkIfItExist = false;
        for (Account account:customer.getAccounts()) {

            if (account.getTypeOfAccount().equals(typeofAccount)) {
                checkIfItExist = true;
                break;
            }
        }
        return checkIfItExist;
    }
}
