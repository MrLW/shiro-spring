package com.lw.shiro.handler;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lw.shiro.service.ShiroService;

@Controller
@RequestMapping("/shiro")
public class ShiroHandler {
	
	@Autowired
	private ShiroService shiroService;

	@RequestMapping("/testShiroAnnotation")
	public String testShiroAnnotation(HttpSession session){
		session.setAttribute("name", "value");
		shiroService.testMethod(); 
		return "redirect:/list.jsp"; 
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam("username")String username,
			@RequestParam("password")String password){
		//1����ȡsubject����
		Subject currentUser = SecurityUtils.getSubject();
		if(!currentUser.isAuthenticated()){ // ������userû�б���֤
			// 2������token
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			//--���ü�ס
			token.setRememberMe(true);
			try {
				System.out.println("��ǰ�û���" + token.getUsername());
				currentUser.login(token);
			} catch (Exception e) {
				System.out.println("��¼ʧ�ܣ�" + e.getMessage());
			}
		}
		return "redirect:/list.jsp" ;
	}
}
