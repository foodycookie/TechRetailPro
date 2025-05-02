package techretailpro.objects;

public class Transaction {
    private int choosenMethod;
    private String cardNo;
    private int cvv;
    private String accountNo;
    
    public Transaction(int choosenMethod){
        this.choosenMethod = choosenMethod;
    }
    
    public Transaction(){
    }
    
    public int getChoosenMethod() {
        return choosenMethod;
    }

    public String getCardNo() {
        return cardNo;
    }

    public int getCvv() {
        return cvv;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setChoosenMethod(int choosenMethod) {
        this.choosenMethod = choosenMethod;
    }
}