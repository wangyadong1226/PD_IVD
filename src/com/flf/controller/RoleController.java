package com.flf.controller;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flf.entity.Menu;
import com.flf.entity.Role;
import com.flf.service.MenuService;
import com.flf.service.RoleService;
import com.flf.util.Const;
import com.flf.util.RightsHelper;
import com.flf.util.Tools;

@Controller
@RequestMapping(value="/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	/**
	 * 显示角色列表
	 * @param map
	 * @return
	 */
	@RequestMapping
	public String list(Map<String,Object> map,HttpSession session,Role role){
		role.setIsSuper((Integer)session.getAttribute(Const.SESSION_USER_ISSUPER));
		List<Role> roleList = roleService.listAllRoles(role);
		map.put("roleList", roleList);
		return "role/roles";
	}
	
	/**
	 * 保存角色信息
	 * @param out
	 * @param role
	 */
	@RequestMapping(value="/save")
	public void save(PrintWriter out,Role role){
		boolean flag = true;
		if(role.getRoleId()!=null && role.getRoleId().intValue()>0){
			flag = roleService.updateRoleBaseInfo(role);
		}else{
			flag = roleService.insertRole(role);
		}
		if(flag){
			out.write("success");
		}else{
			out.write("failed");
		}
		out.flush();
		out.close();
	}
	
	/**
	 * 请求角色授权页面
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/auth")
	public String auth(@RequestParam int roleId,Model model,Menu m,HttpSession session){
		m.setIsSuper((Integer)session.getAttribute(Const.SESSION_USER_ISSUPER) );
		List<Menu> menuList = menuService.listAllMenu(m);
		Role role = roleService.getRoleById(roleId);
		String roleRights = role.getRights();
		if(Tools.notEmpty(roleRights)){
			for(Menu menu : menuList){
				menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMenuId()));
				if(menu.isHasMenu()){
					List<Menu> subMenuList = menu.getSubMenu();
					for(Menu sub : subMenuList){
						sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMenuId()));
					}
				}
			}
		}
		JSONArray arr = JSONArray.fromObject(menuList);
		String json = arr.toString();
		json = json.replaceAll("menuId", "id").replaceAll("menuName", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
		model.addAttribute("zTreeNodes", json);
		model.addAttribute("roleId", roleId);
		return "authorization";
	}
	
	/**
	 * 保存角色权限
	 * @param roleId
	 * @param menuIds
	 * @param out
	 */
	@RequestMapping(value="/auth/save")
	public void saveAuth(@RequestParam int roleId,@RequestParam String menuIds,PrintWriter out){
		BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
		Role role = roleService.getRoleById(roleId);
		role.setRights(rights.toString());
		roleService.updateRoleRights(role);
		out.write("success");
		out.close();
	}
}
