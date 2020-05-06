package com.flf.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.flf.entity.Menu;
import com.flf.entity.Role;
import com.flf.entity.User;
import com.flf.service.MenuService;
import com.flf.service.UserService;
import com.flf.util.Const;
import com.flf.util.RightsHelper;
import com.flf.util.Tools;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;
	
	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginGet(){
		return "login";
	}
	
	/**
	 * 请求登录，验证用户
	 * @param session
	 * @param loginname
	 * @param password
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView loginPost(HttpSession session,@RequestParam String loginname,@RequestParam String password){
		ModelAndView mv = new ModelAndView();
		String errInfo = "";
			User user = userService.getUserByNameAndPwd(loginname, password);
			if(user!=null){
				user.setLastLogin(new Date());
				userService.updateLastLogin(user);
				session.setAttribute(Const.SESSION_USER, user);
				session.removeAttribute(Const.SESSION_SECURITY_CODE);
			}else{
				errInfo = "用户名或密码有误！";
			}
		if(Tools.isEmpty(errInfo)){
			mv.setViewName("redirect:index.html");
		}else{
			mv.addObject("errInfo", errInfo);
			mv.addObject("loginname",loginname);
			mv.addObject("password",password);
			mv.setViewName("login");
		}
		return mv;
	}
	
	/**
	 * 访问系统首页
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(HttpSession session,Model model,Menu m){
		User user = (User)session.getAttribute(Const.SESSION_USER);
		user = userService.getUserAndRoleById(user.getUserId());
		Role role = user.getRole();
		String roleRights = role!=null ? role.getRights() : "";
		String userRights = user.getRights();
		Integer isSuper = user.getIsSuper();
		//避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
		session.setAttribute(Const.SESSION_ROLE_RIGHTS, roleRights); //将角色权限存入session
		session.setAttribute(Const.SESSION_USER_RIGHTS, userRights); //将用户权限存入session
		session.setAttribute(Const.SESSION_USER_ISSUPER, isSuper);//将用户类型存入session
		m.setIsSuper(isSuper);
		List<Menu> menuList = menuService.listAllMenu(m);
		if(Tools.notEmpty(userRights) || Tools.notEmpty(roleRights)){
			for(Menu menu : menuList){
				menu.setHasMenu(RightsHelper.testRights(userRights, menu.getMenuId()) || RightsHelper.testRights(roleRights, menu.getMenuId()));
				if(menu.isHasMenu()){
					List<Menu> subMenuList = menu.getSubMenu();
					for(Menu sub : subMenuList){
						sub.setHasMenu(RightsHelper.testRights(userRights, sub.getMenuId()) || RightsHelper.testRights(roleRights, sub.getMenuId()));
					}
				}
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("menuList", menuList);
		return "index";
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/default")
	public String defaultPage(){
		return "default";
	}
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(Const.SESSION_USER_RIGHTS);
		return "login";
	}
	/**
	 * 首页
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/main")
	public String main(HttpSession session){
		return "index";
	}
}
