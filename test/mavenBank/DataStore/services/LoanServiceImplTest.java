package mavenBank.DataStore.services;

import Entities.Account;
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
    private AccountService accountService;
    private LoanService loanService;
    @BeforeEach
    void setUp() {
        loanService = new LoanServiceImpl();
        accountService = new AccountServiceImpl();

        joshuaLoan = new Loan();
        joshuaLoan.setApplyDate(LocalDate.now());
        joshuaLoan.setAmount(BigDecimal.valueOf(9000000));
        joshuaLoan.setInterest(0.1);
        joshuaLoan.setStatus(LoanStatus.NEW);
        joshuaLoan.setTenor(24);
        joshuaLoan.setTypeOfLoan(LoanType.SME);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void approveLoan(){
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
    }@Test
    void approveLoanWithNullLoan(){
        assertThrows(MavenBankLoanException.class, ()->loanService.approveLoan(null));
    }
}