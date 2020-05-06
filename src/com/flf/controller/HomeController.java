package com.flf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flf.entity.Menu;
import com.flf.entity.User;
import com.flf.service.MenuService;
import com.flf.util.Const;
import com.flf.util.RightsHelper;
import com.flf.util.Tools;



@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private MenuService menuService;  //菜单
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/header")	
	public String header(){
		return "home/header";
	}
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/left")
	public String left(HttpSession session,Model model,Menu m){
		String roleRights = (String) session.getAttribute(Const.SESSION_ROLE_RIGHTS); //将角色权限存入session
		String userRights = (String) session.getAttribute(Const.SESSION_USER_RIGHTS); //将用户权限存入session
		m.setIsSuper((Integer) session.getAttribute(Const.SESSION_USER_ISSUPER));
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
		model.addAttribute("menuList", menuList);
				
		return "home/menu";
	}
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(HttpServletRequest req,HttpSession session,Model model){
		User user = (User)session.getAttribute(Const.SESSION_USER);
		return "home/welcome";
	}
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/footer")
	public String footer(){
		return "home/footer";
	}
	/**
	 *关于
	 * @param
	 * @return
	 */
	@RequestMapping(value="/about")
	public String about(){
		return "home/about";
	}
}
