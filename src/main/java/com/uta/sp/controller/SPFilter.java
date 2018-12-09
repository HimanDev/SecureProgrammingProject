package com.uta.sp.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.uta.sp.helper.Constants;

public class SPFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest=((HttpServletRequest)request);
		HttpServletResponse httpServletResponse=((HttpServletResponse)response);
		HttpSession session = httpServletRequest.getSession();
		String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
		if(Constants.ALLOWED_URLS.contains(path)) {
			chain.doFilter(request, response);
			return;
		}else if (session.getAttribute(Constants.SP_USERNAME)==null ||
				session.getAttribute(Constants.SP_USERNAME).toString().isEmpty()) {
			httpServletResponse.sendRedirect("/sp");
			return;
			
		}
		chain.doFilter(request, response);
	
	
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
