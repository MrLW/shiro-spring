package com.lw.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 *  ��֤��Ҫ�̳�AuthenticatingRealm(��Ȩrealm)
 *  ��Ȩ��Ҫ�̳�
 * @author lw
 */
public class ShiroRealm extends AuthorizingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		
		System.out.println("ShiroRealm====");
		//1����authenticationTokenת����UsernamePasswordToken
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken ;
		//2����UsernamePasswordToken��ȡusername
		String username = token.getUsername();
		
		// 3���������ݿ��еķ����������ݿ��в�ѯusername
		// 4�����û�������
		if("unknow".equals(username)){
			throw new UnknownAccountException("�û�������");
		}
		if("master".equals(username)){
			throw new LockedAccountException("�û�������");
		}
		
		/*************************�൱�����ݿ���Ķ���*******************************/
		// 5�������û�����AuthenticationInfo���󣬲�����
		Object principal = username ; // ��֤��ʵ����Ϣ
		Object credentials = null ; // ����
		if("user".equals(username)){
			credentials = "098d2c478e9c11555ce2823231e02ec1"; // ����
		}else if("admin".equals(username)){
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e"; // ����
		}
		String realmName = getName(); // ��ǰ�����name�����ø����getName()
		ByteSource salt = ByteSource.Util.bytes(username); // ����
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials,salt, realmName);
		return info;
	}
	/**
	 * user:	098d2c478e9c11555ce2823231e02ec1
	 * admin:	038bdaf98f2037b31f1e75b5b4c9b26e
	 * @param args
	 */
	public static void main(String[] args) {
		
		String algorithmName = "MD5"; // ʹ��ʲô��������
		Object source = "123456"; // ��ʲô����
		Object salt = ByteSource.Util.bytes("user"); // ʹ��ʲô���Σ�����ʹ�õ����û���
		int hashIterations = 1024;// ���εĴ���
		SimpleHash result = new SimpleHash(algorithmName, source, salt, hashIterations) ;
		System.out.println(result);
	}
	// ��Ȩ�ᱻshiro���õķ���
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection info) {
		System.out.println("doGetAuthorizationInfo---------");
		// 1����PrincipalCollection �л�ȡ��¼����Ϣ
		Object principal = info.getPrimaryPrincipal();
		// 2�����õ�¼���û���Ϣ
		Set<String> roles = new HashSet<>() ;
		roles.add("user");
		if ("admin".equals(principal)) {
			roles.add("admin");
		}
		// 3������SimpleAuthorizationInfo
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roles);
		// 4������authorizationInfo
		return authorizationInfo;
	}

}
