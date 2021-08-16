package mavenBank.DataStore.services;

import mavenBank.Exceptions.MavenBankException;
import mavenBank.AccountType;
import mavenBank.Customer;
import mavenBank.DataStore.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {
    private AccountService accountService;
    private Customer joshua;
    @BeforeEach
    void setUp(){
        accountService = new AccountServiceImpl();
        joshua = new Customer();
        joshua.setBVN(BankService.generateBVN());
        joshua.setFirstName("Dome");
        joshua.setSurname("joshua");
        joshua.setEmail("akenz1901@gmail.com");
        joshua.setPhone("08183563309");
        joshua.setPassword("1248489284749");
    }
    @Test
    void openAccount(){
        assertFalse(CustomerRepo.getCustomers().containsKey(joshua.getBVN()));
        assertEquals(0, BankService.getCurrentAccountNumber());
        assertTrue(CustomerRepo.getCustomers().isEmpty());

        try {
            long account = accountService.openAccount(joshua, AccountType.SAVINGS);
            assertFalse(CustomerRepo.getCustomers().isEmpty());
            assertEquals(1, BankService.getCurrentAccountNumber());
            assertTrue(CustomerRepo.getCustomers().containsKey(joshua.getBVN()));
            assertFalse(joshua.getAccounts().isEmpty());
            assertEquals(account, joshua.getAccounts().get(0).getAccountNumber());
        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
    @Test
    void testOpenAccountThrowsAnException(){
        assertThrows(MavenBankException.class, ()-> accountService.openAccount(null, AccountType.SAVINGS));
    }
    @Test
    void SameCustomerCannotOpenSameTypeOfAccount(){
        try {
            long account = accountService.openAccount(joshua, AccountType.SAVINGS);
            assertFalse(CustomerRepo.getCustomers().isEmpty());
            assertEquals(1, BankService.getCurrentAccountNumber());
            assertTrue(CustomerRepo.getCustomers().containsKey(joshua.getBVN()));
            assertEquals(account, joshua.getAccounts().get(0).getAccountNumber());
        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
        assertThrows(MavenBankException.class, ()-> accountService.openAccount(joshua, AccountType.SAVINGS));
        assertEquals(1, joshua.getAccounts().size());
        assertEquals(1, BankService.getCurrentAccountNumber());
    }
    @Test
    void openCurrentAccount(){
        assertFalse(CustomerRepo.getCustomers().containsKey(joshua.getBVN()));
        assertEquals(0, BankService.getCurrentAccountNumber());
        assertTrue(CustomerRepo.getCustomers().isEmpty());

        try {
            long account = accountService.openAccount(joshua, AccountType.CURRENT);
            assertFalse(CustomerRepo.getCustomers().isEmpty());
            assertEquals(1, BankService.getCurrentAccountNumber());
            assertTrue(CustomerRepo.getCustomers().containsKey(joshua.getBVN()));
            assertFalse(joshua.getAccounts().isEmpty());
            assertEquals(account, joshua.getAccounts().get(0).getAccountNumber());
        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }

}