package com.pf.api.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy{
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		log.warn("세션이 만료되었다");
		HttpServletResponse response = event.getResponse();
		HttpServletRequest request = event.getRequest();
		response.sendRedirect(request.getContextPath()+"/user/login?duplicateLogin");
	}
}
