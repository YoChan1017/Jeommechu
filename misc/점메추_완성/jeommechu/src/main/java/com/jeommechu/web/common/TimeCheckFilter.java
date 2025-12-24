package com.jeommechu.web.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/jeommechu/*")
public class TimeCheckFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

    public TimeCheckFilter() {
        System.out.println("> TimeCheckFilter 생성 <");
    }
    
    public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("> init() 호출");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/jeommechu/"));
		
		long startTime = System.currentTimeMillis();
		
		chain.doFilter(request, response);

		long endTime = System.currentTimeMillis();
		System.out.println(path + " 요청 처리에 소요된 시간 : " + (endTime - startTime) + "(ms)초");
	}
	public void destroy() {
		System.out.println("> destroy() 호출");
	}
	
}
