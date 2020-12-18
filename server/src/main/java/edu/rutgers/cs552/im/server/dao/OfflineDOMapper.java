package edu.rutgers.cs552.im.server.dao;

import edu.rutgers.cs552.im.server.dataobject.OfflineDO;
import java.util.List;

public interface OfflineDOMapper {
    int insert(OfflineDO record);

    List<OfflineDO> selectByToID(String toID);

    int deleteByToId(String toID);
}
