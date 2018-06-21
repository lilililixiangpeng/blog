package demo.web.mapper;


import demo.web.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserFriendMapper {

    public List<User> GetFriendbyId(@Param("id")int id);

}
