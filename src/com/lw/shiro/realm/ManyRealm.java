package com.lw.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.ByteSource;

/**
 *  ��֤
 * @author lw
 */
public class ManyRealm extends AuthenticatingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		
		System.out.println("ManyRealm====");
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
			credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718"; // ����
		}else if("admin".equals(username)){
			credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06"; // ����
		}
		String realmName = getName(); // ��ǰ�����name�����ø����getName()
		ByteSource salt = ByteSource.Util.bytes(username); // ����
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials,salt, realmName);
		return info;
	}
	/**
	 * user:	073d4c3ae812935f23cb3f2a71943f49e082a718
	 * admin:	ce2f6417c7e1d32c1d81a797ee0b499f87c5de06
	 * @param args
	 */
	public static void main(String[] args) {
		
		String algorithmName = "SHA1"; // ʹ��ʲô��������
		Object source = "123456"; // ��ʲô����
		Object salt = ByteSource.Util.bytes("user"); // ʹ��ʲô���Σ�����ʹ�õ����û���
		int hashIterations = 1024;// ���εĴ���
		SimpleHash result = new SimpleHash(algorithmName, source, salt, hashIterations) ;
		System.out.println(result);
	}

}
