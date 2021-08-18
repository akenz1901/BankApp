package mavenBank.DataStore.services;

import mavenBank.Account;
import mavenBank.Exceptions.MavenBankException;
import mavenBank.AccountType;
import mavenBank.Customer;
import mavenBank.DataStore.CustomerRepo;
import mavenBank.Exceptions.MavenBankTransactionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {
    private AccountService accountService;
    private Customer abu;
    private Customer bessie;
    private Account abuAccount;
    private Account bessieAccount;
    @BeforeEach
    void setUp(){
        accountService = new AccountServiceImpl();
        abu = new Customer();
        bessie = new Customer();

        abu.setBVN(BankService.generateBVN());
        abu.setFirstName("Dome");
        abu.setSurname("joshua");
        abu.setEmail("akenz1901@gmail.com");
        abu.setPhone("08183563309");
        abu.setPassword("1248489284749");

        bessie.setBVN(BankService.generateBVN());
        bessie.setFirstName("Dome");
        bessie.setSurname("joshua");
        bessie.setEmail("akenz1901@gmail.com");
        bessie.setPhone("08183563309");
        bessie.setPassword("1248489284749");
    }
    @AfterEach
    void tearDown(){
        BankService.tearDown();
        CustomerRepo.setUp();
    }
    @Test
    void openAccount(){
        assertFalse(CustomerRepo.getCustomers().containsKey(abu.getBVN()));
        assertEquals(1000110003, BankService.getCurrentAccountNumber());
        assertEquals(4, BankService.getCurrentBVN());

        try {
            long account = accountService.openAccount(abu, AccountType.SAVINGS);
            assertFalse(CustomerRepo.getCustomers().isEmpty());
            assertEquals(1000110004, BankService.getCurrentAccountNumber());
            assertTrue(CustomerRepo.getCustomers().containsKey(abu.getBVN()));
            assertFalse(abu.getAccounts().isEmpty());
            assertEquals(account, abu.getAccounts().get(0).getAccountNumber());
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
         Optional<Customer> optionalJoshua = CustomerRepo.getCustomers().values().stream().findFirst();
        Customer joshua = (optionalJoshua.isEmpty())? null : optionalJoshua.get();
        assertFalse(CustomerRepo.getCustomers().isEmpty());
        assertEquals(1000110003, BankService.getCurrentAccountNumber());

        assertThrows(MavenBankException.class, ()-> accountService.openAccount(joshua, AccountType.SAVINGS));
        assertEquals(2, joshua.getAccounts().size());
        assertEquals(1000110003, BankService.getCurrentAccountNumber());
    }
    @Test
    void openCurrentAccount(){
//        assertFalse(CustomerRepo.getCustomers().containsKey(abu.getBVN()));
        assertEquals(1000110003, BankService.getCurrentAccountNumber());

        try {
            long account = accountService.openAccount(abu, AccountType.CURRENT);
            assertFalse(CustomerRepo.getCustomers().isEmpty());
            assertEquals(1000110004, BankService.getCurrentAccountNumber());
            assertTrue(CustomerRepo.getCustomers().containsKey(abu.getBVN()));
            assertFalse(abu.getAccounts().isEmpty());
            assertEquals(account, abu.getAccounts().get(0).getAccountNumber());
        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
    @Test
    void twoCustomersCanOpenAccount(){
//        assertFalse(CustomerRepo.getCustomers().containsKey(abu.getBVN()));
        assertEquals(1000110003, BankService.getCurrentAccountNumber());
        assertFalse(CustomerRepo.getCustomers().isEmpty());

        try {
            long account = accountService.openAccount(abu, AccountType.SAVINGS);
            assertFalse(CustomerRepo.getCustomers().isEmpty());
            assertEquals(1000110004, BankService.getCurrentAccountNumber());
            assertTrue(CustomerRepo.getCustomers().containsKey(abu.getBVN()));
            assertFalse(abu.getAccounts().isEmpty());
            assertEquals(account, abu.getAccounts().get(0).getAccountNumber());

            long newAccount = accountService.openAccount(bessie, AccountType.SAVINGS);
            assertFalse(CustomerRepo.getCustomers().isEmpty());
            assertEquals(1000110005, BankService.getCurrentAccountNumber());
            assertTrue(CustomerRepo.getCustomers().containsKey(bessie.getBVN()));
            assertFalse(bessie.getAccounts().isEmpty());
            assertEquals(newAccount, bessie.getAccounts().get(0).getAccountNumber());

        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
    @Test
    void deposit(){
        try {
            Account abuSavingsAccount = accountService.findAccount(1000110001);
            assertEquals(BigDecimal.ZERO, abuSavingsAccount.getBalance());


            BigDecimal accountBalance = accountService.deposit(new BigDecimal(5000), 1000110001);
            assertEquals(new BigDecimal(5000), accountBalance);
        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
    @Test
    void findAccount(){
        try {
            Account johnSavingsAccount = accountService.findAccount(1000110002);
            assertNotNull(johnSavingsAccount);
            assertEquals(1000110002, johnSavingsAccount.getAccountNumber());

        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
    @Test
    void findAccountWithInvalidAccountNumber(){
        try {
            Account johnSavingsAccount = accountService.findAccount(2000);
            assertNull(johnSavingsAccount);

        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
    @Test
    void depositNegativeAmount(){
        try {
            Account joshuaSavingsAccount = accountService.findAccount(1000110001);
            assertEquals(BigDecimal.ZERO, joshuaSavingsAccount.getBalance());

            BigDecimal accountBalance = accountService.deposit(new BigDecimal(-5000), 1000110001);
        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
    @Test
    void depositNegativeAmountThrowsAnException(){
        assertThrows(MavenBankTransactionException.class, ()-> accountService.deposit(new BigDecimal(-5000), 1000110002));
    }
    @Test
    void depositWithVeryLargeAmount(){
        try {
            Account abuSavingsAccount = accountService.findAccount(1000110001);
            assertEquals(BigDecimal.ZERO, abuSavingsAccount.getBalance());
            BigDecimal depositAmount = new BigDecimal("10000000000000");

            BigDecimal accountBalance = accountService.deposit(depositAmount, 1000110001);
            assertEquals(depositAmount, accountBalance);
        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
    @Test
    void depositWithInvalidAccountNumberShouldNotDeposit(){
        try {
            Account newAccount = accountService.findAccount(1000110005);
            assertNull(newAccount);
        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
}