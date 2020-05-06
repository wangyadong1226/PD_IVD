package com.flf.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flf.entity.Menu;
import com.flf.service.MenuService;
import com.flf.util.Const;

@Controller
@RequestMapping(value="/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	/**
	 * 显示菜单列表
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String list(Model model,Menu menu,HttpSession session){
		menu.setIsSuper((Integer)session.getAttribute(Const.SESSION_USER_ISSUPER));
		List<Menu> menuList = menuService.listAllParentMenu(menu);
		model.addAttribute("menuList", menuList);
		return "menu/menus";
	}
	
	/**
	 * 请求新增菜单页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add")
	public String toAdd(Model model,Menu menu,HttpSession session){
		menu.setIsSuper((Integer)session.getAttribute(Const.SESSION_USER_ISSUPER));
		List<Menu> menuList = menuService.listAllParentMenu(menu);
		model.addAttribute("menuList", menuList);
		return "menu/menus_info";
	}
	
	/**
	 * 请求编辑菜单页面
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit")
	public String toEdit(@RequestParam Integer menuId,Model model){
		Menu menu = menuService.getMenuById(menuId);
		model.addAttribute("menu", menu);
		if(menu.getParentId()!=null && menu.getParentId().intValue()>0){
			List<Menu> menuList = menuService.listAllParentMenu(menu);
			model.addAttribute("menuList", menuList);
		}
		return "menu/menus_info";
	}
	
	/**
	 * 保存菜单信息
	 * @param menu
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/save")
	public String save(Menu menu,Model model){
		menuService.saveMenu(menu);
		model.addAttribute("msg", "success");
		return "save_result";
	}
	
	/**
	 * 获取当前菜单的所有子菜单
	 * @param menuId
	 * @param response
	 */
	@RequestMapping(value="/sub")
	public void getSub(Menu menu,HttpServletResponse response){
		List<Menu> subMenu = menuService.listSubMenuByParentMenu(menu);
		JSONArray arr = JSONArray.fromObject(subMenu);
		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			String json = arr.toString();
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除菜单
	 * @param menuId
	 * @param out
	 */
	@RequestMapping(value="/del")
	public void delete(@RequestParam Integer menuId,PrintWriter out){
		menuService.deleteMenuById(menuId);
		out.write("success");
		out.flush();
		out.close();
	}
}
