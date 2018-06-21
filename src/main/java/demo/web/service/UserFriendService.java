package demo.web.service;

import demo.web.mapper.UserFriendMapper;
import demo.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserFriendService {

    @Autowired
    UserFriendMapper userFriendMapper;

    @Autowired
    UserService userService;

    @Transactional
    public List<User> GetFriendbyUsername(String username){
        int id = userService.SearchUserId(username);
        return userFriendMapper.GetFriendbyId(id);
    }
}
