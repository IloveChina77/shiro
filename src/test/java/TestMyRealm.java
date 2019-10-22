import customRealm.MyRealmDemo1;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class TestMyRealm {

    @Test
    public void testAuthentication() {


        MyRealmDemo1 myRealmDemo1 = new MyRealmDemo1();

        // 1 构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(myRealmDemo1);

        // 2 主体（subject）提交认证请求 note ； subject 就是主体，可以是个人、第三方服务，守护进程账户，时钟守护任务或者其他和当前软件交互的任何事件
        SecurityUtils.setSecurityManager(defaultSecurityManager); // 设置SecurityManager环境
        Subject subject = SecurityUtils.getSubject(); // 获取当前subject （和当前线程绑定的subject）

        // 使用用户的登录信息创建 令牌（token）
        UsernamePasswordToken token = new UsernamePasswordToken("a","123");

        subject.login(token);

        // 主体提交认证
        subject.isAuthenticated();


        //
        System.out.println("isAuthenticated: " + subject.isAuthenticated());
        // 检查主体是否有两个角色权限，没有就抛异常
        subject.checkRoles("admin","user");
        // 检查subject 是否具有user:add权限
        subject.checkPermission("user:addaa");

    }

}
