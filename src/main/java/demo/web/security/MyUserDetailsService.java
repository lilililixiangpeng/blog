package demo.web.security;

import demo.web.model.Role;
import demo.web.model.User;
import demo.web.service.IdentifyingCodeService;
import demo.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("MyUserDetailsServiceImpl")
public class MyUserDetailsService implements UserDetailsService {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    @Lazy
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //要从后端获取用户的权限信息，和密码信息，然后要修改SecureUser类
        User user = userService.SearchInformation(username);
        String password = userService.FindUser(username);
        List<Role> roles = user.getRole();
        LOGGER.info("loadUserByUsername --> [{}]", username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role1: roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role1.getRole()));
        }
        return  new SecureUser(username,grantedAuthorities);

    }

}
