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
 *  认证
 * @author lw
 */
public class ManyRealm extends AuthenticatingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		
		System.out.println("ManyRealm====");
		//1、将authenticationToken转换成UsernamePasswordToken
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken ;
		//2、从UsernamePasswordToken获取username
		String username = token.getUsername();
		
		// 3、调用数据库中的方法，从数据库中查询username
		// 4、若用户不存在
		if("unknow".equals(username)){
			throw new UnknownAccountException("用户不存在");
		}
		if("master".equals(username)){
			throw new LockedAccountException("用户被锁定");
		}
		
		/*************************相当于数据库里的对象*******************************/
		// 5、根据用户构建AuthenticationInfo对象，并返回
		Object principal = username ; // 认证的实体信息
		Object credentials = null ; // 密码
		if("user".equals(username)){
			credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718"; // 密码
		}else if("admin".equals(username)){
			credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06"; // 密码
		}
		String realmName = getName(); // 当前对象的name，调用父类的getName()
		ByteSource salt = ByteSource.Util.bytes(username); // 加盐
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials,salt, realmName);
		return info;
	}
	/**
	 * user:	073d4c3ae812935f23cb3f2a71943f49e082a718
	 * admin:	ce2f6417c7e1d32c1d81a797ee0b499f87c5de06
	 * @param args
	 */
	public static void main(String[] args) {
		
		String algorithmName = "SHA1"; // 使用什么技术加密
		Object source = "123456"; // 对什么加密
		Object salt = ByteSource.Util.bytes("user"); // 使用什么加盐，这里使用的是用户名
		int hashIterations = 1024;// 加盐的次数
		SimpleHash result = new SimpleHash(algorithmName, source, salt, hashIterations) ;
		System.out.println(result);
	}

}
