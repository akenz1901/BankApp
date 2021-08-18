package mavenBank;

import Entities.Customer;
import Entities.SavingsAccount;
import mavenBank.DataStore.CustomerRepo;
import mavenBank.DataStore.services.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    SavingsAccount joshuaAccount;
    Customer joshua;
    @BeforeEach
    void setUp () {
        joshuaAccount = new SavingsAccount();
        joshua = new Customer();
    }
    @Test
    void openAccount(){
        joshua.setBVN(BankService.generateBVN());
        joshua.setFirstName("Dome");
        joshua.setSurname("joshua");
        joshua.setEmail("akenz1901@gmail.com");
        joshua.setPhone("08183563309");
        joshua.setPassword("1248489284749");

        joshuaAccount.setAccountNumber(BankService.generateAccountNumber());
        joshuaAccount.setBalance( new BigDecimal(5000));
        joshua.getAccounts().add(joshuaAccount);

        assertTrue(joshua.getAccounts().contains(joshuaAccount));
        assertTrue(CustomerRepo.getCustomers().isEmpty());
    }
}
