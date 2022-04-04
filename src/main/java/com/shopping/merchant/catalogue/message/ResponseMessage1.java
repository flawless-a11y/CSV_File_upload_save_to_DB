package com.shopping.merchant.catalogue.message;

import com.shopping.merchant.catalogue.entity.Merchant;

import java.util.List;

public class ResponseMessage1 {

    private String status;

    private String message;

    private List<Merchant> data ;

    public ResponseMessage1() {
    }


    public ResponseMessage1(String status, String message, List<Merchant> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Merchant> getData() {
        return data;
    }

    public void setData(List<Merchant> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

