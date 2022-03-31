package com.shopping.merchant.catalogue.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Merchant_Catalogue")

public class Merchant {

    @Column(insertable = false, updatable = false, columnDefinition="serial")
    private Long merchantId;

    private String merchantName;

    @Id
    private String gstin;

    private String pan;

    private String address;

    private Long accountNumber;

    private Long phoneNumber;

    @CreationTimestamp
    @Column(updatable = false)
    private Date created;

    @UpdateTimestamp
    private Date modified;


    public Merchant() {
    }

    public Merchant(String merchantName, String gstin, String pan,
                    String address, Long accountNumber, Long phoneNumber) {
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.gstin = gstin;
        this.pan = pan;
        this.address = address;
        this.accountNumber = accountNumber;
        this.phoneNumber = phoneNumber;
        this.created = created;
        this.modified = modified;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "merchantId=" + merchantId +
                ", merchantName='" + merchantName + '\'' +
                ", gstin='" + gstin + '\'' +
                ", pan='" + pan + '\'' +
                ", address='" + address + '\'' +
                ", accountNumber=" + accountNumber +
                ", phoneNumber=" + phoneNumber +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
