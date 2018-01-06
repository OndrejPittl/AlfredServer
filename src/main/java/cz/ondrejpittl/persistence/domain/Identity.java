package cz.ondrejpittl.persistence.domain;

import java.util.Date;

public class Identity {

    private Long userId;

    private String token;

    private Date tokenExpiration;


    public Identity() { }

    public Identity(Long userId, String token, Date tokenExpiration) {
        this.userId = userId;
        this.token = token;
        this.tokenExpiration = tokenExpiration;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Date tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }
}
