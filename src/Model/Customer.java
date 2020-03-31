/*
@author Mohammed Hadra (frækfyr@cphbusiness.dk)
 */
package Model;

public class Customer {

    private String Name;
    private int phoneNo;
    private String Mail;
    private int customerNo;
    private int prevOrder;

    //Getter and Setter

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public int getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(int customerNo) {
        this.customerNo = customerNo;
    }

    public int getPrevOrder() {
        return prevOrder;
    }

    public void setPrevOrder(int prevOrder) {
        this.prevOrder = prevOrder;
    }

    //Construktor

    public Customer(String name, int phoneNo, String mail, int customerNo, int prevOrder) {
        this.Name = name;
        this.phoneNo = phoneNo;
        this.Mail = mail;
        this.customerNo = customerNo;
        this.prevOrder = prevOrder;
    }

    //toString

    @Override
    public String toString() {
        return "Customer{" +
                "Name='" + Name + '\'' +
                ", phoneNo=" + phoneNo +
                ", Mail='" + Mail + '\'' +
                ", customerNo=" + customerNo +
                ", prevOrder=" + prevOrder +
                '}';
    }
}


