package mavenBank.DataStore;

public enum TransactionServiceType {
    DEPOSIT(), WITHDRAW();
    private String withdrawalMessage;
    private String depositMessage;

    public String getWithdrawalMessage() {
        return withdrawalMessage;
    }

    public void setWithdrawalMessage(String withdrawalMessage) {
        this.withdrawalMessage = withdrawalMessage;
    }

    public String getDepositMessage() {
        return depositMessage;
    }

    public void setDepositMessage(String depositMessage) {
        this.depositMessage = depositMessage;
    }
}
