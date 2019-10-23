package com.zr.myshiro;



import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义Realm
 */


public class MyRealm  extends AuthorizingRealm {


    @Override
    public void setName(String name) {
        super.setName("MyRealm");
    }



    /**
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
        String username = utoken.getUsername();

/*
      Object object =  token.getPrincipal();
      Object object1 =  token.getCredentials();
*/

        // mock select username from dataBase
        String userNameFromDataBase = "nice";
        String passwordFromDataBase = "1234";

        if(!username.equals(userNameFromDataBase)){
            throw new UnknownAccountException("用户不存在");
        }
        return new SimpleAuthenticationInfo(username, passwordFromDataBase, getName());
    }



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.err.println("进到了授权中心");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // mock 查库获取 用户角色和权限
//        simpleAuthorizationInfo.addStringPermission("/doOperation");
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        simpleAuthorizationInfo.addRoles(roles);

        Set<String> permissions = new HashSet<>();
        permissions.add("/ds");
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }







}
