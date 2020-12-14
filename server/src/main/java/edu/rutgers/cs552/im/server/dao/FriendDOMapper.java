package edu.rutgers.cs552.im.server.dao;

import edu.rutgers.cs552.im.server.dataobject.FriendDO;
import java.util.List;

public interface FriendDOMapper {
    int insert(FriendDO record);

    List<FriendDO> selectByUserID(String userID);

}