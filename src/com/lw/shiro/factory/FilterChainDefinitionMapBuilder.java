package com.lw.shiro.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {

//	 		/login.jsp = anon
//             /shiro/login = anon
//             /shiro/logout = logout 
//             # ����user.jsp��user��ɫ
//             /user.jsp = roles[user]
//             # ����admin.jsp��admin��ɫ
//             /admin.jsp = roles[admin]
//             
//             # everything else requires authentication:
//             /** = authc
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<>() ;
		map.put("/login.jsp", "anon");
		map.put("/shiro/login", "anon");
		map.put(" /shiro/logout", "logout");
		map.put("/user.jsp", "authc,roles[user]"); // ���뾭����֤�ĵĻ��о����Ȩ��
		map.put("/admin.jsp", "authc,roles[admin]");// ���뾭����֤�Ļ��о����Ȩ��
		map.put("/list.jsp", "user");// ֻҪ�Ƕ�Ӧ�Ľ�ɫ����
		map.put("/**", "anon");
		return map ;
	}
}
