package mavenBank.DataStore.services;

import Entities.Account;
import Entities.CurrentAccount;
import Entities.Customer;
import Entities.Loan;
import mavenBank.DataStore.LoanStatus;
import mavenBank.DataStore.LoanType;
import mavenBank.Exceptions.MavenBankException;
import mavenBank.Exceptions.MavenBankLoanException;
import mavenBank.Exceptions.MavenBankTransactionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceImplTest {
    Loan joshuaLoan;
    Loan judasLoan;
    private AccountService accountService;
    private LoanService loanService;
    CurrentAccount abeebCurrent;

    @BeforeEach
    void setUp() {
        loanService = new LoanServiceImpl();
        accountService = new AccountServiceImpl();
        abeebCurrent = new CurrentAccount();

        joshuaLoan = new Loan();
        joshuaLoan.setApplyDate(LocalDate.now());
        joshuaLoan.setAmount(BigDecimal.valueOf(9000000));
        joshuaLoan.setInterest(0.1);
        joshuaLoan.setStatus(LoanStatus.NEW);
        joshuaLoan.setTenor(24);
        joshuaLoan.setTypeOfLoan(LoanType.SME);

        judasLoan = new Loan();
        judasLoan.setApplyDate(LocalDate.now());
        judasLoan.setAmount(BigDecimal.valueOf(9000000));
        judasLoan.setInterest(0.1);
        judasLoan.setStatus(LoanStatus.NEW);
        judasLoan.setTenor(24);
        judasLoan.setTypeOfLoan(LoanType.SME);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void approveLoanWithAccountBalance(){
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110002);
            assertNull(joshuaCurrentAccount.getAccountLoan());
            joshuaCurrentAccount.setAccountLoan(joshuaLoan);//FICO
            assertNotNull(joshuaCurrentAccount.getAccountLoan());

            Loan processedLoan = loanService.approveLoan(joshuaCurrentAccount);
            assertNotNull(processedLoan);
            assertEquals(LoanStatus.APPROVED, processedLoan.getStatus());

        }catch (MavenBankException ex){
            ex.printStackTrace();
        }
    }
    @Test
    void approveLoanWithNullAccount(){
        assertThrows(MavenBankLoanException.class, ()-> loanService.approveLoan(null));
    }
    @Test
    void approveLoanWithNullLoan(){

        assertThrows(MavenBankLoanException.class, ()->loanService.approveLoan(abeebCurrent));
    }
    @Test
    void approveLoanWithCustomerEngagement(){
        try {
            Account judaCurrentAccount = accountService.findAccount(1000110003);
            judaCurrentAccount.setAccountLoan(joshuaLoan);
        }catch(MavenBankException accountCanBeFoundException){
            accountCanBeFoundException.printStackTrace();
        }
    }
    @Test
    void approveLoanWithCustomerRelationWithLengthOfRelationship(){
        try {
            Account joshuaCurrentAccount = accountService.findAccount(1000110001);
            System.out.println(joshuaCurrentAccount.getStartDate());
        }catch(MavenBankException accountCanBeFoundException){
            accountCanBeFoundException.printStackTrace();
        }
    }
}