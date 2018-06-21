package demo.web.mapper;


import demo.web.model.Role;
import demo.web.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface UserServiceMapper {

    public void SaveUser(User user);

    public void AddUserByAdmin(User user);

    public void AddUserRole(@Param("id") int id);

    public void DeleteUser(@Param("id") int id);

    public void DeleteUserRole(@Param("id") int id);

    public String FindUser(@Param("username") String username);

    public User SearchInformation(@Param("id") int id);

    public int SearchUserId(@Param("username") String username);

    public List<User> FindAllUser();

    public void UpdateUser(User user);

    public String FindUserImg(@Param("username")String username);

    public List<Role> SearchRoleByUsername(@Param("id") int id);

    public void UpdateUserbyUser(User user);

    List<User> findByPage();

    public void updateimgpath(@Param("imgpath") String imgpath,@Param("id") int id);
}