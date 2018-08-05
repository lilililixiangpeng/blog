package demo.web.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.web.mapper.UserServiceMapper;
import demo.web.model.Role;
import demo.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService{

    @Autowired
    private UserServiceMapper userServiceMapper;


    public void SaveUser(User user) {
        userServiceMapper.SaveUser(user);
        userServiceMapper.AddUserRole(SearchUserId(user.getUsername()));

    }

    public String FindUser(String username) {
        return userServiceMapper.FindUser(username);
    }

    public User SearchInformation(String username){
        return userServiceMapper.SearchInformation(SearchUserId(username));
    }

    public int SearchUserId(String username){
        return userServiceMapper.SearchUserId(username);
    }

    public Boolean IfAliveUser(String username){
        boolean ifaliveuser = (userServiceMapper.FindUser(username) != null);
        return ifaliveuser;
    }

    public List<User> FindAllUser(){
        return userServiceMapper.FindAllUser();
    }

    @Transactional
    public PageInfo findAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<User> users = userServiceMapper.FindAllUser();
        return new PageInfo(users);
    }

    @Transactional
    public void DeleteUser(String username){
        int id = SearchUserId(username);
        userServiceMapper.DeleteUser(id);
        userServiceMapper.DeleteUserRole(id);
    }

    @Transactional
    public void AddUserByAdmin(User user){
        userServiceMapper.AddUserByAdmin(user);
        userServiceMapper.AddUserRole(SearchUserId(user.getUsername()));
    }

    @Transactional
    public void UpdateUser(User user){
        userServiceMapper.UpdateUser(user);
    }

    @Transactional
    public String FindUserImg(String username){
        return userServiceMapper.FindUserImg(username);
    }

    public List<Role> SearchRoleByUsername(String username){
        return userServiceMapper.SearchRoleByUsername(SearchUserId(username));
    }

    @Transactional
    public void UpdateUserbyUser(User user){
        userServiceMapper.UpdateUserbyUser(user);
    }

    @Transactional
    public void updateimgpath(String imgpath,int id){
        userServiceMapper.updateimgpath(imgpath,id);
    }
}
