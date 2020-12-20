package cn.myh.twesqu.config;

import cn.myh.twesqu.common.util.JwtUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 此处只是为了使用security提供的密码加密功能，关闭了接口权限控制
 */
@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }

    @Bean()
    public BCryptPasswordEncoder bCryptPasswordEncoderProvider() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConfigurationProperties("jwt.config")
    public JwtUtil jwtUtilProvider() {
        return new JwtUtil();
    }
}
