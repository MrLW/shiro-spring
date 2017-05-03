package com.lw.shiro.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {

//	 		/login.jsp = anon
//             /shiro/login = anon
//             /shiro/logout = logout 
//             # 访问user.jsp是user角色
//             /user.jsp = roles[user]
//             # 访问admin.jsp是admin角色
//             /admin.jsp = roles[admin]
//             
//             # everything else requires authentication:
//             /** = authc
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<>() ;
		map.put("/login.jsp", "anon");
		map.put("/shiro/login", "anon");
		map.put(" /shiro/logout", "logout");
		map.put("/user.jsp", "authc,roles[user]"); // 必须经过认证的的还有具体的权限
		map.put("/admin.jsp", "authc,roles[admin]");// 必须经过认证的还有具体的权限
		map.put("/list.jsp", "user");// 只要是对应的角色即可
		map.put("/**", "anon");
		return map ;
	}
}
