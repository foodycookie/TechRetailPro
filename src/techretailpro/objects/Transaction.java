/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assg;
/**
 *
 * @author TAY TIAN YOU
 */
public class Transaction {
    private int choosenMethod;
    private String cardNo;
    private int cvv;
    private String accountNo;
    private String password;
    
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
    
    public String getPassword() {
        return password;
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
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setChoosenMethod(int choosenMethod) {
        this.choosenMethod = choosenMethod;
    }
    
}
