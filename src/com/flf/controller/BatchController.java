package com.flf.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.flf.entity.TbBatch;
import com.flf.entity.User;
import com.flf.entity.TbSample;
import com.flf.service.BatchService;
import com.flf.service.SampleService;
import com.flf.util.ImportCsv;
import com.flf.util.Const;

@Controller
@RequestMapping(value="/batch")
public class BatchController {
	
	@Autowired
	private  BatchService batchService;
	@Autowired
	private SampleService sampleService;
	
	/**
	 * 显示批次列表
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(HttpSession session,HttpServletRequest request,TbBatch batch){
		List<TbBatch> batchList = batchService.listPageBatch(batch);
		Iterator iterator = batchList.iterator();
		while(iterator.hasNext()){
			TbBatch tbBatch = (TbBatch) iterator.next();
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("batch/batchs");
		mv.addObject("batchList", batchList);
		mv.addObject("batch", batch);
		return mv;
	}
	
	/**
	 * 请求新增批次页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add")
	public String toAdd(Model model){
		return "batch/add";
	}
	
	@RequestMapping(value="/findDetails")
	public ModelAndView findDetails(@RequestParam Long batchId){
		TbBatch tbBatch=batchService.getBatchById(batchId);
		ModelAndView mv=new ModelAndView();
		mv.addObject("tbBatch",tbBatch);
		mv.setViewName("batch/gc_detail");
		return mv;
		
	}
 
	
	/**
	 * 保存批次信息
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	
	@RequestMapping(value="/save")
	public String saveBatch(HttpServletRequest request,HttpSession session,HttpServletResponse response,@RequestParam(value = "doc", required = false) MultipartFile doc)throws IOException {
		response.setContentType("application/json;charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");
		List<Map<Integer, String>> csvList = null;
		User user = (User)session.getAttribute(Const.SESSION_USER);
		TbBatch tbBatch = new TbBatch();
		tbBatch.setBatchNum(doc.getOriginalFilename().split("\\.")[0]);
		tbBatch.setUploadMan(user.getUsername());
		tbBatch.setUploadTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());
        batchService.insertBatch(tbBatch);
		try{
			ImportCsv test = new ImportCsv(doc.getInputStream());
			csvList = test.readCSVFile();
			if(null == csvList && csvList.size()<1){
				response.getWriter().print("csv文件内容为空，请检查。");  
				return null;
			}
			for(int a=1;a<csvList.size();a++){

	        	Map<Integer,String> map = (Map<Integer,String>) csvList.get(a);
	        	TbSample tbSample = new TbSample();
	        	if(csvList.get(0).size() < 20){
	        		response.getWriter().print("csv文件错误，少于20列。");
	    			return null;
	        	}
	        	if(csvList.get(0).size() > 20){
	        		response.getWriter().print("csv文件错误，大于20列。");
	    			return null;
	        	}
	        	for(int i=0;i<map.size();i++){
	        		switch (i)
	        		{
		        		 case 0:
							 tbSample.setFoodSubclass(map.get(i));
	        		     case 1:
	        		    	tbSample.setPacking(map.get(i));
	        		     case 2:
	        		    	tbSample.setArea(map.get(i));
	        		     case 3:
	        		    	 tbSample.setLocation(map.get(i));
	        		     case 4:
	        		    	 tbSample.setLink(map.get(i));
	         		        break;
	        		     case 5:
							tbSample.setProvince( map.get(i));
							break;
	        		     case 6:
	        		    	 tbSample.setSampleState(map.get(i));
	         		        break;
	        		     case 7:
	         		        tbSample.setSampeType(map.get(i));
	         		        break;
	        		     case 8:
	         		        tbSample.setProductionProvince(map.get(i));
	         		        break;
	        		     case 9:
	         		        tbSample.setZone(map.get(i));
	         		        break;
	        		     case 10:
	         		        tbSample.setSampleQuarter(map.get(i));
	         		        break;
	        		     case 11:
	         		        tbSample.setProductionQuarter(map.get(i));
	         		        break;
	        		     case 12:
	          		        tbSample.setGuaranteeQualityRate(map.get(i));
	          		        break;
	        		     case 13:
	          		        tbSample.setSalesVolume(map.get(i));
	          		        break;
	        		     case 14:
	          		        tbSample.setSorbicAcid(map.get(i));
	          		        break;
	        		     case 15:
	          		        tbSample.setNitrite(map.get(i));
	          		        break;
	        		     case 16:
	          		        tbSample.setNitrite(map.get(i));
	          		        break;
	        		     case 17:
	          		        tbSample.setLead(map.get(i));
	          		        break;
	        		     case 18:
	        		     	tbSample.setChromium(map.get(i));
	          		        break;
	        		     case 19:
	          		        tbSample.setCadmium(map.get(i));
	          		        break;
	        		   default:
	        			   System.out.println("多余的列!");
	        		}
	        	}
	        	tbSample.setBatchId(tbBatch.getBatchId());
	        	sampleService.insertSample(tbSample);
	        }
			response.getWriter().print("");
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除某个批次
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String deleteUser(HttpSession session,HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		Long lid=Long.parseLong(id);
		sampleService.deleteSample(lid);
		batchService.deleteBatch(lid);
		//TbBatch tbBatch=batchService.getBatchById(lid);
		//toDelAnalysis(lid,request);
		return "redirect:../batch.html";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
}
