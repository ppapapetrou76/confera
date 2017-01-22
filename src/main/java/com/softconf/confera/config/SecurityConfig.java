package com.softconf.confera.config;

import com.softconf.confera.security.AuthTokenProcessingFilter;
import com.softconf.confera.security.ConferaUserDetailsService;
import com.softconf.confera.security.CustomBasicAuthenticationEntryPoint;
import com.softconf.confera.security.SimpleCORSFilter;
import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Inject
  private ConferaUserDetailsService userDetailsService;

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
            .antMatchers("/**/*.html", "/css/**", "/js/**", "/i18n/**", "/libs/**", "/img/**", "/ico/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    HttpSecurity httpSecurity = initializeSecurity(http);
    httpSecurity = addRouteSecurity(httpSecurity);
    finalizeSettings(httpSecurity);
    http.headers().cacheControl().disable();
  }

  private void finalizeSettings(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.securityContext()
            .securityContextRepository(new NullSecurityContextRepository())
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .httpBasic().authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint("custom"))
            .and()
            .csrf().disable();
    
  }

  private HttpSecurity initializeSecurity(HttpSecurity http) {
//        if (profileHelper.isLocal()) {
    http.addFilterBefore(new SimpleCORSFilter(), ChannelProcessingFilter.class);
//        }
    return http.addFilterBefore(
                    new AuthTokenProcessingFilter(userDetailsService),
                    DefaultLoginPageGeneratingFilter.class);
  }

  
  
  private HttpSecurity addRouteSecurity(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.
            authorizeRequests().
            antMatchers("/swagger-*/**", "/v2/api-docs", "/webjars/**").permitAll().
            antMatchers("/api/identity/authentication").permitAll().
            anyRequest().authenticated().
            and();
  }

  @Bean(name = "eventsIOAuthProvider")
  public AuthenticationProvider getAuthenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider
            = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    return daoAuthenticationProvider;
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
