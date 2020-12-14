package edu.rutgers.cs552.im.server.dao;

import edu.rutgers.cs552.im.server.dataobject.UserDO;


public interface UserDOMapper {
    int insert(UserDO record);

    UserDO selectByUserID(String userID);

}