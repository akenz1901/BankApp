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
import mavenBank.Exceptions.MavenBankDateException;
import mavenBank.Exceptions.MavenBankException;
import mavenBank.Exceptions.MavenBankLoanException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoanEngineByRelationLengthTest {
    private LoanRequest joshuaLoanRequest;
    private AccountService accountService;
    private LoanEngine loanEngine;
    private Customer joshua;

    @BeforeEach
    void setUp() {
        loanEngine = new LoanEngineByRelationLength();
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
    }

    @Test
    void approveLoanRequestWithNullCustomer() {
        assertThrows(MavenBankLoanException.class, () -> loanEngine.calculateAmountAutoApprove(null, new SavingsAccount()));
    }

    @Test
    void approveLoanRequestWithNullAccount() {
        assertThrows(MavenBankLoanException.class, () -> loanEngine.calculateAmountAutoApprove(joshua, null));
    }

    @Test
    void calculateAmountAutoApproveForCustomerRelationShipLengthTwoMonths() {
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            joshuaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);
            joshua.setRelationshipStartDate(LocalDateTime.now().minusMonths(2));
            BigDecimal amountApproved = loanEngine.calculateAmountAutoApprove(joshua, joshuaCurrentAccount);
            assertEquals(0, amountApproved.intValue());
        } catch (MavenBankException e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculateAmountAutoApproveForCustomerRelationShipLengthThreeToFiveMonths() {
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            joshuaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);
            joshua.setRelationshipStartDate(LocalDateTime.now().minusMonths(3));
            BigDecimal amountApproved = loanEngine.calculateAmountAutoApprove(joshua, joshuaCurrentAccount);
            assertEquals(1009000, amountApproved.intValue());
        } catch (MavenBankException e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculateAmountAutoApproveForCustomerRelationShipLengthOfSixToElevenMonths() {
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            joshuaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);
            joshua.setRelationshipStartDate(LocalDateTime.now().minusMonths(11));
            BigDecimal amountApproved = loanEngine.calculateAmountAutoApprove(joshua, joshuaCurrentAccount);
            assertEquals(2018000, amountApproved.intValue());
        } catch (MavenBankException e) {
            e.printStackTrace();
        }
    }
    @Test
    void calculateAmountAutoApproveForCustomerRelationShipLengthOfTwelveToSeventeenMonth() {
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            joshuaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);
            joshua.setRelationshipStartDate(LocalDateTime.now().minusMonths(17));
            BigDecimal amountApproved = loanEngine.calculateAmountAutoApprove(joshua, joshuaCurrentAccount);
            assertEquals(3027000, amountApproved.intValue());
        } catch (MavenBankException e) {
            e.printStackTrace();
        }
    }
    @Test
    void calculateAmountAutoApproveForCustomerRelationShipLengthOfEighteenToTwentyThreeMonth() {
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            joshuaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);
            joshua.setRelationshipStartDate(LocalDateTime.now().minusMonths(21));
            BigDecimal amountApproved = loanEngine.calculateAmountAutoApprove(joshua, joshuaCurrentAccount);
            assertEquals(4036000, amountApproved.intValue());
        } catch (MavenBankException e) {
            e.printStackTrace();
        }
    }
    @Test
    void calculateAmountAutoApproveForCustomerRelationShipLengthOfTwentyFourMonthAbove() {
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            joshuaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);
            joshua.setRelationshipStartDate(LocalDateTime.now().minusMonths(100));
            BigDecimal amountApproved = loanEngine.calculateAmountAutoApprove(joshua, joshuaCurrentAccount);
            assertEquals(5045000, amountApproved.intValue());
        } catch (MavenBankException e) {
            e.printStackTrace();
        }
    }
    @Test
    void calculateAmountAutoApproveThrowsAnExceptionIfNegativeMonthValueIsEntered() {
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            joshuaCurrentAccount.setAccountLoanRequest(joshuaLoanRequest);
            assertThrows(MavenBankDateException.class, ()-> joshua.setRelationshipStartDate(LocalDateTime.now().minusMonths(-173)));
        } catch (MavenBankException e) {
            e.printStackTrace();
        }
    }
}