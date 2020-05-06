package com.flf.mapper;

import java.util.List;
import com.flf.entity.Menu;

public interface MenuMapper {
	List<Menu> listAllParentMenu(Menu menu);
	List<Menu> listSubMenuByParentMenu(Menu menu);
	Menu getMenuById(Integer menuId);
	void insertMenu(Menu menu);
	void updateMenu(Menu menu);
	void deleteMenuById(Integer menuId);
	List<Menu> listAllSubMenu();
}
