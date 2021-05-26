package com.pf.api.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.google.gson.JsonObject;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		if(request.getHeader("X-Requested-With")!=null && request.getHeader("X-Requested-With").contentEquals("XMLHttpRequest"))
		{
			response.setContentType("application/json;charset=utf-8");
	        response.setStatus(HttpStatus.UNAUTHORIZED.value());
	        JsonObject json = new JsonObject();
	        String message = "UNAUTHORIZED";
	        json.addProperty("statusCode", "401");
	        json.addProperty("message", message);
	        PrintWriter out = response.getWriter();
	        out.print(json);
		}else{
			response.sendRedirect(request.getContextPath()+"/login");
		}
	}
}
