package com.flf.service;

import java.util.List;

import com.flf.entity.Menu;

public interface MenuService {
	List<Menu> listAllMenu(Menu menu);
	List<Menu> listAllParentMenu(Menu menu);
	List<Menu> listSubMenuByParentMenu(Menu menu);
	Menu getMenuById(Integer menuId);
	void saveMenu(Menu menu);
	void deleteMenuById(Integer menuId);
	List<Menu> listAllSubMenu();
}
