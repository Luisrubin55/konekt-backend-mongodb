package com.konekt.backend.konket_backend.Entities.DTO;

public class MessageDTO {
    private String message;
    private int statusCode;
    private Exception ex;

    public MessageDTO() {
    }

    public MessageDTO(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public MessageDTO(String message, int statusCode, Exception ex) {
        this.message = message;
        this.statusCode = statusCode;
        this.ex = ex;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }
}
