package be.lacerta.cq2.sim;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class RealIPFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {			
			chain.doFilter(new RealIPRequestWrapper((HttpServletRequest)request), response);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	class RealIPRequestWrapper extends HttpServletRequestWrapper {
		String remoteHost = null;
		
		public RealIPRequestWrapper(HttpServletRequest request) {
			super(request);
		}
		
		@Override
		public String getRemoteAddr() {
			String realIP = super.getHeader("X-Real-IP");
			return realIP!=null?realIP:super.getRemoteAddr();
		}
		
		@Override
		public String getRemoteHost() {
			try {
				return InetAddress.getByName(getRemoteAddr()).getHostName();
			} catch (UnknownHostException e) {
				return getRemoteAddr();
			}
		}
	}
}

