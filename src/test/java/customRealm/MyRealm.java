package customRealm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.Map;

public class MyRealm extends AuthorizingRealm {


    Map<String, String> userMap = new HashMap<>(5);
    {

        userMap.put("afsfs0", "123456");
        userMap.put("afsfs1", "123456");
        userMap.put("afsfs2", "123456");
        userMap.put("afsfs3", "123456");
        super.setName("myCustomRealm"); // 给realm起个名字

    }



/*  // 重写父类方法，设置realm名字
    @Override
    public void setName(String name) {
        super.setName("myCustomRealm");
    }
*/

    /**
     * @author 用于用户认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // token 是用户输入的
        // 1、从Token中取出身份信息
        String  userCode = (String) authenticationToken.getPrincipal();

        // 2、从数据库查询用户信息

        // 如果查询不到返回null
        if(!userCode.equals("usrA")){
            return null;
        }
        // 模拟从数据库查到密码
        String passwd = "123456";

        // 如果查到返回认证信息
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                userCode, passwd, this.getName()
        );
        return simpleAuthenticationInfo;
    }

    /**
     * @author 用于给用户授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

}
