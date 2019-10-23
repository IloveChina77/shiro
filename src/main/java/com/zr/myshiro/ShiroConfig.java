package com.zr.myshiro;

import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  配置shiro
 *  note : SecurityManager in Shiro framework must be singleton whether static or not
 *
 *  Shiro 的核心部分是 SecurityManager
 *  它负责安全认证和授权。Shiro 本身已经实现了所有细节，用户完全可以把它当作一个黑盒来使用。
 *  SecurityUtils对象  本质上就是一个工厂类似Spring中的ApplicationContext
 *
 */

@Configuration
public class ShiroConfig {

    /**
     * 创建 MyRealm 实例
     * @reutrn the instance of class MyRealm
     */
    @Bean
    public MyRealm myRealm() {
        return new MyRealm();
    }

    /**
     * 构建 SecurityManager 环境
     * @return the instance of class DefaultWebSecurity
     */
    @Bean
    DefaultWebSecurityManager defaultWebSecurityManager(MyRealm myRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myRealm);
        return defaultWebSecurityManager;
    }

    /**
     * lifecycleBeanPostProcessor
     *
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 创建ShiroFilterFactoryBean 的实例 交由 Spring 管理
     * @reutrn
     */
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
       // ShiroFilterFactoryBean 应该是集合了 Filter 和 Factory 的功能
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
       // 指定SecurityManager
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
       // 指定 登录url
        shiroFilterFactoryBean.setLoginUrl("/index.html");
       // 指定登录成功页面
        shiroFilterFactoryBean.setSuccessUrl("/index");
       // 指定
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorizedUrl");

        // 指定 路径过滤规则
        Map<String, String> map = new LinkedHashMap<>();
        // 放行一些请求
        map.put("/static/index.html","anon");
        map.put("/doLogin", "anon");
        map.put("/swagger**/**", "anon");
        map.put("/webjars/**", "anon");
        map.put("/v2/**", "anon");
        // 其余接口一律拦截
        map.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    // extra
    /**
     * 禁用 Shiro  Session
     */
    @Bean
    SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return  sessionStorageEvaluator;
    }

/*    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }*/




}
