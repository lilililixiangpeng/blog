package demo.web.security;

import demo.web.service.IdentifyingCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private MyUserDetailsService userService;

    @Autowired
    private IdentifyingCodeService identifyingCodeService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        if(identifyingCodeService.checkCode(username, password)) {
            UserDetails user = userService.loadUserByUsername(username);
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authorities);
        }

        throw new BadCredentialsException("Wrong password.");

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
