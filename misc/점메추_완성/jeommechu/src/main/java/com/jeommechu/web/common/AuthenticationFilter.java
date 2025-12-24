package com.jeommechu.web.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/getLunches", "/resetLunches", "/addLunch"})
public class AuthenticationFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
    
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		if(session.getAttribute("memberName") == null) {
			session.setAttribute("loginMessage", "회원만 이용가능합니다.");
			res.sendRedirect("login.html");
		} else {
			chain.doFilter(request, response);
		}
	}
}
