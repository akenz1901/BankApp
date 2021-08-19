package mavenBank.DataStore.services;

import java.time.Year;

public class BankService {
    private static long currentBVN = 2;
    private static long currentAccountNumber = 1000110003;
    private static long transactionId = 0;

    public static long getTransactionId() {
        return transactionId;
    }
    public static long generateId(){
        transactionId++;
        return transactionId;
    }
    private static void setTransactionId(long transactionId) {
        BankService.transactionId = transactionId;
    }

    private static void setCurrentBVN(long currentBVN) {
        BankService.currentBVN = currentBVN;
    }

    public static long getCurrentAccountNumber() {
        return currentAccountNumber;
    }

    private static void setCurrentAccountNumber(long currentAccountNumber) {
        BankService.currentAccountNumber = currentAccountNumber;
    }
    public static long getCurrentBVN() {
        return currentBVN;
    }

    public static long generateBVN(){
        currentBVN++;
        return currentBVN;
    }
    public static long generateAccountNumber(){
        currentAccountNumber++;
        return currentAccountNumber;
    }

    public static void tearDown() {
        currentAccountNumber = 1000110003;
        currentBVN = 2;
    }
}
