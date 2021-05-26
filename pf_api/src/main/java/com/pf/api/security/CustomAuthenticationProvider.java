package com.pf.api.security;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.pf.api.entity.Account;
import com.pf.api.service.AccountService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@NonNull
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
    private AccountService accountService;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
    @Override
    public boolean supports(Class<?> authentication) {
    	//인증처리 가능한 Provider 인지 반환
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    	//return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
			log.info("token : "+token.toString());
			Account accountUser = accountService.findByUsername(token.getPrincipal().toString());

			boolean check = false;
			if(accountUser != null) {
				//log.info("패스워드 비교중1 : "+passwordEncoder.matches((String)token.getCredentials(), accountUser.getPassword()));
				//log.info("패스워드 비교중2 : "+(String)token.getCredentials());
				//log.info("패스워드 비교중3 : "+accountUser.getPassword());
			}
      		if(accountUser==null || !((String)token.getCredentials()).equals(accountUser.getPassword())) {
        		throw new BadCredentialsException(accountUser.getUsername() + "Invalid password !!");
        	}

      		if(request.getRequestURI().contains(request.getContextPath()+"/user"))
			{
	      		final List<SessionInformation> sessionInfo = sessionRegistry.getAllSessions(token.getPrincipal().toString(), false);
	      		final Iterator<SessionInformation> session = sessionInfo.iterator();
	      		
				while (session.hasNext()) {
					SessionInformation sessionInformation = session.next();
					String username = sessionInformation.getPrincipal().toString();
					
					if (username.equals(token.getPrincipal().toString()) 
							&& !request.getSession().getId().toString().equals(sessionInformation.getSessionId().toString())) {
						sessionInformation.expireNow();
						sessionRegistry.removeSessionInformation(token.getPrincipal().toString());
						log.warn("-------------중복 로그인--------------------------");
						log.warn("SESSION ID:{}"+request.getSession().getId());
						log.warn(sessionInformation.getPrincipal().toString());
						log.warn("-----------------------------------------------");
					}
					break;
				}
				
			}
      		sessionRegistry.registerNewSession(request.getSession().getId(), token.getPrincipal().toString());
      		UsernamePasswordAuthenticationToken resultToken  = new UsernamePasswordAuthenticationToken(accountUser, null, accountUser.getAuthorities());
      		resultToken.setDetails(accountUser);//template thymeleaf => #authentication.details.username
			return resultToken;
        }catch (Exception e){
        	throw new BadCredentialsException("customUserDetailsService Invalid password");
        }
	}
}
