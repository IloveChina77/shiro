package shiro;

//import com.zr.customRealm.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class Authentication {



    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();


    @Before // 在运行 testAuthentication 前添加用户
    public void addUser() {
        simpleAccountRealm.addAccount("usrAAA", "123456");
    }

    @Test
    public void testAuthentication() {
        // 1、 构建SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2、 主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager); // 设置SecurityManager环境
        Subject subject = SecurityUtils.getSubject(); // 获取当前主体

        UsernamePasswordToken token = new UsernamePasswordToken("usrA", "123456");
        subject.login(token); // 登录执行认证

        System.out.println("isAuthenticated:" + subject.isAuthenticated() );

        subject.logout();
        System.out.println("isAuthenticated:" + subject.isAuthenticated() );

    }


}
