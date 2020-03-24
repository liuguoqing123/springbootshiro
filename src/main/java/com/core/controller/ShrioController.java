package com.core.controller;

//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.SecurityUtils;
		import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShrioController {
	

	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	@ResponseBody
	public String test(){
		return "test success";
	}

	@RequestMapping(value="/testThymeleaf",method=RequestMethod.GET)
	public String testThymeleaf(String username,Model model){
		model.addAttribute("name",username);
		return "testThymeleaf";
	}

	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){
		System.out.println("进入方法add");
		return "user/add";
	}

	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(){
		System.out.println("进入方法update");
		return "user/update";
	}

	@RequestMapping(value="/toLogin",method=RequestMethod.GET)
	public String toLogin(){
		System.out.println("进入方法toLogin");
		return "/login";
	}


    @RequestMapping(value="/noauth",method=RequestMethod.GET)
    public String noauth(){
        System.out.println("进入方法noauth");
        return "user/noauth";
    }

	@RequestMapping(value="/login")
	public String login(String username,String password,Model model){
		System.out.println("username:"+username);
		//获取subject数据
		Subject subject  = SecurityUtils.getSubject();
		//封装用户数据
		UsernamePasswordToken token  = new UsernamePasswordToken(username,password);
		//执行登录方法
		try {
			//登陆成功
			subject.login(token);
			//redirect 重定向请求
			return "redirect:/testThymeleaf";
		} catch (UnknownAccountException e) {
			//e.printStackTrace();
			//登录失败，用户账号不存在！
			model.addAttribute("msg","登录失败，用户账号不存在！");
			System.out.println("登录失败，用户账号不存在！");
			return "login";
		}catch (IncorrectCredentialsException e){
			//e.printStackTrace();
			//密码错误
			model.addAttribute("msg","登录失败，用户密码错误！");
			System.out.println("登录失败，用户密码错误！");
			return "login";
		}
		//return "user/login";
	}
	
}
