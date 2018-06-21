package demo.web.service;


import demo.web.mapper.AdminServiceMapper;
import demo.web.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    @Autowired
    AdminServiceMapper adminServiceMapper;

    @Transactional
    public Admin SearchUserByName(String username){
        return adminServiceMapper.SearchAdminById(SearchIdByName(username));
    }

    @Transactional
    public int SearchIdByName(String username){
        return adminServiceMapper.SearchIdByName(username);
    }

    @Transactional
    public String SearchEmailByName(String username){
        return adminServiceMapper.SearchEmailByName(username);
    }

    @Transactional
    public void UpdatePassword(String username,String password){
        adminServiceMapper.UpdatePassword(username,password);
    }
}
