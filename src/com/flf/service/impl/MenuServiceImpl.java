package com.flf.service.impl;

import java.util.List;

import com.flf.entity.Menu;
import com.flf.mapper.MenuMapper;
import com.flf.service.MenuService;

public class MenuServiceImpl implements MenuService{

	private MenuMapper menuMapper;
	
	public void deleteMenuById(Integer menuId) {
		// TODO Auto-generated method stub
		menuMapper.deleteMenuById(menuId);
	}

	public Menu getMenuById(Integer menuId) {
		// TODO Auto-generated method stub
		return menuMapper.getMenuById(menuId);
	}

	public List<Menu> listAllParentMenu(Menu menu) {
		// TODO Auto-generated method stub
		return menuMapper.listAllParentMenu(menu);
	}

	public void saveMenu(Menu menu) {
		// TODO Auto-generated method stub
		if(menu.getMenuId()!=null && menu.getMenuId().intValue()>0){
			menuMapper.updateMenu(menu);
		}else{
			menuMapper.insertMenu(menu);
		}
	}

	public List<Menu> listSubMenuByParentMenu(Menu menu) {
		// TODO Auto-generated method stub
		return menuMapper.listSubMenuByParentMenu(menu);
	}
	
	public List<Menu> listAllMenu(Menu m) {
		// TODO Auto-generated method stub
		List<Menu> rl = this.listAllParentMenu(m);
		for(Menu menu : rl){
			menu.setParentId(menu.getMenuId());
			List<Menu> subList = this.listSubMenuByParentMenu(menu);
			menu.setSubMenu(subList);
		}
		return rl;
	}

	public List<Menu> listAllSubMenu(){
		return menuMapper.listAllSubMenu();
	}
	
	public MenuMapper getMenuMapper() {
		return menuMapper;
	}

	public void setMenuMapper(MenuMapper menuMapper) {
		this.menuMapper = menuMapper;
	}

}
