package demo.web.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Configuration
@ComponentScan("demo.web")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "demo.web")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    @Lazy
    MyAuthenticationProvider authenticationProvider;

    @Autowired
    @Lazy
    MyUserDetailsService userDetailsService;

    @Autowired
    @Lazy
    MyRememberMeAuthenticationProvider rememberMeAuthenticationProvider;


    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(rememberMeAuthenticationProvider);
        auth.authenticationProvider(authenticationProvider);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                    .frameOptions().sameOrigin().disable()
                .authorizeRequests()
                    .antMatchers("/user/**","/admin/**").fullyAuthenticated()//只有/user需要验证
                    .anyRequest().permitAll()
        .and()
                .formLogin()
                    .successHandler(loginSuccessHandler())  //登录成功后可使用loginSuccessHandler()存储用户信息
                    .failureHandler(loginFailHandler())     //登录失败处理
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login")//拦截登陆的路径/login
                    .loginPage("/information/index")
                    //.failureUrl("/information/index?error=true")
                    //.defaultSuccessUrl("/user/Personalcenter")
                    .permitAll()
                .and()
                    .rememberMe()
//                        .tokenRepository(tokenRepository())
                        .rememberMeServices(rememberMeServices()).key("remember-me")
//                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds(1209600)
                .and()
                    .logout()
                        .logoutSuccessHandler(logoutSuccessHandler())
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/index")
                        .invalidateHttpSession(true)
                .and()
//                    .csrf()
                    .csrf().disable() //disable csrf
//                .and()
                    .sessionManagement().maximumSessions(1);
    }



    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new MyLogoutSuccessHandler();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public MyLoginFailHandler loginFailHandler() {
        return new MyLoginFailHandler();
    }

    //spring security 内部都写死了，这里要把 这个DAO 注入
    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl jtr = new JdbcTokenRepositoryImpl();
        jtr.setDataSource(dataSource);
        return jtr;
    }

    @Bean
    public MyRememberMeAuthenticationProvider rememberMeAuthenticationProvider(){
        MyRememberMeAuthenticationProvider rememberMeAuthenticationProvider = new MyRememberMeAuthenticationProvider("remember-me");
        return rememberMeAuthenticationProvider;
    }


    /*@Bean
    public RememberMeServices rememberMeServices() {
        JdbcTokenRepositoryImpl rememberMeTokenRepository = new JdbcTokenRepositoryImpl();
        // 此处需要设置数据源，否则无法从数据库查询验证信息
        rememberMeTokenRepository.setDataSource(dataSource);

        // 此处的 key 可以为任意非空值(null 或 "")，单必须和起前面
        // rememberMeServices(RememberMeServices rememberMeServices).key(key)的值相同
        PersistentTokenBasedRememberMeServices rememberMeServices =
                new PersistentTokenBasedRememberMeServices("remember-me", userDetailsService, rememberMeTokenRepository);

        // 该参数不是必须的，默认值为 "remember-me", 但如果设置必须和页面复选框的 name 一致
        rememberMeServices.setParameter("remember-me");
        return rememberMeServices;
    }*/


    @Bean
    public RememberMeServices rememberMeServices() {
        JdbcTokenRepositoryImpl rememberMeTokenRepository = new JdbcTokenRepositoryImpl();
        // 此处需要设置数据源，否则无法从数据库查询验证信息
        rememberMeTokenRepository.setDataSource(dataSource);
        // Key must be equal to rememberMe().key()
        MyPersistentTokenBasedRememberMeServices rememberMeServices =
                new MyPersistentTokenBasedRememberMeServices("remember-me", userDetailsService, rememberMeTokenRepository);
        rememberMeServices.setParameter("remember-me");
        rememberMeServices.setTokenValiditySeconds(864000);
        return rememberMeServices;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
