package demo.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override    
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获得用户信息
    	SecureUser userDetails = (SecureUser)authentication.getPrincipal();
    	userDetails.setIp(SecurityUtils.getIpAddress(request));
    	userDetails.setAgent(request.getHeader("User-Agent"));
    	userDetails.setReferer(request.getHeader("Referer"));
    	userDetails.setBrowser(SecurityUtils.getOsAndBrowserInfo(request));
        //输出登录提示信息    
 	   logger.info("login success --> {}, ip: {}, agent: {}, referer: {}, os browser: {}", userDetails.getUsername(), SecurityUtils.getIpAddress(request), request.getHeader("User-Agent"), request.getHeader("Referer"), SecurityUtils.getOsAndBrowserInfo(request));
 	   //登陆成功后的处理，跳转路径等，可以自己写组件，比如返回信息，一会自己看着写
        super.onAuthenticationSuccess(request, response, authentication);
    }
}