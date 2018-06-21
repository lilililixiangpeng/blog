package demo.web.controller;

import demo.web.filestorage.DefauleUser;
import demo.web.model.User;
import demo.web.service.SearchTitle;
import demo.web.service.UserService;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MainController {

    @Autowired
    SearchTitle searchTitle;


    @GetMapping(path = "/")
    public String index(Model model){
        return "index";
    }


    @GetMapping(path = "/{name}")
    public String page(@PathVariable String name, Model model){
        model.addAttribute("title",searchTitle.searchtitle(name));
        return name;
    }


}
