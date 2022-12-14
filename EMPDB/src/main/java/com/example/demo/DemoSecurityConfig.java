package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
    public void configure(WebSecurity web) {
        //org.springframework.security.web.firewall.RequestRejectedException:
        //The request was rejected because the URL contained a potentially 
        //malicious String ";"というエラーログがコンソールに出力されるため、下記を追加
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        web.httpFirewall(firewall);
    }
	
	/**
     * SpringSecurityによる認証を設定
     * @param http HttpSecurityオブジェクト
     * @throws Exception 例外
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
		//Basic認証の設定
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
        http
        .csrf().disable();
    }
}
