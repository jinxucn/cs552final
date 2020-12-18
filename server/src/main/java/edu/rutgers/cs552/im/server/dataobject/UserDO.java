package edu.rutgers.cs552.im.server.dataobject;

// dao for user
public class UserDO {
    private Integer id;


    private String userID;


    private String password;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUserID() {
        return userID;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

}