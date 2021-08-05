package com.magic233.entity;

public class Transaction {
    private String hash;

    private Long blocknumber;

    private String fromAddress;

    private String toAddress;

    private String input;

    private String methodid;

    private String tokenamount;

    private Double tokenamount10;

    private String tokenaddress;

    private Long time;

    private Double price;

    private Double decimals;

    private Double value;

    private Long startTime;

    private Long endTime;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getBlocknumber() {
        return blocknumber;
    }

    public void setBlocknumber(Long blocknumber) {
        this.blocknumber = blocknumber;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getMethodid() {
        return methodid;
    }

    public void setMethodid(String methodid) {
        this.methodid = methodid;
    }

    public String getTokenamount() {
        return tokenamount;
    }

    public void setTokenamount(String tokenamount) {
        this.tokenamount = tokenamount;
    }

    public Double getTokenamount10() {
        return tokenamount10;
    }

    public void setTokenamount10(Double tokenamount10) {
        this.tokenamount10 = tokenamount10;
    }

    public String getTokenaddress() {
        return tokenaddress;
    }

    public void setTokenaddress(String tokenaddress) {
        this.tokenaddress = tokenaddress;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDecimals() {
        return decimals;
    }

    public void setDecimals(Double decimals) {
        this.decimals = decimals;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}