package mavenBank.DataStore.services;

import Entities.*;
import mavenBank.DataStore.CustomerRepo;
import mavenBank.DataStore.LoanRequestStatus;
import mavenBank.DataStore.LoanType;
import mavenBank.Exceptions.MavenBankException;
import mavenBank.Exceptions.MavenBankLoanException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceImplTest {
    LoanRequest joshuaLoanRequest;
    private AccountService accountService;
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        loanService = new LoanServiceImpl();
        accountService = new AccountServiceImpl();

        joshuaLoanRequest = new LoanRequest();
        joshuaLoanRequest.setApplyDate(LocalDate.now());
        joshuaLoanRequest.setInterest(0.1);
        joshuaLoanRequest.setStatus(LoanRequestStatus.NEW);
        joshuaLoanRequest.setTenor(24);
        joshuaLoanRequest.setTypeOfLoan(LoanType.SME);
    }

    @AfterEach
    void tearDown() {
        BankService.tearDown();
        CustomerRepo.setUp();
    }

    @Test
    void approveLoanRequestWithAccountBalance(){
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            assertNull(joshuaCurrentAccount.getAccountLoanRequest());
            joshuaLoanRequest.setAmount(BigDecimal.valueOf(9000000));
            joshuaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);//FICO
            assertNotNull(joshuaCurrentAccount.getAccountLoanRequest());

            LoanRequest processedLoanRequest = loanService.approveLoanRequest(joshuaCurrentAccount);
            assertNotNull(processedLoanRequest);
            assertEquals(LoanRequestStatus.APPROVED, processedLoanRequest.getStatus());

        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
    @Test
    void approveLoanRequestWithNullAccount(){
        assertThrows(MavenBankLoanException.class, ()-> loanService.approveLoanRequest(null));
    }
    @Test
    void approveLoanRequestWithNullLoanRequest(){
        CurrentAccount currentAccountWithoutLoanRequest = new CurrentAccount();
        assertThrows(MavenBankLoanException.class, ()->loanService.approveLoanRequest(currentAccountWithoutLoanRequest));
    }
    @Test
    void approveLoanRequestWithNullCustomer(){
        assertThrows(MavenBankLoanException.class, ()-> loanService.approveLoanRequest(null, new SavingsAccount()));
    }
    @Test
    void approveLoanRequestWithCustomerEngagement(){
        try {
            Account judaCurrentAccount = accountService.findAccount(1000110003);
            judaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);
        }catch(MavenBankException accountCanBeFoundException){
            accountCanBeFoundException.printStackTrace();
        }
    }
    @Test
    void approveLoanWithCustomerRelationWithTotalVolume(){
        try {
            Account joshuaSavingsAccount = accountService.findAccount(1000110001);
            Optional<Customer> optionalCustomer = CustomerRepo.getCustomers().values().stream().findFirst();
            Customer joshua = optionalCustomer.isPresent() ? optionalCustomer.get() : null;
            assertNotNull(joshua);
            joshua.setRelationshipStartDate(joshuaSavingsAccount.getStartDate().minusYears(2));
            assertEquals(BigDecimal.valueOf(450000), joshuaSavingsAccount.getBalance());
            joshuaLoanRequest.setAmount(BigDecimal.valueOf(3000000));
            joshuaSavingsAccount.setAccountLoanRequest(joshuaLoanRequest);
            LoanRequest decision = loanService.approveLoanRequest(joshua, joshuaSavingsAccount);
            assertEquals(LoanRequestStatus.APPROVED, joshuaLoanRequest.getStatus());
        }catch(MavenBankException accountCanBeFoundException){
            accountCanBeFoundException.printStackTrace();
        }
    }
    @Test
    void approveLoanRequestWithAccountBalanceAndHighLoanRequestAmount(){
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            assertNull(joshuaCurrentAccount.getAccountLoanRequest());
            joshuaLoanRequest.setAmount(BigDecimal.valueOf(90000000));
            joshuaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);//FICO
            assertNotNull(joshuaCurrentAccount.getAccountLoanRequest());

            LoanRequest processedLoanRequest = loanService.approveLoanRequest(joshuaCurrentAccount);
            assertNotNull(processedLoanRequest);
            assertEquals(LoanRequestStatus.PENDING, processedLoanRequest.getStatus());

        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
    @Test
    void approveLoanRequestWithCustomerButWithoutAccount(){
        assertThrows(MavenBankLoanException.class, ()-> loanService.approveLoanRequest(new Customer(), null));
    }
}