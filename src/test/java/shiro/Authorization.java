package shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class Authorization {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();


    @Before
    public void addUser() {

        simpleAccountRealm.addAccount("hhhh", "123456", "admin", "user" );



    }

    @Test
    public void testAuthorization() {

        // 1 构建securityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);


        // 2 主体提交认证请求 SecurityUtil 本质上就是一个工厂，类似Spring中 ApplicationContext 用来管理和生成 类的实例
        SecurityUtils.setSecurityManager(defaultSecurityManager); // 设置SecurityManager 环境
        Subject subject = SecurityUtils.getSubject(); //获取当前主体

        UsernamePasswordToken token = new UsernamePasswordToken("hhhh","123456");
        subject.login(token); // 登录

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        // 判断用户权限
        subject.checkRoles("admin", "user", "abcd");














    }


}
