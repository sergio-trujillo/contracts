package com.aconex.cost.contract.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "contracts")
public class Contract {

    private Long id;

    private String code;

    private String description;

    private String vendor;

    private double contractAmount;

    private double invoicedAmount;

    private DateTime createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public Contract setId(Long id) {
        this.id = id;
        return this;
    }

    @Column(name = "created_at")
    public DateTime getCreatedAt() {
        return createdAt;
    }

    public Contract setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public Contract setCode(String code) {
        this.code = code;
        return this;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public Contract setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(name = "vendor")
    public String getVendor() {
        return vendor;
    }

    public Contract setVendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    @Column(name = "contract_amount", columnDefinition = "NUMERIC")
    public double getContractAmount() {
        return contractAmount;
    }

    public Contract setContractAmount(double contractAmount) {
        this.contractAmount = contractAmount;
        return this;
    }

    @Column(name = "invoiced_amount", columnDefinition = "NUMERIC")
    public double getInvoicedAmount() {
        return invoicedAmount;
    }

    public Contract setInvoicedAmount(double invoicedAmount) {
        this.invoicedAmount = invoicedAmount;
        return this;
    }
}
