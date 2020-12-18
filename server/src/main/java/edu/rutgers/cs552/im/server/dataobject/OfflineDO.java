package edu.rutgers.cs552.im.server.dataobject;

public class OfflineDO {
    private Integer id;

    private String fromID;

    private String toID;

    private String message;

    private Integer is_sent;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getFromID() {
        return fromID;
    }


    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getToID() {
        return toID;
    }


    public void setToID(String toID) {
        this.toID = toID;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIs_sent() {
        return is_sent;
    }

    public void setIs_sent(Integer is_sent) {
        this.is_sent = is_sent;
    }



}
