package com.pf.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.pf.api.security.CustomAccessDeniedHandler;
import com.pf.api.security.CustomAuthenticationEntryPoint;
import com.pf.api.security.CustomAuthenticationProvider;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true, jsr250Enabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
         return super.authenticationManagerBean();
    }
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
    	return new BCryptPasswordEncoder();
	}

	@Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(bCryptPasswordEncoder());
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
	@Autowired
	private SessionRegistry sessionRegistry;

    @Bean
    public ConcurrentSessionFilter concurrencyFilter() {
    	ConcurrentSessionFilter concurrentSessionFilter = new ConcurrentSessionFilter(sessionRegistry, new CustomSessionInformationExpiredStrategy());
        return concurrentSessionFilter;
    }
    
	@Configuration  
	@Order(2)       
	public static class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
	    private CustomAuthenticationEntryPoint authenticationEntryPoint;
		
		@Autowired
		private CustomAccessDeniedHandler customAccessDeniedHandler;
		
		@Autowired
		private ConcurrentSessionFilter concurrencyFilter;
		
		@Autowired
		private CustomAuthenticationProvider customAuthenticationProvider;
		
		@Override
		
		protected void configure(HttpSecurity http) throws Exception {
	    	/*
	    	 *       
	    	SessionCreationPolicy.ALWAYS        - 항상 세션을 생성
	      	SessionCreationPolicy.IF_REQUIRED 	- 필요시 생성(기본) 
	      	SessionCreationPolicy.NEVER       	- 생성하지않지만, 기존에 존재하면 사용
	      	SessionCreationPolicy.STATELESS     - 생성하지도않고 기존것을 사용하지도 않음 -> JWT 같은토큰방식
	    	 */
				//for CustomSessionInfomationExpiredStrategy
				http.addFilterAt(concurrencyFilter, ConcurrentSessionFilter.class);
				http
	        	.httpBasic().disable()
		        .cors()
		        .and()
		        .csrf()
		        	.disable()
	          	.exceptionHandling()
	                .authenticationEntryPoint(authenticationEntryPoint)
	                .accessDeniedHandler(customAccessDeniedHandler)
	            .and()
	                .authenticationProvider(customAuthenticationProvider)

		        .formLogin()
            		.disable()
            	.logout()
    				.disable()

		        .sessionManagement()
		        	.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		        	.sessionFixation()
	        		.migrateSession()
	        	.and()
	        	.antMatcher("/**")
			    .authorizeRequests()
		            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		            
		        	.antMatchers("/api/v1/auth/login").permitAll()
		        	
		        	.antMatchers("/**").permitAll()
		        	
						/*
						 * .antMatchers("/user/join1").permitAll()
						 * .antMatchers("/user/join2").permitAll()
						 * .antMatchers("/user/join2_exec").permitAll()
						 * .antMatchers("/user/join3").permitAll()
						 * .antMatchers("/user/join3_exec").permitAll()
						 * .antMatchers("/user/join4").permitAll()
						 * 
						 * .antMatchers("/user/recovery").permitAll()
						 * .antMatchers("/user/recovery_email/*").permitAll()
						 * .antMatchers("/user/completed_recovery").permitAll()
						 * 
						 * .antMatchers("/ajax/email/requestJoinAuth").permitAll()
						 * .antMatchers("/ajax/email/confirmJoinAuth").permitAll()
						 * .antMatchers("/ajax/email/requestAccountRecovery").permitAll()
						 * .antMatchers("/ajax/email/confirmAccountRecovery").permitAll()
						 * 
						 * .antMatchers("/user/join4").permitAll()
						 * 
						 * .antMatchers("/user/login").permitAll() .antMatchers("/login").permitAll()
						 * .antMatchers("/login_exec").permitAll()
						 * .antMatchers("/user/login_exec").permitAll()
						 * 
						 * .antMatchers("/api/**").authenticated()
						 * .antMatchers("/api/**").hasRole("USER")
						 * 
						 * .antMatchers("/user/**").authenticated()
						 * .antMatchers("/user/**").hasRole("USER")
						 * 
						 * .antMatchers("/common/**").permitAll()
						 */
		        	//.antMatchers("/**").authenticated()
		        	.anyRequest().authenticated();
		}
	}

	@Configuration
	@Order(1)                              
	public static class SecurityBackendConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Autowired
	    private CustomAuthenticationEntryPoint authenticationEntryPoint;
		
		@Autowired
		private CustomAccessDeniedHandler customAccessDeniedHandler;

		@Autowired
		private CustomAuthenticationProvider customAuthenticationProvider;
		
		protected void configure(HttpSecurity http) throws Exception {
			
			http
        	.httpBasic().disable()
	        .cors()
	        .and()
	        .csrf()
	        	.disable()
          	.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
            .and()
                .authenticationProvider(customAuthenticationProvider)

	        .formLogin()
        		.disable()
        	.logout()
				.disable()

	        .sessionManagement()
	        	.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	        	.sessionFixation()
        		.migrateSession()
        	.and()
                .antMatcher("/backoffice/**")    
               
                .authorizeRequests()
	            		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	            		.antMatchers("/backoffice/login").permitAll()
	            		.antMatchers("/backoffice/login_exec").permitAll()
	            		.antMatchers("/backoffice/**").hasRole("ADMIN")
	            		.anyRequest().authenticated();                
		}
	}

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
        	.requestMatchers(PathRequest.toStaticResources() // disable static Security 
        	.atCommonLocations())
        	//for swagger2
        	.antMatchers("v2/api-docs","/swagger-resources/**","/swagger-ui.html","/webjars/**","/swagger/**");
    }
}

