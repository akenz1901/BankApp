package mavenBank.engines;

import Entities.Account;
import Entities.Customer;
import Entities.LoanRequest;
import Entities.SavingsAccount;
import mavenBank.DataStore.CustomerRepo;
import mavenBank.DataStore.LoanRequestStatus;
import mavenBank.DataStore.LoanType;
import mavenBank.DataStore.services.AccountService;
import mavenBank.DataStore.services.AccountServiceImpl;
import mavenBank.DataStore.services.BankService;
import mavenBank.Exceptions.MavenBankException;
import mavenBank.Exceptions.MavenBankLoanException;
import mavenBank.engines.LoanEngine;
import mavenBank.engines.LoanEngineByBalance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LoanEngineByBalanceTest {
    private LoanRequest joshuaLoanRequest;
    private AccountService accountService;
    private LoanEngine loanEngine;
    private Customer joshua;

    @BeforeEach
    void setUp() {
        loanEngine = new LoanEngineByBalance();
        accountService = new AccountServiceImpl();

        joshuaLoanRequest = new LoanRequest();
        joshuaLoanRequest.setApplyDate(LocalDate.now());
        joshuaLoanRequest.setInterest(0.1);
        joshuaLoanRequest.setStatus(LoanRequestStatus.NEW);
        joshuaLoanRequest.setTenor(24);
        joshuaLoanRequest.setTypeOfLoan(LoanType.SME);

        Optional<Customer> optionalCustomer = CustomerRepo.getCustomers().values().stream().findFirst();
        joshua = (optionalCustomer.isPresent()) ? optionalCustomer.get() : null;
        assertNotNull(joshua);
    }

    @AfterEach
    void tearDown() {
        BankService.tearDown();
        CustomerRepo.setUp();
    }

    @Test
    void calculateAmountAutoApproveWithNegative(){
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            joshuaCurrentAccount.setBalance(BigDecimal.valueOf(-900000));
            joshuaLoanRequest.setAmount(BigDecimal.valueOf(9000000));
            joshuaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);
            BigDecimal amountApproved = loanEngine.calculateAmountAutoApprove(joshua, joshuaCurrentAccount);
            assertEquals(0, amountApproved.intValue());
        }catch (MavenBankException e){
            e.printStackTrace();
        }
    }
    @Test
    void calculateAmountAutoApprove(){
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            joshuaLoanRequest.setAmount(BigDecimal.valueOf(9000000));
            joshuaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);
            BigDecimal amountApproved = loanEngine.calculateAmountAutoApprove(joshua, joshuaCurrentAccount);
            assertEquals(10090000, amountApproved.intValue());
        }catch (MavenBankException e){
            e.printStackTrace();
        }
    }
    void calculateAmountAutoPreApproveForCustomerWithNegativeBalance(){

    }
    @Test
    void approveLoanRequestWithNullCustomer(){
        assertThrows(MavenBankLoanException.class, ()-> loanEngine.calculateAmountAutoApprove(null, new SavingsAccount()));
    }
    @Test
    void approveLoanRequestWithNullAccount(){
        assertThrows(MavenBankLoanException.class, ()-> loanEngine.calculateAmountAutoApprove(joshua, null));
    }
}
