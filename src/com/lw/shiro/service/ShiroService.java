package com.lw.shiro.service;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;

public class ShiroService {
	// 必须是admin的属性
	@RequiresRoles(value={"admin"})
	public void testMethod(){
		Session session = SecurityUtils.getSubject().getSession();
		System.out.println("session:" + session.getAttribute("name"));
		
		System.out.println("test method:" + new Date().getTime());
	}
}
