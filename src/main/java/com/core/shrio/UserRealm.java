package com.core.shrio;

import com.core.bean.UserShrio;
import com.core.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;


    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑.doGetAuthorizationInfo");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //给资源进行授权
        //info.addStringPermission("user:add");
        //从数据库中查找当前登录用户的授权信息
        Subject subject = SecurityUtils.getSubject();
        subject.getPrincipal();
        UserShrio user  = (UserShrio)subject.getPrincipal();


        UserShrio dbUser = userService.findById(user.getId());
        System.out.println("user:"+user);
            System.out.println("info.addStringPermission(dbUser.getPerms()):"+dbUser.getPerms());
            info.addStringPermission(dbUser.getPerms());

        return info;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑.doGetAuthenticationInfo");

        //String username = "lewis";
        //String password = "123456";

        //获取用户的输入的账号.方式1
        String loginUsername = (String)authenticationToken.getPrincipal();
        System.out.println("loginUsername:"+loginUsername);
        //方式2
//        UsernamePasswordToken token =  (UsernamePasswordToken) authenticationToken ;
//        token.getUsername();
        //User user = userService.findById(1);
        UserShrio user = userService.findByName(loginUsername);

       if(user == null){//用户不存在
            return null;//Shrio底层会抛出UnknownAccountException 异常
        }

        //加密方式;
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                userInfo, //用户名
//                userInfo.getPassword(), //密码
//                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
//                getName()  //realm name
//        );

        //明文: 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
//      SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//    		  userInfo, //用户名
//    		  userInfo.getPassword(), //密码
//             getName()  //realm name
//      );
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
