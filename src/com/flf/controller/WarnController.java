package com.flf.controller;

import com.flf.entity.Warn;
import com.flf.service.MenuService;
import com.flf.service.RoleService;
import com.flf.service.WarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value="/warn")
public class WarnController {
	
	@Autowired
	private WarnService warnService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	
	@RequestMapping
	public ModelAndView list(Warn warn,HttpSession session){
		List<Warn> warnList = warnService.listPageWarn(warn);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("warn/warns");
		mv.addObject("warnList", warnList);
		mv.addObject("warn", warn);
		return mv;
	}
	

	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ModelAndView update(Warn warn){
		ModelAndView mv = new ModelAndView();
		warn.setDealTime(new Date());
		warnService.updateWarn(warn);
		mv.setViewName("warn/save_result");
		return mv;
	}
    @RequestMapping(value="/dashboard")
    public ModelAndView dashboard(Warn warn){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("warn/dashboard");
        return mv;
    }
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
