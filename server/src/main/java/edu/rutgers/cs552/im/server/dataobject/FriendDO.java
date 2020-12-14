package edu.rutgers.cs552.im.server.dataobject;

public class FriendDO {

    private Integer id;


    private String userID1;


    private String userID2;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUserID1() {
        return userID1;
    }


    public void setUserID(String userID1) {
        this.userID1 = userID1;
    }

    public String getUserID2() {
        return userID1;
    }


    public void setUserID2(String userID2) {
        this.userID2 = userID2;
    }
}
