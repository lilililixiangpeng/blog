package demo.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecureUser extends User {
	private String ip;
	private String agent;
	private String referer;
	private String browser;
	
	public SecureUser(String username ,Collection<GrantedAuthority> authorities) {
        super(username, "", authorities);
    }

	@Override
	public String getUsername() {
		return super.getUsername();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}
}
