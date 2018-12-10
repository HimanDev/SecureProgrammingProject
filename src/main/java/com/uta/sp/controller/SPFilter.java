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

import org.apache.log4j.Logger;

import com.uta.sp.helper.Constants;

public class SPFilter implements Filter{
	
	private static final Logger LOG=Logger.getLogger(SPFilter.class);


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest=((HttpServletRequest)request);
		HttpServletResponse httpServletResponse=((HttpServletResponse)response);
		HttpSession session = httpServletRequest.getSession();
		String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
		if(Constants.ALLOWED_URLS.contains(path)) {
			LOG.info("valid request from ip-address => "+httpServletRequest.getRemoteAddr());
			chain.doFilter(request, response);
			return;
		}else if (session.getAttribute(Constants.SP_USERNAME)==null ||
				session.getAttribute(Constants.SP_USERNAME).toString().isEmpty()) {
			LOG.error("invalid request from ip-address => "+httpServletRequest.getRemoteAddr());
			httpServletResponse.sendRedirect("/sp");
			return;
			
		}
		LOG.info("valid request from ip-address => "+httpServletRequest.getRemoteAddr());
		chain.doFilter(request, response);
	
	
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
