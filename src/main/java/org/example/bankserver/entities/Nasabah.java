package org.example.bankserver.entities;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "datanasabah")
public class Nasabah {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String fullname;
    String address;
    String phonenumber;
    String username;
    String password;
    String status = "Active";
    String accountnumber;
    Integer balance = 500000;

    public Nasabah(){}




    //------GetterSetter---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nasabah nasabah = (Nasabah) o;
        return Objects.equals(id, nasabah.id) && Objects.equals(fullname, nasabah.fullname) && Objects.equals(address, nasabah.address) && Objects.equals(phonenumber, nasabah.phonenumber) && Objects.equals(username, nasabah.username) && Objects.equals(password, nasabah.password) && Objects.equals(status, nasabah.status) && Objects.equals(accountnumber, nasabah.accountnumber) && Objects.equals(balance, nasabah.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullname, address, phonenumber, username, password, status, accountnumber, balance);
    }

    @Override
    public String toString() {
        return "Nasabah {" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", address='" + address + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", accountnumber='" + accountnumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
