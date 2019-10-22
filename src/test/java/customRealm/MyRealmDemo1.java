package customRealm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyRealmDemo1 extends AuthorizingRealm {

    /**
     *  模拟数据库连接
     */
    Map<String, String> userMap = new HashMap<>(6);
    {

        userMap.put("a", "123");
       /* userMap.put("b", "123");
        userMap.put("c", "123");
        userMap.put("d", "123");
        userMap.put("e", "123");
        userMap.put("f", "123");*/

        // 指定myRealm name  此外，也可以重写父类中的setName 指定 myRealmName
        super.setName("myCustomDemo1");
    }


    /**
     *  授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String)principals.getPrimaryPrincipal();
        // 从数据库获取角色和权限信息
        Set<String> roles = getRolesByUserName(userName);
        Set<String> permissions = getPermissionsByUserName(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);

        return simpleAuthorizationInfo;
    }

    /**
     * 模拟从数据库中获取角色数据
     *
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }


    /**
     *  模拟从数据库获取权限数据
     * @param name
     * @return
     */
    private Set<String> getPermissionsByUserName(String name) {
        Set<String> permissions = new HashSet<>();
        permissions.add("user:delete");
        permissions.add("user:add");
        return permissions;
    }



    /**
     * 认证
     * @param token 主体传过来的认证信息
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 1 从主体传过来的认证信息中，获得用户名
        String username = (String)token.getPrincipal();

        // 2 通过用户名到数据库中获取凭证
        String pwd = getPaswdByUserName(username);
        if(pwd == null) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("a", pwd, "myCustomDemo1");
        return simpleAuthenticationInfo;
    }

    // 模拟数据库查数据
    private String getPaswdByUserName(String username) {

        return userMap.get(username);
    }


}
