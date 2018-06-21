package demo.web.controller;


import com.github.pagehelper.PageInfo;
import demo.web.filestorage.DefauleUser;
import demo.web.model.Admin;
import demo.web.model.Role;
import demo.web.model.User;
import demo.web.security.SecureUser;
import demo.web.service.AdminService;
import demo.web.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.Authenticator;
import java.util.*;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    DefauleUser defauleUser;


    @GetMapping(path = "/admin/{tname}")
    public String center(@PathVariable String tname,Model model,@RequestParam(value = "pagenum",defaultValue = "1")int pagenum){
        List<String> auth = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities = AuthUtils.getAuthenticationObject().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")){
                String username = AuthUtils.getAuthenticationObject().getName();
                List<User> userlist = userService.FindAllUser();
                PageInfo pageInfo = userService.findAll(pagenum,10);
                List<User> users = pageInfo.getList();
                if (pagenum - 4 >= 1 && pagenum + 4 <= pageInfo.getPages()){
                    model.addAttribute("newpage",pagenum-4);
                    model.addAttribute("addpage",pagenum+4);
                }
                if (pagenum - 4 < 1 && pagenum + 4 <= pageInfo.getPages()){
                    model.addAttribute("newpage",1);
                    model.addAttribute("addpage",9);
                }
                if (pagenum - 4 > 1 && pagenum + 4 > pageInfo.getPages()){
                    model.addAttribute("newpage",pageInfo.getPages()-8);
                    model.addAttribute("addpage",pageInfo.getPages());
                }
                if (pagenum - 4 < 1 && pagenum + 4 > pageInfo.getPages()){
                    model.addAttribute("newpage",1);
                    model.addAttribute("addpage",pageInfo.getPages());
                }
                model.addAttribute("username",username);
                model.addAttribute("email",adminService.SearchEmailByName(username));
                model.addAttribute("userlist",users);
                model.addAttribute("totalpage",pageInfo.getPages());
                model.addAttribute("nowpage",pageInfo.getPageNum());
                return "admin/"+ tname;
            }
        }
        model.addAttribute("adminerror",true);
        return "information/index";

    }

    @GetMapping(path = "/admin/delete")
    public String DeleteUser(Model model,@RequestParam(value = "username")String deleteusername){
        userService.DeleteUser(deleteusername);
        String username = AuthUtils.getAuthenticationObject().getName();
        List<User> userlist = userService.FindAllUser();
        PageInfo pageInfo = userService.findAll(1,10);
        List<User> users = pageInfo.getList();
        model.addAttribute("newpage",1);
        model.addAttribute("addpage",9);
        model.addAttribute("username",username);
        model.addAttribute("email",adminService.SearchEmailByName(username));
        model.addAttribute("userlist",users);
        model.addAttribute("totalpage",pageInfo.getPages());
        model.addAttribute("nowpage",pageInfo.getPageNum());
        return "admin/index";
    }

    @RequestMapping(value = "/admin/adduser", method = RequestMethod.POST)
    @ResponseBody
    public  Map<String,String> adduser(HttpServletRequest request){
        User user = new User(request.getParameter("username"),request.getParameter("password"),request.getParameter("phonenumber"),request.getParameter("email"),Integer.parseInt(request.getParameter("situation")),defauleUser.getUserimg());
        userService.AddUserByAdmin(user);
        Map<String,String> result = new HashMap<>();
        result.put("result","true");
        return result;
    }

    @RequestMapping(value = "/admin/getuser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> getuser(HttpServletRequest request){
        User user = userService.SearchInformation(request.getParameter("username"));
        Map<String,String> result = new HashMap<>();
        result.put("username",user.getUsername());
        result.put("password",user.getPassword());
        result.put("email",user.getEmail());
        result.put("phonenumber",user.getPhonenumber());
        result.put("state",String.valueOf(user.getUserstate()));
        return result;
    }

    @RequestMapping(value = "/admin/updateuser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updateuser(HttpServletRequest request){
        User user = new User(userService.SearchUserId(request.getParameter("username")),request.getParameter("username"),request.getParameter("password"),request.getParameter("phonenumber"),request.getParameter("email"),Integer.parseInt(request.getParameter("situation")));
        userService.UpdateUser(user);
        Map<String,String> result = new HashMap<>();
        result.put("state",String.valueOf(user.getUserstate()));
        return result;
    }

    @RequestMapping(value = "/admin/updatepassword",method = RequestMethod.POST)
    public String updatepassword(HttpServletRequest request){
        String username = AuthUtils.getAuthenticationObject().getName();
        adminService.UpdatePassword(username,request.getParameter("password"));
        return "redirect:/logout";
    }

}
