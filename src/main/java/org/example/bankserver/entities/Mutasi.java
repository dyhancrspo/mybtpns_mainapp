package org.example.bankserver.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "mutasi")
public class Mutasi {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMutasi;

    private String accountnumber;

    private Double nominal;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp waktuMutasi;


    public Mutasi() {
    }

//--------------GetterSetter--------------------


    public Long getIdMutasi() {
        return idMutasi;
    }

    public void setIdMutasi(Long idMutasi) {
        this.idMutasi = idMutasi;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public Double getNominal() {
        return nominal;
    }

    public void setNominal(Double nominal) {
        this.nominal = nominal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getWaktuMutasi() {
        return waktuMutasi;
    }

    public void setWaktuMutasi(Timestamp waktuMutasi) {
        this.waktuMutasi = waktuMutasi;
    }

}
