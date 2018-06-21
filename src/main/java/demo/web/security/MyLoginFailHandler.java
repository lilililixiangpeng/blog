package demo.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
   @Override  
   public void onAuthenticationFailure (HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
   {
	   logger.info("login failed --> {}, ip:  {}", request.getParameter ("name"), SecurityUtils.getIpAddress(request));
	   setDefaultFailureUrl("/information/index?error=true&username="+request.getParameter ("name"));
       //这里可以做fail的处理  
       //request.getSession(true).setAttribute ("SPRING_SECURITY_LAST_USERNAME", request.getParameter ("username"));
       super.onAuthenticationFailure (request, response, exception);
   }
}
