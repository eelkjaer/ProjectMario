/*
@author Mohammed Hadra (cph-mh879@cphbusiness.dk)
 */
package Model;

public class Customer {

    private String name;
    private int phoneNo;
    private String mail;
    private int prevOrder = 0;

    //Construktor

    public Customer(String name, int phoneNo, String mail) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.mail = mail;
        this.prevOrder++;
    }

    public Customer(String name, int phoneNo, String mail, int prevOrder){
        this.name = name;
        this.phoneNo = phoneNo;
        this.mail = mail;
        this.prevOrder = prevOrder;
    }

    //Getter and Setter


    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getPrevOrder() {
        return prevOrder;
    }

    public void addNewOrder() {
        this.prevOrder++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return phoneNo == customer.phoneNo;
    }

    @Override
    public int hashCode() {
        return phoneNo;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "Name='" + name + '\'' +
                ", phoneNo=" + phoneNo +
                ", Mail='" + mail + '\'' +
                ", prevOrder=" + prevOrder +
                '}';
    }
}


