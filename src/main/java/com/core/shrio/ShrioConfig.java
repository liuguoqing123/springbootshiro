package com.core.shrio;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShrioConfig {

    /*创建ShrioFilterFactoryBean*/
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager SecurityManager ){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(SecurityManager);
        //添加Shrio内置过滤器
        /**
         * Shrio内置过滤器，可以实现相关权限拦截
         * 常用过滤器：
         *      anon:无需认证（登录）即可访问
         *      authc:必须认证才可以访问
         *      user：如果使用rememberMe的功能可以直接访问
         *      perms:该资源必须得到资源权限才可以访问
         *      role:该资源必须得到 角色权限才可以访问
         */
        Map<String,String> filterMap = new LinkedHashMap<String,String>();
        //filterMap.put("/add","authc");
        //filterMap.put("/update","authc");
        filterMap.put("/testThymeleaf","anon");
        filterMap.put("/login","anon");
        //授权过滤器
        //当授权拦截后，shrio会自动跳转到未授权页面
        filterMap.put("/add","perms[user:add]");
        filterMap.put("/update","perms[user:update]");
        //<!-- 过滤链定义，从上向下顺序执行，一般将 /** 或者/user/* 放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        filterMap.put("/*", "authc");

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        // 登录成功后要跳转的链接
        //        shiroFilterFactoryBean.setSuccessUrl("/index");
        //        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }
   /*创建DefaultWebSecurityManager*/
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }
   /* 创建Realm*/
    @Bean(name = "userRealm")
    public UserRealm getUserRealm(){
        return new UserRealm();
    }

    /**
     * ShiroDialect,用于thymeleaf和Shrio标签混合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        System.out.println("ShiroDialect");
        return new ShiroDialect();
    }

}
