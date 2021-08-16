package mavenBank.DataStore.services;

public class BankService {
    private static long currentBVN = 0;
    private static long currentAccountNumber = 0;

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
}
