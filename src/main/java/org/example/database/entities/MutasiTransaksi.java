package org.example.database.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class MutasiTransaksi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="accountnumber")
    private Nasabah nasabah;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTransaction = LocalDateTime.now();

    @NotNull @NotEmpty
    private String description;

    @NotNull @Min(0)
    private BigDecimal nominal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Nasabah getNasabah() {
        return nasabah;
    }

    public void setNasabah(Nasabah nasabah) {
        this.nasabah = nasabah;
    }

    public LocalDateTime getWaktuTransaksi() {
        return dateTransaction;
    }

    public void setWaktuTransaksi(LocalDateTime dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getKeterangan() {
        return description;
    }

    public void setKeterangan(String description) {
        this.description = description;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }
}
