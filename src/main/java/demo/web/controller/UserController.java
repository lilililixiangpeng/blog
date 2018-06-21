package demo.web.controller;



import demo.web.model.Gallery;
import demo.web.model.Post;
import demo.web.model.Role;
import demo.web.model.User;
import demo.web.security.SecureUser;
import demo.web.service.GalleryService;
import demo.web.service.PostService;
import demo.web.service.UserFriendService;
import demo.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserFriendService userFriendService;

    @Autowired
    PostService postService;

    @Autowired
    GalleryService galleryService;

    @GetMapping(path = "/information/{tname}")
    public String login(@PathVariable String tname , Model model){
        return "information/"+tname;
    }


    @GetMapping(path = "/user/{tname}")
    public String center(@PathVariable String tname,Model model,HttpServletRequest request){
        String username = AuthUtils.getAuthenticationObject().getName();
        User user = userService.SearchInformation(username);
        List<Role> roles = userService.SearchRoleByUsername(username);
        List<User> userfriend = userFriendService.GetFriendbyUsername(username);
        for (Role role:roles) {
            if (role.getRole().equals("ROLE_ADMIN")){
                model.addAttribute("role","admin");
                break;
            }else{
                model.addAttribute("role","user");
            }
        }
        if (request.getParameter("postid") != null){
            int id = Integer.parseInt(request.getParameter("postid"));
            Post p = postService.GetPostbyId(id);
            List<String> files = postService.GetFilebyID(id);
            Map<String,String> filesuffix = new IdentityHashMap<>();
            for (String t:files) {
                filesuffix.put(t.substring(t.lastIndexOf(".") + 1),t);
            }
            model.addAttribute("filesuffix",filesuffix);
            model.addAttribute("post",p);
        }
        List<Post> posts = postService.FindAllPost();
        int userid = userService.SearchUserId(username);
        List<Gallery> list = galleryService.GetGallerybyId(userid);
        model.addAttribute("gallery",list);
        model.addAttribute("posts",posts);
        model.addAttribute("username",username);
        model.addAttribute("email",user.getEmail());
        model.addAttribute("phone",user.getPhonenumber());
        model.addAttribute("friends",userfriend);
        return "user/"+ tname;
    }

    @RequestMapping(value = "/date/save" , method = RequestMethod.POST)
    public String SaveUser(HttpServletRequest request,Model model){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        if (userService.IfAliveUser(username)){
            model.addAttribute("name",username);
            model.addAttribute("password",password);
            model.addAttribute("email",email);
            model.addAttribute("AliveUsername",true);
            return "information/sign-up";
        }

        User user = new User(username,password,email);
        userService.SaveUser(user);
        return "information/index";
    }


    /*@RequestMapping(value = "/date/find" , method = RequestMethod.POST)
    public String FindUser(HttpServletRequest request,Model model){

        String username = request.getParameter("name");
        String password = request.getParameter("password");

        String ifpassword = userService.FindUser(username);

        if (ifpassword == null){
            model.addAttribute("name",username);
            model.addAttribute("password",password);
            model.addAttribute("usernamenull",true);
            return "information/index";
        }

        if (!ifpassword.equals(password)){
            model.addAttribute("name",username);
            model.addAttribute("passworderror",true);
            return "information/index";
        }else {
            return "user/Personalcenter";
        }
    }*/

}
