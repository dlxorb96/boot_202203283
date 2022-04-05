package com.example.config;

import com.example.handler.MylogoutSuccessHandler;
import com.example.handler.SuccessHandlerLogin;
import com.example.service.MemberDetailServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 1. 직접 만든 detailService 객체 가져오기
    @Autowired
    MemberDetailServiceImpl mService;

    @Autowired

    // 2. 암호화 방법 객체 생성 @Bean은 서버 구동시 자동으로 객체 생성됨
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. 직접만든 dtailsService에 암호화 방법 적용
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    // 기존기능을 제거한 후 필요한 것을 추가
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 페이지별 접근권한 설정
        http.authorizeRequests()
                .antMatchers("/security_admin", "/security_admin/**")
                .hasAnyAuthority("ADMIN")
                .antMatchers("/security_seller", "/security_seller/**")
                .hasAnyAuthority("ADMIN", "SELLER")
                .antMatchers("/security_customer", "/security_customer/**")
                .hasAnyAuthority("CUSTOMER")
                .anyRequest().permitAll();

        // 로그인 페이지 설정, 단 post는 직접 만들지 않음.
        http.formLogin()
                .loginPage("/member/security_login")
                .loginProcessingUrl("/member/security_loginaction")
                .usernameParameter("uemail")
                .passwordParameter("upw")
                .successHandler(new SuccessHandlerLogin())
                // .defaultSuccessUrl("/security_home", true)
                .permitAll();

        // 로그아웃 페이지 설정, url에 맞게 post로 호출하면 딤
        http.logout()
                .logoutUrl("/member/security_logout")
                .logoutSuccessHandler(new MylogoutSuccessHandler())
                // .logoutSuccessUrl("/security_home")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll();

        // 접근권한불가 403
        http.exceptionHandling().accessDeniedPage("/security_403");

        // super.configure(http);
        // super가 부모의 기능이다.
        // 이걸 뺴버리면 부모기능을 안쓰겠다.

        // h2 console db사용을 위해서 임시로
        // 나중에 빼야함. 보안을 위해
        // http.csrf().disable(); // 보안에 취약

        http.csrf().ignoringAntMatchers("/h2-console/**");
        http.headers().frameOptions().sameOrigin();

    }
    // extends가 안되는 상황에서 securityconfig에 컨트롤 .을 써서 불렀음
}
