package cn.com.flat.head.pojo;
//首页登录信息
public class HomeSignIn {
    private String currentUser;
    private String lastLogInTime;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getLastLogInTime() {
        return lastLogInTime;
    }

    public void setLastLogInTime(String lastLogInTime) {
        this.lastLogInTime = lastLogInTime;
    }
}
