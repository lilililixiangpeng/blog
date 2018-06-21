package demo.web.mapper;


import demo.web.model.Admin;
import demo.web.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminServiceMapper {

    public Admin SearchAdminById(@Param("aid") int aid);

    public int SearchIdByName(@Param("username") String username);

    public String SearchEmailByName(@Param("username") String username);

    public void UpdatePassword(@Param("username") String username,@Param("password")String password);

}
