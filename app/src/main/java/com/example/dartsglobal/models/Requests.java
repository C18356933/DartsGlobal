package com.example.dartsglobal.models;

public class Requests {
    private String playerOneId;
    private String playerOneName;
    private String requestId;
    private String status;

    public Requests() {
    }

    public Requests(String playerOneId, String playerOneName, String requestId, String status) {
        this.playerOneId = playerOneId;
        this.playerOneName = playerOneName;
        this.requestId = requestId;
        this.status = status;
    }

    public String getPlayerOneId() {
        return playerOneId;
    }

    public void setPlayerOneId(String playerOneId) {
        this.playerOneId = playerOneId;
    }

    public String getPlayerOneName() {
        return playerOneName;
    }

    public void setPlayerOneName(String playerOneName) {
        this.playerOneName = playerOneName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
