package edu.utn.TpFinal.session;

import edu.utn.TpFinal.model.UserLogin;

import java.util.Date;

public class Session {

    String token;
    UserLogin loggedUserLogin;
    Date lastAction;

    public Session(String token, UserLogin loggedUserLogin, Date lastAction) {
        this.token = token;
        this.loggedUserLogin = loggedUserLogin;
        this.lastAction = lastAction;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserLogin getLoggedUser() {
        return loggedUserLogin;
    }

    public void setLoggedUser(UserLogin loggedUserLogin) {
        this.loggedUserLogin = loggedUserLogin;
    }

    public Date getLastAction() {
        return lastAction;
    }

    public void setLastAction(Date lastAction) {
        this.lastAction = lastAction;
    }
}