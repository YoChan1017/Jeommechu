package com.jeommechu.web.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/jeommechu/*")
public class CharacterEncodingFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ServletContext context = request.getServletContext();
		String encoding = context.getInitParameter("jeommechuEncoding");
		request.setCharacterEncoding(encoding);
		
		chain.doFilter(request, response);
	}
}
