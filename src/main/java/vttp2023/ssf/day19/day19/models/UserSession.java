package vttp2023.ssf.day19.day19.models;

public class UserSession {
    
    private User user;
    private int loginCount;
    public boolean hasCaptcha;
    private int a;
    private int b;



    public UserSession() {
        this.loginCount = 2;
        this.hasCaptcha = false;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public int getLoginCount() {
        return loginCount;
    }


    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }


    public boolean isHasCaptcha() {
        return hasCaptcha;
    }


    public void setHasCaptcha(boolean hasCaptcha) {
        this.hasCaptcha = hasCaptcha;
    }

    

    public int getA() {
        return a;
    }


    public void setA(int a) {
        this.a = a;
    }


    public int getB() {
        return b;
    }


    public void setB(int b) {
        this.b = b;
    }


    public void decrementLoginCount() {
        this.loginCount = loginCount - 1;
    }

    

}
