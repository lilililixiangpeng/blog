package demo.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		//获得用户信息
    	SecureUser userDetails = (SecureUser)authentication.getPrincipal();
    	//可以用作用户登出不在线逻辑使用


        //输出提示信息    
 	   logger.info("logout success --> {}, ip: {}, agent: {}, referer: {}", userDetails.getUsername(), SecurityUtils.getIpAddress(request), request.getHeader("User-Agent"), request.getHeader("Referer"));
 	   
		super.onLogoutSuccess(request, response, authentication);
	}

}
