package com.pf.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pf.api.dto.AccountForm;
import com.pf.api.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class AccountController {
	
	@Autowired 
    AuthenticationManager authenticationManager;
	
	@Autowired
	AccountService accountService;
	
	/*
	 * @ResponseBody
	 * 
	 * @GetMapping("/login") public Map<String,Object> login(Map<String, Object>
	 * list) { Map<String,Object> resultmap = new HashMap<String,Object>();
	 * 
	 * resultmap.put("code","1000"); resultmap.put("result", "ERR");
	 * resultmap.put("message", "로그인 되지 않음");
	 * 
	 * return resultmap; }
	 */
	  
	//test
	@ResponseBody
	@RequestMapping(value="/login_exec", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String,Object> loginExec(@RequestBody(required = false) @Valid AccountForm.Login form, BindingResult bindingResult, HttpServletResponse response) throws Exception 
    {	
		Map<String,Object> resultmap = new HashMap<String,Object>();
		if(form != null)
		log.info(form.toString());
		
		resultmap.put("code","");
		resultmap.put("result", "");
		resultmap.put("message", "");
		
    	//Cookie cookie = new Cookie("save_login_id_key","");
    	//cookie.setPath("/");
		
    	if (bindingResult.hasErrors() || form == null) {
    		resultmap.replace("code","1000");
    		resultmap.replace("result", "ERR");
    		if(form == null) 
    			resultmap.replace("message", "form is null");
    		else
    			resultmap.replace("message", bindingResult.getAllErrors().toString());
    		
			//cookie.setMaxAge(0);
			//response.addCookie(cookie);
    		log.info(bindingResult.getAllErrors().toString());
    		return resultmap;
    	}
    	
    	
    	//cookie = new Cookie("save_login_id_key", (String)list.get("name"));    	

    	UsernamePasswordAuthenticationToken loginToken = null;
    	 try {
    		 loginToken = new UsernamePasswordAuthenticationToken(form.getName(), form.getPassword());
    		 //loginToken = new UsernamePasswordAuthenticationToken((String)list.get("name"), (String)list.get("password"));
    		 Authentication auth = authenticationManager.authenticate(loginToken);
    		 SecurityContextHolder.getContext().setAuthentication(auth);
    		 //session.setMaxInactiveInterval(60*60*3);//3 hour
    		 resultmap.replace("code","1000");
	     	 resultmap.replace("result", "ERR");
	     	 
    	 } catch (DisabledException | LockedException | BadCredentialsException e) {
             String status;
             if (e.getClass().equals(BadCredentialsException.class)) {
                 status = "invalid password";
             } else if (e.getClass().equals(DisabledException.class)) {
                 status = "user locked";
             } else if (e.getClass().equals(LockedException.class)) {
                 status = "user disable";
             } else {
                 status = "unknown exception";
             }
             log.info("login {}", status);
             //log.info("로그인실패:"+(String)list.get("name"));
             log.info("로그인실패:"+form.getName());
             //cookie.setMaxAge(0);     
             //response.addCookie(cookie);
             
             resultmap.replace("code", 999);
             resultmap.replace("result", "ERR");
             resultmap.replace("message", "state");
             
             return resultmap;
         }
    	 log.info("로그인성공");
			/*
			 * if(form.getIsRemember()) { cookie.setMaxAge(60*60*24*30);
			 * form.setIsRemember(true); }else { cookie.setMaxAge(0); }
			 */
    	 //response.addCookie(cookie);
    	 
    	 resultmap.replace("code", 200);
         resultmap.replace("result", "OK");
         resultmap.replace("message", "로그인 성공");
    	 
    	 return resultmap;
    }
}
