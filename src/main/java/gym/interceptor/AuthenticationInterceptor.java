package gym.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import gym.annotation.EmployeeAuth;
import gym.annotation.ManagerAuth;
import gym.annotation.RequireAuth;
import gym.dao.EmployeeRepository;
import gym.dao.ManagerRepository;
import gym.service.JwtService;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor
{
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private EmployeeRepository employees;
	
	@Autowired
	private ManagerRepository managers;
	
	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) throws Exception
	{
		// If handler is not a method then skip
		if(!(handler instanceof HandlerMethod)) return true;
		
		// Check if skipAuth annotation is present
		Method method = ((HandlerMethod)handler).getMethod();
		if(!method.isAnnotationPresent(RequireAuth.class)) return true;
		
		// Do authentication before actually engage the handler
		Integer uid = this.jwtService.getUserId(request);
		int authType =(
			method.isAnnotationPresent(EmployeeAuth.class)
			? 1
			: method.isAnnotationPresent(ManagerAuth.class) ? 2 : 0
		);
		
		if(authType == 0)
			return true;
		
		boolean isEmployee = this.employees.findByUserId(uid) != null;
		boolean isManager = this.managers.findByUserId(uid) != null;
		
		return isManager || (authType == 1 && isEmployee); 
	}
}
