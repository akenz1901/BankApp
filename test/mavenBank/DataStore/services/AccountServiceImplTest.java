package mavenBank.DataStore.services;

import Entities.Account;
import Entities.BankTransaction;
import Entities.LoanRequest;
import mavenBank.DataStore.*;
import mavenBank.Exceptions.MavenBankException;
import Entities.Customer;
import mavenBank.Exceptions.MavenBankInsufficientAmountException;
import mavenBank.Exceptions.MavenBankTransactionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
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
            long account = accountService.openAccount(abu, AccountType.SAVINGSACCOUNT);
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
        assertThrows(MavenBankException.class, ()-> accountService.openAccount(null, AccountType.SAVINGSACCOUNT));
    }

    @Test
    void SameCustomerCannotOpenSameTypeOfAccount(){
         Optional<Customer> optionalJoshua = CustomerRepo.getCustomers().values().stream().findFirst();
        Customer joshua = (optionalJoshua.isEmpty())? null : optionalJoshua.get();
        assertFalse(CustomerRepo.getCustomers().isEmpty());
        assertEquals(1000110003, BankService.getCurrentAccountNumber());
        System.out.println(joshua.getAccounts().get(0).getClass().getTypeName());
        System.out.println(joshua.getAccounts().get(0).getClass().getSimpleName());
        System.out.println(AccountType.SAVINGSACCOUNT.toString());
        assertEquals(AccountType.SAVINGSACCOUNT.toString(), joshua.getAccounts().get(0).getClass().getSimpleName().toUpperCase());

        assertThrows(MavenBankException.class, ()-> accountService.openAccount(joshua, AccountType.SAVINGSACCOUNT));
        assertEquals(2, joshua.getAccounts().size());
        assertEquals(1000110003, BankService.getCurrentAccountNumber());
    }
    @Test
    void openCurrentAccount(){
//        assertFalse(CustomerRepo.getCustomers().containsKey(abu.getBVN()));
        assertEquals(1000110003, BankService.getCurrentAccountNumber());

        try {
            long account = accountService.openAccount(abu, AccountType.CURRENTACCOUNT);
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
            long account = accountService.openAccount(abu, AccountType.SAVINGSACCOUNT);
            assertFalse(CustomerRepo.getCustomers().isEmpty());
            assertEquals(1000110004, BankService.getCurrentAccountNumber());
            assertTrue(CustomerRepo.getCustomers().containsKey(abu.getBVN()));
            assertFalse(abu.getAccounts().isEmpty());
            assertEquals(account, abu.getAccounts().get(0).getAccountNumber());

            long newAccount = accountService.openAccount(bessie, AccountType.SAVINGSACCOUNT);
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
            assertEquals(BigDecimal.valueOf(450000), abuSavingsAccount.getBalance());


            BigDecimal accountBalance = accountService.deposit(new BigDecimal(50000), 1000110001);
            assertEquals(new BigDecimal(500000), accountBalance);
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
            assertEquals(BigDecimal.valueOf(450000), joshuaSavingsAccount.getBalance());

            assertThrows(MavenBankException.class, ()-> accountService.deposit(new BigDecimal(-5000), 1000110001));
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
            BigDecimal originalBalance = abuSavingsAccount.getBalance();
            assertEquals(BigDecimal.valueOf(450000), originalBalance);
            BigDecimal depositAmount = new BigDecimal("10000000000000");

            BigDecimal accountBalance = accountService.deposit(depositAmount, 1000110001);
            BigDecimal newBalance = originalBalance.add(depositAmount);
            assertEquals(newBalance, accountBalance);
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
    @Test
    void withdraw()  {
        try {
            Account availableAccount = accountService.findAccount(1000110001);
            assertEquals(BigDecimal.valueOf(450000), availableAccount.getBalance());


            BigDecimal withdrawalAmount = accountService.withdraw(BigDecimal.valueOf(300000), availableAccount.getAccountNumber());
            assertEquals(BigDecimal.valueOf(300000), withdrawalAmount);
            assertEquals(BigDecimal.valueOf(150000), availableAccount.getBalance());

        }catch (MavenBankTransactionException ex){
            ex.printStackTrace();

        }catch (MavenBankException ex) {
            ex.printStackTrace();
        }
    }
    @Test
    void customerCannotWithdrawNegativeAmount(){
        try {
            Account availableAccount = accountService.findAccount(1000110001);
            
            assertEquals(BigDecimal.valueOf(450000), availableAccount.getBalance());
            assertThrows(MavenBankTransactionException.class, ()-> accountService.withdraw(BigDecimal.valueOf(-500000), 1000110001));

        }catch (MavenBankTransactionException ex){
            ex.printStackTrace();

        }catch (MavenBankException ex) {
            ex.printStackTrace();
        }
    }
    @Test
    void withdrawAmountHigherThanAccountBalance(){
        try {
            Account availableAccount = accountService.findAccount(1000110001);
            assertEquals(BigDecimal.valueOf(450000), availableAccount.getBalance());

            assertThrows(MavenBankInsufficientAmountException.class, ()-> accountService.withdraw(BigDecimal.valueOf(500000), availableAccount.getAccountNumber()));
            assertEquals(BigDecimal.valueOf(450000), availableAccount.getBalance());
        }catch (MavenBankTransactionException ex){
            ex.printStackTrace();
        }catch (MavenBankException ex) {
            ex.printStackTrace();
        }
    }
    @Test
    void withdrawFromAWrongAccountNumberThrowsAnException(){
        try {
            Account availableAccount = accountService.findAccount(1000110001);

            Account withdrawingAccount = accountService.findAccount(10001109);
            assertEquals(BigDecimal.valueOf(450000), availableAccount.getBalance());
        }catch(MavenBankTransactionException ex){
            ex.printStackTrace();
        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
        assertThrows(MavenBankTransactionException.class, ()-> accountService.withdraw(BigDecimal.valueOf(5000), 1000110009));
    }
    @Test
    void applyForLoan(){
        LoanRequest joshuaLoanRequest = new LoanRequest();
        joshuaLoanRequest.setApplyDate(LocalDate.now());
        joshuaLoanRequest.setAmount(BigDecimal.valueOf(50000000));
        joshuaLoanRequest.setInterest(0.1);
        joshuaLoanRequest.setStatus(LoanRequestStatus.NEW);
        joshuaLoanRequest.setTenor(24);
        joshuaLoanRequest.setTypeOfLoan(LoanType.SME);

        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            assertNull(joshuaCurrentAccount.getAccountLoanRequest());
            joshuaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);//FICO
            assertNotNull(joshuaCurrentAccount.getAccountLoanRequest());

        }catch(MavenBankTransactionException ex){
            ex.printStackTrace();
    }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
    @Test
    void addBankTransactionWithNullTransaction(){
        assertThrows(MavenBankTransactionException.class, ()-> accountService.addBankTransaction(null, abuAccount));
    }
    @Test
    void addBankTransactionWithNullAccount(){
        BankTransaction transaction = new BankTransaction(BankTransactionType.DEPOSIT, BigDecimal.valueOf(10));
        assertThrows(MavenBankTransactionException.class, ()-> accountService.addBankTransaction(transaction, null));
    }
    @Test
    void addBankTransactionWithDeposit() {
        try {
            Account judasSavingsAccount = accountService.findAccount(1000110003);
            assertNotNull(judasSavingsAccount);
            assertEquals(BigDecimal.ZERO, judasSavingsAccount.getBalance());
            assertEquals(0, judasSavingsAccount.getTransactions().size());
            BankTransaction janeDeposit = new BankTransaction(BankTransactionType.DEPOSIT, BigDecimal.valueOf(10000));
            accountService.addBankTransaction(janeDeposit, judasSavingsAccount);
            assertEquals(BigDecimal.valueOf(10000), judasSavingsAccount.getBalance());
            assertEquals(1, judasSavingsAccount.getTransactions().size());
        } catch (MavenBankException ex) {
            ex.printStackTrace();
        }
    }
    @Test
    void addBankTransactionWithNegativeDeposit(){
        try {
            Account judasSavingsAccount = accountService.findAccount(1000110003);
            assertNotNull(judasSavingsAccount);
            assertEquals(BigDecimal.ZERO, judasSavingsAccount.getBalance());
            assertEquals(0, judasSavingsAccount.getTransactions().size());

            BankTransaction janeDeposit = new BankTransaction(BankTransactionType.DEPOSIT, BigDecimal.valueOf(-10000));
            assertThrows(MavenBankTransactionException.class, ()-> accountService.addBankTransaction(janeDeposit, judasSavingsAccount));
            assertEquals(BigDecimal.ZERO, judasSavingsAccount.getBalance());
            assertEquals(0, judasSavingsAccount.getTransactions().size());
        }catch (MavenBankException ex){
            ex.printStackTrace();

        }
    }
    @Test
    void addBankTransactionForWithdrawal(){
        try{
            Account janeSavingsAccount = accountService.findAccount(1000110003);
            assertNotNull(janeSavingsAccount);
            BankTransaction depositTx = new BankTransaction(BankTransactionType.DEPOSIT, BigDecimal.valueOf(50000));
            accountService.addBankTransaction(depositTx, janeSavingsAccount);
            assertEquals(BigDecimal.valueOf(50000), janeSavingsAccount.getBalance());
            assertEquals(1, janeSavingsAccount.getTransactions().size());

            BankTransaction withdrawTx = new BankTransaction(BankTransactionType.WITHDRAW, BigDecimal.valueOf(20000));
            accountService.addBankTransaction(withdrawTx, janeSavingsAccount);
            assertEquals(BigDecimal.valueOf(30000), janeSavingsAccount.getBalance());
            assertEquals(2, janeSavingsAccount.getTransactions().size());
        }catch (MavenBankException exception){
            exception.printStackTrace();
        }
    }
    @Test
    void addBankTransactionWithNegativeWithdrawal(){
        try{
            Account judasSavingsAccount = accountService.findAccount(1000110003);
            assertNotNull(judasSavingsAccount);
            assertEquals(BigDecimal.ZERO, judasSavingsAccount.getBalance());
            assertEquals(0, judasSavingsAccount.getTransactions().size());

            BankTransaction depositTx = new BankTransaction(BankTransactionType.DEPOSIT, BigDecimal.valueOf(50000));
            accountService.addBankTransaction(depositTx, judasSavingsAccount);
            assertEquals(BigDecimal.valueOf(50000), judasSavingsAccount.getBalance());
            assertEquals(1, judasSavingsAccount.getTransactions().size());

            BankTransaction withdrawTx = new BankTransaction(BankTransactionType.WITHDRAW, BigDecimal.valueOf(-20000));
            assertThrows(MavenBankTransactionException.class, ()->accountService.addBankTransaction(withdrawTx, judasSavingsAccount));
            assertEquals(BigDecimal.valueOf(50000), judasSavingsAccount.getBalance());
            assertEquals(1, judasSavingsAccount.getTransactions().size());
        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
}