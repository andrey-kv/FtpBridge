package com.epam.ankov.FtpBridge.models;

public class JsonResponse {
    private String response;

    public JsonResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
