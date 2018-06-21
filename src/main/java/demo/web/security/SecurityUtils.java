package demo.web.security;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;


public class SecurityUtils {
	
	/**
	 * 获取登录用户的真实IP
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getHeader("WL-Proxy-Client-IP");
        }      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getHeader("HTTP_CLIENT_IP");
        }      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	
	/**
	 * 获取登录用户
	 * @return
	 */
	public static SecureUser getUser() {
		return (SecureUser) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
	}
	
	/**
	 * 获取操作系统,浏览器及浏览器版本信息
	 * @param request
	 * @return
	 */
	public static String getOsAndBrowserInfo(HttpServletRequest request) {
	        String  userAgent  =   request.getHeader("User-Agent");
	        String  agent            =   userAgent.toLowerCase();
	  
	        String os = null;
	        String browser = null;
	  
	        //=================OS Info=======================  
	        if (agent.contains("windows")) {  
	            os = "Windows";  
	        } else if(agent.contains("mac")) {  
	            os = "Mac";  
	        } else if(agent.contains("x11")) {  
	            os = "Unix";  
	        } else if(agent.contains("android")) {  
	            os = "Android";  
	        } else if(agent.contains("iphone")) {  
	            os = "iPhone";  
	        }
	        
	        //===============Browser===========================  
	        if (agent.contains("edge")) {  
	            browser=(userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");  
	        } else if (agent.contains("msie")) {  
	            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];  
	            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];  
	        } else if (agent.contains("safari") && agent.contains("version")) {  
	            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]  
	                    + "-" +(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
	        } else if ( agent.contains("opr") || agent.contains("opera")) {  
	            if(agent.contains("opera")){  
	                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]  
	                        +"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];  
	            }else if(agent.contains("opr")){  
	                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))  
	                        .replace("OPR", "Opera");  
	            }  
	        } else if (agent.contains("chrome"))  {  
	            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");  
	        } else if (agent.contains("firefox")) {  
	            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");  
	        } else if ((agent.indexOf("mozilla/7.0") > -1) || (agent.indexOf("netscape6") != -1)  ||  
	                (agent.indexOf("mozilla/4.7") != -1) || (agent.indexOf("mozilla/4.78") != -1) ||  
	                (agent.indexOf("mozilla/4.08") != -1) || (agent.indexOf("mozilla/3") != -1) ) {  
	            
	        	browser = "Netscape-?";  
	        } else if(agent.contains("rv")) {  
	            String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");  
	            browser="IE" + IEVersion.substring(0,IEVersion.length() - 1);  
	        } 

	        if(os == null || browser==null)
	        	return "UnKnown, "+userAgent;  

	        return os +", "+ browser ;  
	}

}
