package com.pf.api.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import com.pf.api.entity.Account;
import com.pf.api.service.AccountService;

public class BaseInterceptor implements HandlerInterceptor {
    @Autowired 
    AuthenticationManager authenticationManager;
    
    @Autowired 
    AccountService accountService;
    
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(request.getRequestURI().toString().equals(request.getContextPath()+"/backoffice") || request.getRequestURI().toString().equals(request.getContextPath()+"/backoffice/"))
		{
			response.sendRedirect(request.getContextPath()+"/backoffice/login");
			return false;
		}
		
		if(request.getRequestURI().toString().equals(request.getContextPath()+"/") || request.getRequestURI().toString().equals(request.getContextPath()+"/user") || request.getRequestURI().toString().equals(request.getContextPath()+"/user/"))
		{
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		if(request.getRequestURI().contains(request.getContextPath()+"/backoffice/login"))
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Cookie rememberMe = WebUtils.getCookie(request, "remember_me");
			if((auth==null || auth.getPrincipal().toString().equals("anonymousUser")) && rememberMe !=null)
			{
                if(false) { //TODO 시간확인
                	Account accountUser = accountService.findByUsername("test@code.com");
	        		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(accountUser, null, accountUser.getAuthorities()));
		            response.sendRedirect(request.getContextPath()+"/backoffice/main/dashboard");
		            return false;
                }
			}else if(auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
	            response.sendRedirect(request.getContextPath()+"/backoffice/main/dashboard");
	            return false;
			}
		}
		response.setHeader("Cache-Control","no-cache"); 
		response.setHeader("Pragma","no-cache");

		//회원권한수정 바로 적용
		//TODO 활성화 비활성화에 따라 자동 로그아웃필요?
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(null == auth || "anonymousUser".equals( auth.getPrincipal().toString() )) {
			return true;
		}
		Object principal = auth.getPrincipal();
		if( principal instanceof Account ) {
			Account currentUser = (Account) principal;
			currentUser = accountService.findByUsername(currentUser.getUsername());
			if(currentUser != null) {
				List<GrantedAuthority> updateAuthorities = currentUser.getAuthorities().stream().map(userRole -> new SimpleGrantedAuthority(userRole.toString())).collect(Collectors.toList());
		        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updateAuthorities);
		        SecurityContextHolder.getContext().setAuthentication(newAuth);
			}else {
				SecurityContextHolder.clearContext();
				response.sendRedirect(request.getContextPath()+"/");
				return false;
			}
		}
		return true;
	}
}
