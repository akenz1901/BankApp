package mavenBank.DataStore;

import Entities.Account;
import Entities.AccountType;
import Entities.Customer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CustomerRepo {
    private static Map<Long, Customer> customers = new HashMap<>();

    public static Map<Long, Customer> getCustomers() {
        return customers;
    }

    private void setCustomers(Map<Long, Customer> customers) {
        customers = customers;
    }

//    public static void tearDown(){
//        customers=null;
//    }
    static {
        setUp();
    }

    public static void setUp(){
        Customer joshua = new Customer();
        joshua.setBVN(1);
        joshua.setFirstName("Dome");
        joshua.setSurname("joshua");
        joshua.setEmail("akenz1901@gmail.com");
        joshua.setPhone("08183563309");
        joshua.setPassword("1248489284749");
        Account joshuaSavingsAccount = new Account(1000110001, AccountType.SAVINGS);
        joshuaSavingsAccount.setPin("1111");
        joshua.getAccounts().add(joshuaSavingsAccount);

        Account joshuaCurrentAccount = new Account(1000110002, AccountType.CURRENT, new BigDecimal(500000000));
        joshua.getAccounts().add(joshuaCurrentAccount);
        customers.put(joshua.getBVN(), joshua);

        Customer judas = new Customer();
        judas.setBVN(2);
        judas.setFirstName("Dome");
        judas.setSurname("joshua");
        judas.setEmail("akenz1901@gmail.com");
        judas.setPhone("08183563309");
        judas.setPassword("1248489284749");
        Account judasSavingsAccount = new Account(1000110003, AccountType.SAVINGS);
        judasSavingsAccount.setPin("1234");
        judas.getAccounts().add(judasSavingsAccount);
        customers.put(judas.getBVN(), judas);

    }
}
