package demo.web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentifyingCodeService {

    @Autowired
    UserService userService;

    public Boolean checkCode(String username, String password){
        //这里判断参数中的用户数据是否和数据库中的数据一致，在usermapper中写判断逻辑
        if (userService.IfAliveUser(username)){
            String ifpassword = userService.FindUser(username);
            if (ifpassword.equals(password)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
