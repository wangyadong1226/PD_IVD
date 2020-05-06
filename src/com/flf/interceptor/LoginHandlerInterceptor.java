package com.flf.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.flf.entity.User;
import com.flf.util.Const;

public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			if(user!=null){
				return true;
			}else{
				response.sendRedirect(request.getContextPath()+"/login.html");
				return false;
			}
		}
	}
	
}
