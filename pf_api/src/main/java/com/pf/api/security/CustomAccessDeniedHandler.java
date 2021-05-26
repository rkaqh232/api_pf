package com.pf.api.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if(request.getHeader("X-Requested-With")!=null && request.getHeader("X-Requested-With").contentEquals("XMLHttpRequest"))
		{
	        response.setContentType("application/json;charset=utf-8");
	        response.setStatus(HttpStatus.FORBIDDEN.value());
	        JsonObject json = new JsonObject();
	        json.addProperty("statusCode", "403");
	        json.addProperty("message", "FORBIDDEN");
	        PrintWriter out = response.getWriter();
	        out.print(json);
		}else {
			if(request.getRequestURI().contains(request.getContextPath()+"/backoffice"))
			{
				response.sendRedirect(request.getContextPath()+"/backoffice/login");
			}else {
				response.sendRedirect(request.getContextPath()+"/user/login");
			}
		}
	}
}
