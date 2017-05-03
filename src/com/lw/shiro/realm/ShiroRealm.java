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
 *  认证需要继承AuthenticatingRealm(授权realm)
 *  授权需要继承
 * @author lw
 */
public class ShiroRealm extends AuthorizingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		
		System.out.println("ShiroRealm====");
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
			credentials = "098d2c478e9c11555ce2823231e02ec1"; // 密码
		}else if("admin".equals(username)){
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e"; // 密码
		}
		String realmName = getName(); // 当前对象的name，调用父类的getName()
		ByteSource salt = ByteSource.Util.bytes(username); // 加盐
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials,salt, realmName);
		return info;
	}
	/**
	 * user:	098d2c478e9c11555ce2823231e02ec1
	 * admin:	038bdaf98f2037b31f1e75b5b4c9b26e
	 * @param args
	 */
	public static void main(String[] args) {
		
		String algorithmName = "MD5"; // 使用什么技术加密
		Object source = "123456"; // 对什么加密
		Object salt = ByteSource.Util.bytes("user"); // 使用什么加盐，这里使用的是用户名
		int hashIterations = 1024;// 加盐的次数
		SimpleHash result = new SimpleHash(algorithmName, source, salt, hashIterations) ;
		System.out.println(result);
	}
	// 授权会被shiro调用的方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection info) {
		System.out.println("doGetAuthorizationInfo---------");
		// 1、从PrincipalCollection 中获取登录的信息
		Object principal = info.getPrimaryPrincipal();
		// 2、利用登录的用户信息
		Set<String> roles = new HashSet<>() ;
		roles.add("user");
		if ("admin".equals(principal)) {
			roles.add("admin");
		}
		// 3、创建SimpleAuthorizationInfo
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roles);
		// 4、返回authorizationInfo
		return authorizationInfo;
	}

}
