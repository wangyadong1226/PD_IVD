package com.flf.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.flf.entity.Role;
import com.flf.entity.TbBatch;
import com.flf.entity.TbSample;
import com.flf.entity.User;
import com.flf.service.BatchService;
import com.flf.service.RoleService;
import com.flf.service.SampleService;
import com.flf.util.CommonDateParseUtil;
import com.flf.util.Const;
import com.flf.view.ExportExcelView;
import com.flf.view.ExportMultiExcelView;

@Controller
@RequestMapping(value="/sample")
public class SampleController {
	
	@Autowired
	private SampleService sampleService;
	
	@Autowired
	private BatchService batchService;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 样本查询列表
	 * @param sample
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(HttpSession session,HttpServletRequest request,TbSample sample){
		ModelAndView mv=new ModelAndView();
		List<TbSample> sampleList=sampleService.listPageSampleByBatchId(sample);
		mv.addObject("sampleList",sampleList);
		mv.setViewName("sample/samples");
		mv.addObject("sample", sample);
		return mv;
	}
	/**
	 * 请求批次管理详情页面
	 * @param
	 * @return
	 */
	@RequestMapping(value="/detailList")
	public ModelAndView detailList(HttpSession session,HttpServletRequest request,TbSample sample){
		/*User user = (User)session.getAttribute(Const.SESSION_USER);
		if(null != user){
			request.setAttribute("roleId", user.getRoleId());
		}
		Role role=roleService.getRoleById(user.getRoleId());
		//sample.setUnitFrom(role.getUnitFrom().getName());
		String id = request.getParameter("id");*/
		List<TbSample> sampleList = sampleService.listPageSampleByBatchId(sample);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("batch/detail");
		mv.addObject("sampleList", sampleList);
		mv.addObject("sample", sample);
		return mv;
	}
	/**
	 * 请求样本结果详情页面
	 * @param
	 * @return
	 */
	@RequestMapping(value="/listToExamine")
	public ModelAndView listToExamine(HttpSession session,HttpServletRequest request,@RequestParam(value="id", required = false) Long id){
		User user = (User)session.getAttribute(Const.SESSION_USER);
		if(null != user){
			request.setAttribute("roleId", user.getRoleId());
		}
		TbSample sample=sampleService.getSampleById(id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("item", sample);
		mv.setViewName("batch/examineDetail");
		return mv;
	}
	
	@RequestMapping(value="/updateStatus")
	public String updateStatus(TbSample sample){
		/*if(sample.getFlag().equals("0")){
			sample.setCheckStatus("不通过");
		}else {
			sample.setCheckStatus("通过");
		}
		sampleService.updateStatus(sample);
		TbBatch batch=batchService.getBatchById(new Long(sample.getBatchId()));
		List<TbSample> list=sampleService.getSampleByBatchId(batch.getBatchId());
		boolean flag=true;
		for(TbSample sam:list){
			if(sam.getCheckStatus().equals("未审核")){
				flag=false;
				break;
			}
			if(flag){
				batch.setCheckStatus("已审核");
				batchService.updateBatchStatus(batch);
			}
			
		}
		return "redirect:detailList.html?id="+batch.getBatchId()+"&flag=2";*/
		return "";
	}
	@RequestMapping(value="/updateStatusForBatch")
	public void updateStatusForBatch(@RequestParam(value="ids") String ids,TbSample sample,PrintWriter out) throws IOException{
		/*String[] arrayId=ids.split(",");
		for(String id:arrayId){
			sample.setTbSampleId(Long.parseLong(id));
			if(sample.getCheckStatus().equals("2")){
				sample.setCheckStatus("不通过");
			}else {
				sample.setCheckStatus("通过");
			}
			sampleService.updateStatus(sample);
		}
		TbBatch batch=batchService.getBatchById(new Long(sample.getBatchId()));
		List<TbSample> list=sampleService.getSampleByBatchId(batch.getBatchId());
		boolean flag=true;
		for(TbSample sam:list){
			if(sam.getCheckStatus().equals("未审核")){
				flag=false;
				break;
			}
		}
		if(flag){
			batch.setCheckStatus("已审核");
			batchService.updateBatchStatus(batch);
		}
		out.print("success");
		out.close();*/
		
	}
	
	/**
	 * 高级查询 导出Excel
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportExcel")
	public void exportExcel(HttpSession session,Model model,HttpServletRequest request, HttpServletResponse response,TbSample sample) throws Exception{
		User user=(User)session.getAttribute(Const.SESSION_USER);
		/*Role role=roleService.getRoleById(user.getRoleId());
		sample.setUnitFrom(role.getUnitFrom().getName());
		String sql = createSQL(sample);
		sample.setSql(sql);
		List<TbSample> list = sampleService.listSample(sample);
		toExcelModl(list,request, response,null);*/
	}
	
	/**
	 * 同一批次的样本 导出Excel
	 * @param
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportExcelForBatch")
	public void exportExcelForBatch(HttpSession session,HttpServletRequest request, HttpServletResponse response,TbSample sample) throws Exception{
		request.setAttribute("batchId", sample.getBatchId());
		User user=(User)session.getAttribute(Const.SESSION_USER);
		Role role=roleService.getRoleById(user.getRoleId());
		String sql = createSQL(sample);
		sample.setSql(sql);
		List<TbSample> list = sampleService.listSample(sample);
		TbBatch batch= batchService.getBatchById(new Long(sample.getBatchId()));
		toExcelModl(list,request, response,batch.getBatchNum());
	}
	public void toExcelModl(List<TbSample> list,HttpServletRequest request, HttpServletResponse response,String fileName ) throws Exception{
		
		String title = "样本导出结果";
		String[] rowsName = new String[]{"序号","接收日期","样本ID","文库ID","孕妇姓名","住院门诊号","样本状态","送检医生","T13","T18","T21","Z13","Z18","Z21",
    			"质控结果","孕妇年龄","孕妇孕周","样本类型","送检单位/部门","采样日期","临床诊断","末次月经","孕产史","妊娠类型","NT值","唐筛结果","唐筛比例",
    			"特殊情况说明","全血ID","胎数","医院编码","index","原始质控量(M)","Q30","比对率","Duplication 率","Unique reads数量(M)","样本GC含量"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		/*for (int i = 0;i<list.size();i++) {
			TbSample tbSample = list.get(i);
			objs = new Object[rowsName.length];
			objs[0] = i;
			objs[1] = tbSample.getRecieveDate();
			objs[2] = tbSample.getSampleId();
			objs[3] = tbSample.getLibId();
			objs[4] = tbSample.getName();
			objs[5] = tbSample.getOutpatientNum();
			objs[6] = tbSample.getStatusName();
			objs[7] = tbSample.getDoctorFrom();
			objs[8] = tbSample.getT13Name();
			objs[9] = tbSample.getT18Name();
			objs[10] = tbSample.getT21Name();
			objs[11] = tbSample.getZ13Name();
			objs[12] = tbSample.getZ18Name();
			objs[13] = tbSample.getZ21Name();
			objs[14] = tbSample.getRawQCName();
			objs[15] = tbSample.getAge();
			objs[16] = tbSample.getGesWeek();
			objs[17] = tbSample.getSampleType();
			objs[18] = tbSample.getUnitFrom();
			objs[19] = tbSample.getSamplingDate();
			objs[20] = tbSample.getDiagnosis();
			objs[21] = tbSample.getLastPeriod();
			objs[22] = tbSample.getPregHistory();
			objs[23] = tbSample.getIvfGestation();
			objs[24] = tbSample.getNT();
			objs[25] = tbSample.getDownRes();
			objs[26] = tbSample.getDownRatio();
			objs[27] = tbSample.getSpecialInfo();
			objs[28] = tbSample.getBloodId();
			objs[29] = tbSample.getFetusNum();
			objs[30] = tbSample.getHospitalCode();
			objs[31] = tbSample.getIndexNum();
			objs[32] = tbSample.getRawDataName();
			objs[33] = tbSample.getQ30Name();
			objs[34] = tbSample.getAlignRateName();
			objs[35] = tbSample.getDupRateName();
			objs[36] = tbSample.getUrNumName();
			objs[37] = tbSample.getGCName();
			dataList.add(objs);
		}*/
		
		ExportExcelView ex = new ExportExcelView(title, rowsName, dataList,response);
		if(null!=fileName){
			ex.export(fileName);
		}else{
			ex.export(null);
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	public String createSQL(TbSample item){
		String sql = "SELECT A.* FROM tb_sample A,tb_batch B WHERE A.batch_id = B.id ";
		/*if (null!=item){
			if (null != item.getBatchId() && item.getBatchId() != 0){ 
				sql += "and A.batch_id= "+item.getBatchId()+"  ";	
			}
			
			if (isNotNullOrEmpty(item.getName())){   //孕妇名字
				sql += "and A.name like '%"
						+item.getName()+"%' ";				
			}
			if (isNotNullOrEmpty(item.getUnitFrom()) && !item.getUnitFrom().equals("安诺优达")){   //非安诺优达的角色只能查看本单位的样本
				sql += "and A.unit_from like '%"
						+item.getUnitFrom()+"%' ";				
			}
			if (isNotNullOrEmpty(item.getDoctorFrom())){   //送检医生
				sql += "and A.doctor_from like '%"
						+item.getDoctorFrom()+"%' ";				
			}
			if (isNotNullOrEmpty(item.getSampleId())){  //样本ID
				sql += "and A.sample_id like '%"
						+item.getSampleId()+"%' ";				
			}
			if (isNotNullOrEmpty(item.getStatus()) && !item.getStatus().equals("-1")){  //样本状态
				sql += "and A.status = '"
						+item.getStatus()+"' ";				
			}
			if (isNotNullOrEmpty(item.getTips()) && !item.getTips().equals("-1")){  //样本处理建议
				sql += "and A.tips = '"
						+item.getTips()+"' ";				
			}
			if (isNotNullOrEmpty(item.getCheckStatus()) && item.getCheckStatus().equals("0")){  //审核状态
				sql += "and A.check_status = '未审核' ";				
			}
			
			if (isNotNullOrEmpty(item.getCheckStatus()) && item.getCheckStatus().equals("1")){  //审核状态
				sql += "and A.check_status = '通过' ";				
			}
			
			if (isNotNullOrEmpty(item.getCheckStatus()) && item.getCheckStatus().equals("2")){  //审核状态
				sql += "and A.check_status = '不通过' ";				
			}
			
			
			if (isNotNullOrEmpty(item.getConditionAName()) || isNotNullOrEmpty(item.getConditionAOpt()) || isNotNullOrEmpty(item.getMoreA1()) || isNotNullOrEmpty(item.getMoreA2())){  //高级查询A
				if(item.getConditionA().equals("recieve_date") || item.getConditionA().equals("sampling_date") || item.getConditionA().equals("last_period")){  //判断是否是日期
					if (isNotNullOrEmpty(item.getMoreA1()) && isNotNullOrEmpty(item.getMoreA2())){   //日期范围查询
						sql += "and A."+item.getConditionA()+" >= '"
								+CommonDateParseUtil.strDate2strDate(item.getMoreA1())+"' ";	
						sql += "and A."+item.getConditionA()+" <= '"
							+CommonDateParseUtil.strDate2strDate(item.getMoreA2())+"' ";	
					}
					
					if (isNotNullOrEmpty(item.getMoreA1()) && !isNotNullOrEmpty(item.getMoreA2())){   //输入第一个日期
						sql += "and A."+item.getConditionA()+" >= '"
								+CommonDateParseUtil.strDate2strDate(item.getMoreA1())+"' ";	
					}
					
					if (!isNotNullOrEmpty(item.getMoreA1()) && isNotNullOrEmpty(item.getMoreA2())){   //输入第二个日期
						sql += "and A."+item.getConditionA()+" <= '"
								+CommonDateParseUtil.strDate2strDate(item.getMoreA2())+"' ";	
					}
				}
				//判断是否是年龄,孕周,NT值,原始数据量,Q30,比对率,Duplication 率,Unique reads 数量,样本GC含量
				if(item.getConditionA().equals("age") || item.getConditionA().equals("ges_week") || item.getConditionA().equals("NT") || item.getConditionA().equals("raw_data") || item.getConditionA().equals("Q30") || item.getConditionA().equals("align_rate") ||  item.getConditionA().equals("dup_rate") || item.getConditionA().equals("ur_num") ||  item.getConditionA().equals("GC")){  
					if(item.getConditionA().equals("ges_week")){
						item.setMoreA1(isNotNullOrEmpty(item.getMoreA1()) ? item.getMoreA1()+"w" : "");
						item.setMoreA2(isNotNullOrEmpty(item.getMoreA2()) ? item.getMoreA2()+"w" : "");
					}
					if(item.getConditionA().equals("raw_data") || item.getConditionA().equals("ur_num")){
						item.setMoreA1(isNotNullOrEmpty(item.getMoreA1()) ? String.valueOf(new BigDecimal(item.getMoreA1()).multiply(new BigDecimal(1000000))) : "");
						item.setMoreA2(isNotNullOrEmpty(item.getMoreA2()) ? String.valueOf(new BigDecimal(item.getMoreA2()).multiply(new BigDecimal(1000000))) : "");
					}
					if(item.getConditionA().equals("align_rate") || item.getConditionA().equals("dup_rate") || item.getConditionA().equals("GC")){
						item.setMoreA1(isNotNullOrEmpty(item.getMoreA1()) ? String.valueOf(new BigDecimal(item.getMoreA1()).divide(new BigDecimal(100))) : "");
						item.setMoreA2(isNotNullOrEmpty(item.getMoreA2()) ? String.valueOf(new BigDecimal(item.getMoreA2()).divide(new BigDecimal(100))) : "");
					}
					if (isNotNullOrEmpty(item.getMoreA1()) && isNotNullOrEmpty(item.getMoreA2())){   //范围查询
						sql += "and A."+item.getConditionA()+" >= '"+item.getMoreA1()+"' ";	
						sql += "and A."+item.getConditionA()+" <= '"+item.getMoreA2()+"' ";	
					}
					
					if (isNotNullOrEmpty(item.getMoreA1()) && !isNotNullOrEmpty(item.getMoreA2())){   //输入第一个值
						sql += "and A."+item.getConditionA()+" >= '"+item.getMoreA1()+"' ";	
					}
					
					if (!isNotNullOrEmpty(item.getMoreA1()) && isNotNullOrEmpty(item.getMoreA2())){   //输入第二个值
						sql += "and A."+item.getConditionA()+" <= '"+item.getMoreA2()+"' ";	
					}
				}
				
				if(item.getConditionA().equals("Z13") || item.getConditionA().equals("Z18") || item.getConditionA().equals("Z21") || item.getConditionA().equals("fetus_num")){  //判断是否是T13  T18  T21  下拉列表查询
					if(item.getConditionAOpt().equals("T13") || item.getConditionAOpt().equals("T18") || item.getConditionAOpt().equals("T21") || item.getConditionAOpt().equals("low_risk") || item.getConditionAOpt().equals("grey")  ){
						if(item.getConditionAOpt().equals("T13") || item.getConditionAOpt().equals("T18") || item.getConditionAOpt().equals("T21")){
							sql += "and A."+item.getConditionA()+" >= 4 ";;
						}
						if(item.getConditionAOpt().equals("low_risk")){
							sql += "and A."+item.getConditionA()+" <= 3 ";;
						}
						if(item.getConditionAOpt().equals("grey")){
							sql += "and A."+item.getConditionA()+" >= 3  and A."+item.getConditionA()+"  < 4 ";;
						}
					}else{
						sql += "and A."+item.getConditionA()+" = '"+item.getConditionAOpt()+"' ";
					}
				}
				
				if(!item.getConditionA().equals("recieve_date") && !item.getConditionA().equals("sampling_date") && !item.getConditionA().equals("last_period") && !item.getConditionA().equals("age") && !item.getConditionA().equals("ges_week") && !item.getConditionA().equals("NT") && !item.getConditionA().equals("raw_data") && !item.getConditionA().equals("Q30") && !item.getConditionA().equals("align_rate") && !item.getConditionA().equals("dup_rate") && !item.getConditionA().equals("ur_num") &&  !item.getConditionA().equals("GC") && !item.getConditionA().equals("Z13") && !item.getConditionA().equals("Z18") && !item.getConditionA().equals("Z21")){  //判断是否精确查询
					sql += "and A."+item.getConditionA()+" = '"+item.getConditionAName()+"' ";
				}
			}
			
			if (isNotNullOrEmpty(item.getConditionBName()) || isNotNullOrEmpty(item.getConditionBOpt()) || isNotNullOrEmpty(item.getMoreB1()) || isNotNullOrEmpty(item.getMoreB2())){  //高级查询B
				
				if(item.getConditionB().equals("recieve_date") || item.getConditionB().equals("sampling_date") || item.getConditionB().equals("last_period")){  //判断是否是日期
					if (isNotNullOrEmpty(item.getMoreB1()) && isNotNullOrEmpty(item.getMoreB2())){   //日期范围查询
						sql += "and A."+item.getConditionB()+" >= '"
								+CommonDateParseUtil.strDate2strDate(item.getMoreB1())+"' ";	
						sql += "and A."+item.getConditionB()+" <= '"
							+CommonDateParseUtil.strDate2strDate(item.getMoreB2())+"' ";	
					}
					
					if (isNotNullOrEmpty(item.getMoreB1()) && !isNotNullOrEmpty(item.getMoreB2())){   //输入第一个日期
						sql += "and A."+item.getConditionB()+" >= '"
								+CommonDateParseUtil.strDate2strDate(item.getMoreB1())+"' ";	
					}
					
					if (!isNotNullOrEmpty(item.getMoreB1()) && isNotNullOrEmpty(item.getMoreB2())){   //输入第二个日期
						sql += "and A."+item.getConditionB()+" <= '"
								+CommonDateParseUtil.strDate2strDate(item.getMoreB2())+"' ";	
					}
				}
				//判断是否是年龄,孕周,NT值,原始数据量,Q30,比对率,Duplication 率,Unique reads 数量,样本GC含量
				if(item.getConditionB().equals("age") || item.getConditionB().equals("ges_week") || item.getConditionB().equals("NT") || item.getConditionB().equals("raw_data") || item.getConditionB().equals("Q30") || item.getConditionB().equals("align_rate") ||  item.getConditionB().equals("dup_rate") || item.getConditionB().equals("ur_num") ||  item.getConditionB().equals("GC")){ 
					if(item.getConditionB().equals("ges_week")){
						item.setMoreB1(isNotNullOrEmpty(item.getMoreB1()) ? item.getMoreB1()+"w" : "");
						item.setMoreB2(isNotNullOrEmpty(item.getMoreB2()) ? item.getMoreB2()+"w" : "");
					}
					if(item.getConditionB().equals("raw_data") || item.getConditionB().equals("ur_num")){
						item.setMoreB1(isNotNullOrEmpty(item.getMoreB1()) ? String.valueOf(new BigDecimal(item.getMoreB1()).multiply(new BigDecimal(1000000))) : "");
						item.setMoreB2(isNotNullOrEmpty(item.getMoreB2()) ? String.valueOf(new BigDecimal(item.getMoreB2()).multiply(new BigDecimal(1000000))) : "");
					}
					if(item.getConditionB().equals("align_rate") || item.getConditionB().equals("dup_rate") || item.getConditionB().equals("GC")){
						item.setMoreB1(isNotNullOrEmpty(item.getMoreB1()) ? String.valueOf(new BigDecimal(item.getMoreB1()).divide(new BigDecimal(100))) : "");
						item.setMoreB2(isNotNullOrEmpty(item.getMoreB2()) ? String.valueOf(new BigDecimal(item.getMoreB2()).divide(new BigDecimal(100))) : "");
					}
					if (isNotNullOrEmpty(item.getMoreB1()) && isNotNullOrEmpty(item.getMoreB2())){   //范围查询
						sql += "and A."+item.getConditionB()+" >= '"+item.getMoreB1()+"' ";	
						sql += "and A."+item.getConditionB()+" <= '"+item.getMoreB2()+"' ";	
					}
					
					if (isNotNullOrEmpty(item.getMoreB1()) && !isNotNullOrEmpty(item.getMoreB2())){   //输入第一个值
						sql += "and A."+item.getConditionB()+" >= '"+item.getMoreB1()+"' ";	
					}
					
					if (!isNotNullOrEmpty(item.getMoreB1()) && isNotNullOrEmpty(item.getMoreB2())){   //输入第二个值
						sql += "and A."+item.getConditionB()+" <= '"+item.getMoreB2()+"' ";	
					}
				}
				
				if(item.getConditionB().equals("Z13") || item.getConditionB().equals("Z18") || item.getConditionB().equals("Z21") || item.getConditionB().equals("fetus_num")){  //判断是否是T13  T18  T21  下拉列表查询
					if(item.getConditionBOpt().equals("T13") || item.getConditionBOpt().equals("T18") || item.getConditionBOpt().equals("T21") || item.getConditionBOpt().equals("low_risk") || item.getConditionBOpt().equals("grey")  ){
						if(item.getConditionBOpt().equals("T13") || item.getConditionBOpt().equals("T18") || item.getConditionBOpt().equals("T21")){
							sql += "and A."+item.getConditionB()+" >= 4 ";;
						}
						if(item.getConditionBOpt().equals("low_risk")){
							sql += "and A."+item.getConditionB()+" <= 3 ";;
						}
						if(item.getConditionBOpt().equals("grey")){
							sql += "and A."+item.getConditionB()+" >= 3  and A."+item.getConditionB()+"  < 4 ";;
						}
					}else{
						sql += "and A."+item.getConditionB()+" = '"+item.getConditionBOpt()+"' ";
					}
				}
				
				if(!item.getConditionB().equals("recieve_date") && !item.getConditionB().equals("sampling_date") && !item.getConditionB().equals("last_period") && !item.getConditionB().equals("age") && !item.getConditionB().equals("ges_week")&& !item.getConditionB().equals("NT") && !item.getConditionB().equals("raw_data") && !item.getConditionB().equals("Q30") && !item.getConditionB().equals("align_rate") && !item.getConditionB().equals("dup_rate") && !item.getConditionB().equals("ur_num") &&  !item.getConditionB().equals("GC") && !item.getConditionB().equals("Z13") && !item.getConditionB().equals("Z18") && !item.getConditionB().equals("Z21")){  //判断是否精确查询
					sql += "and A."+item.getConditionB()+" = '"+item.getConditionBName()+"' ";
				}
			}
			
			if (isNotNullOrEmpty(item.getConditionCName()) || isNotNullOrEmpty(item.getConditionCOpt()) || isNotNullOrEmpty(item.getMoreC1()) || isNotNullOrEmpty(item.getMoreC2())){  //高级查询C
				
				if(item.getConditionC().equals("recieve_date") || item.getConditionC().equals("sampling_date") || item.getConditionC().equals("last_period")){  //判断是否是日期
					if (isNotNullOrEmpty(item.getMoreC1()) && isNotNullOrEmpty(item.getMoreC2())){   //日期范围查询
						sql += "and A."+item.getConditionC()+" >= '"
								+CommonDateParseUtil.strDate2strDate(item.getMoreC1())+"' ";	
						sql += "and A."+item.getConditionC()+" <= '"
							+CommonDateParseUtil.strDate2strDate(item.getMoreC2())+"' ";	
					}
					
					if (isNotNullOrEmpty(item.getMoreC1()) && !isNotNullOrEmpty(item.getMoreC2())){   //输入第一个日期
						sql += "and A."+item.getConditionC()+" >= '"
								+CommonDateParseUtil.strDate2strDate(item.getMoreC1())+"' ";	
					}
					
					if (!isNotNullOrEmpty(item.getMoreC1()) && isNotNullOrEmpty(item.getMoreC2())){   //输入第二个日期
						sql += "and A."+item.getConditionC()+" <= '"
								+CommonDateParseUtil.strDate2strDate(item.getMoreC2())+"' ";	
					}
				}
				//判断是否是年龄,孕周,NT值,原始数据量,Q30,比对率,Duplication 率,Unique reads 数量,样本GC含量
				if(item.getConditionC().equals("age") || item.getConditionC().equals("ges_week") || item.getConditionC().equals("NT") || item.getConditionC().equals("raw_data") || item.getConditionC().equals("Q30") || item.getConditionC().equals("align_rate") ||  item.getConditionC().equals("dup_rate") || item.getConditionC().equals("ur_num") ||  item.getConditionC().equals("GC")){
					if(item.getConditionC().equals("ges_week")){
						item.setMoreC1(isNotNullOrEmpty(item.getMoreC1()) ? item.getMoreC1()+"w" : "");
						item.setMoreC2(isNotNullOrEmpty(item.getMoreC2()) ? item.getMoreC2()+"w" : "");
					}
					if(item.getConditionC().equals("raw_data") || item.getConditionC().equals("ur_num")){
						item.setMoreC1(isNotNullOrEmpty(item.getMoreC1()) ? String.valueOf(new BigDecimal(item.getMoreC1()).multiply(new BigDecimal(1000000))) : "");
						item.setMoreC2(isNotNullOrEmpty(item.getMoreC2()) ? String.valueOf(new BigDecimal(item.getMoreC2()).multiply(new BigDecimal(1000000))) : "");
					}
					if(item.getConditionC().equals("align_rate") || item.getConditionC().equals("dup_rate") || item.getConditionC().equals("GC")){
						item.setMoreC1(isNotNullOrEmpty(item.getMoreC1()) ? String.valueOf(new BigDecimal(item.getMoreC1()).divide(new BigDecimal(100))) : "");
						item.setMoreC2(isNotNullOrEmpty(item.getMoreC2()) ? String.valueOf(new BigDecimal(item.getMoreC2()).divide(new BigDecimal(100))) : "");
					}
					if (isNotNullOrEmpty(item.getMoreC1()) && isNotNullOrEmpty(item.getMoreC2())){   //范围查询
						sql += "and A."+item.getConditionC()+" >= '"+item.getMoreC1()+"' ";	
						sql += "and A."+item.getConditionC()+" <= '"+item.getMoreC2()+"' ";	
					}
					
					if (isNotNullOrEmpty(item.getMoreC1()) && !isNotNullOrEmpty(item.getMoreC2())){   //输入第一个值
						sql += "and A."+item.getConditionC()+" >= '"+item.getMoreC1()+"' ";	
					}
					
					if (!isNotNullOrEmpty(item.getMoreC1()) && isNotNullOrEmpty(item.getMoreC2())){   //输入第二个值
						sql += "and A."+item.getConditionC()+" <= '"+item.getMoreC2()+"' ";	
					}
				}
				
				if(item.getConditionC().equals("Z13") || item.getConditionC().equals("Z18") || item.getConditionC().equals("Z21") || item.getConditionC().equals("fetus_num")){  //判断是否是T13  T18  T21  下拉列表查询
					if(item.getConditionCOpt().equals("T13") || item.getConditionCOpt().equals("T18") || item.getConditionCOpt().equals("T21") || item.getConditionCOpt().equals("low_risk") || item.getConditionCOpt().equals("grey")  ){
						if(item.getConditionCOpt().equals("T13") || item.getConditionCOpt().equals("T18") || item.getConditionCOpt().equals("T21")){
							sql += "and A."+item.getConditionC()+" >= 4 ";;
						}
						if(item.getConditionCOpt().equals("low_risk")){
							sql += "and A."+item.getConditionC()+" <= 3 ";;
						}
						if(item.getConditionCOpt().equals("grey")){
							sql += "and A."+item.getConditionC()+" >= 3  and A."+item.getConditionC()+"  < 4 ";;
						}
					}else{
						sql += "and A."+item.getConditionC()+" = '"+item.getConditionCOpt()+"' ";
					}
				}
				
				if(!item.getConditionC().equals("recieve_date") && !item.getConditionC().equals("sampling_date") && !item.getConditionC().equals("last_period") && !item.getConditionC().equals("age") && !item.getConditionC().equals("ges_week")&& !item.getConditionC().equals("NT") && !item.getConditionC().equals("raw_data") && !item.getConditionC().equals("Q30") && !item.getConditionC().equals("align_rate") && !item.getConditionC().equals("dup_rate") && !item.getConditionC().equals("ur_num") &&  !item.getConditionC().equals("GC")&& !item.getConditionC().equals("Z13") && !item.getConditionC().equals("Z18") && !item.getConditionC().equals("Z21")){  //判断是否精确查询
					sql += "and A."+item.getConditionC()+" = '"+item.getConditionCName()+"' ";
				}
			}
			
			if (isNotNullOrEmpty(item.getConditionDName()) || isNotNullOrEmpty(item.getConditionDOpt())  || isNotNullOrEmpty(item.getMoreD1()) || isNotNullOrEmpty(item.getMoreD2())){  //高级查询D
				if(item.getConditionD().equals("recieve_date") || item.getConditionD().equals("sampling_date") || item.getConditionD().equals("last_period")){  //判断是否是日期
					if (isNotNullOrEmpty(item.getMoreD1()) && isNotNullOrEmpty(item.getMoreD2())){   //日期范围查询
						sql += "and A."+item.getConditionD()+" >= '"
								+CommonDateParseUtil.strDate2strDate(item.getMoreD1())+"' ";	
						sql += "and A."+item.getConditionD()+" <= '"
							+CommonDateParseUtil.strDate2strDate(item.getMoreD2())+"' ";	
					}
					
					if (isNotNullOrEmpty(item.getMoreD1()) && !isNotNullOrEmpty(item.getMoreD2())){   //输入第一个日期
						sql += "and A."+item.getConditionD()+" >= '"
								+CommonDateParseUtil.strDate2strDate(item.getMoreD1())+"' ";	
					}
					
					if (!isNotNullOrEmpty(item.getMoreD1()) && isNotNullOrEmpty(item.getMoreD2())){   //输入第二个日期
						sql += "and A."+item.getConditionD()+" <= '"
								+CommonDateParseUtil.strDate2strDate(item.getMoreD2())+"' ";	
					}
				}
				//判断是否是年龄,孕周,NT值,原始数据量,Q30,比对率,Duplication 率,Unique reads 数量,样本GC含量
				if(item.getConditionD().equals("age") || item.getConditionD().equals("ges_week") || item.getConditionD().equals("NT") || item.getConditionD().equals("raw_data") || item.getConditionD().equals("Q30") || item.getConditionD().equals("align_rate") ||  item.getConditionD().equals("dup_rate") || item.getConditionD().equals("ur_num") ||  item.getConditionD().equals("GC")){
					if(item.getConditionD().equals("ges_week")){
						item.setMoreD1(isNotNullOrEmpty(item.getMoreD1()) ? item.getMoreD1()+"w" : "");
						item.setMoreD2(isNotNullOrEmpty(item.getMoreD2()) ? item.getMoreD2()+"w" : "");
					}
					if(item.getConditionD().equals("raw_data") || item.getConditionD().equals("ur_num")){
						item.setMoreD1(isNotNullOrEmpty(item.getMoreD1()) ? String.valueOf(new BigDecimal(item.getMoreD1()).multiply(new BigDecimal(1000000))) : "");
						item.setMoreD2(isNotNullOrEmpty(item.getMoreD2()) ? String.valueOf(new BigDecimal(item.getMoreD2()).multiply(new BigDecimal(1000000))) : "");
					}
					if(item.getConditionD().equals("align_rate") || item.getConditionD().equals("dup_rate") || item.getConditionD().equals("GC")){
						item.setMoreD1(isNotNullOrEmpty(item.getMoreD1()) ? String.valueOf(new BigDecimal(item.getMoreD1()).divide(new BigDecimal(100))) : "");
						item.setMoreD2(isNotNullOrEmpty(item.getMoreD2()) ? String.valueOf(new BigDecimal(item.getMoreD2()).divide(new BigDecimal(100))) : "");
					}
					if (isNotNullOrEmpty(item.getMoreD1()) && isNotNullOrEmpty(item.getMoreD2())){   //范围查询
						sql += "and A."+item.getConditionD()+" >= '"+item.getMoreD1()+"' ";	
						sql += "and A."+item.getConditionD()+" <= '"+item.getMoreD2()+"' ";	
					}
					
					if (isNotNullOrEmpty(item.getMoreD1()) && !isNotNullOrEmpty(item.getMoreD2())){   //输入第一个值
						sql += "and A."+item.getConditionD()+" >= '"+item.getMoreD1()+"' ";	
					}
					
					if (!isNotNullOrEmpty(item.getMoreD1()) && isNotNullOrEmpty(item.getMoreD2())){   //输入第二个值
						sql += "and A."+item.getConditionD()+" <= '"+item.getMoreD2()+"' ";	
					}
				}
				
				if(item.getConditionD().equals("Z13") || item.getConditionD().equals("Z18") || item.getConditionD().equals("Z21") || item.getConditionD().equals("fetus_num")){  //判断是否是T13  T18  T21  下拉列表查询
					if(item.getConditionDOpt().equals("T13") || item.getConditionDOpt().equals("T18") || item.getConditionDOpt().equals("T21") || item.getConditionDOpt().equals("low_risk") || item.getConditionDOpt().equals("grey")  ){
						if(item.getConditionDOpt().equals("T13") || item.getConditionDOpt().equals("T18") || item.getConditionDOpt().equals("T21")){
							sql += "and A."+item.getConditionD()+" >= 4 ";;
						}
						if(item.getConditionDOpt().equals("low_risk")){
							sql += "and A."+item.getConditionD()+" <= 3 ";;
						}
						if(item.getConditionDOpt().equals("grey")){
							sql += "and A."+item.getConditionD()+" >= 3  and A."+item.getConditionD()+"  < 4 ";;
						}
					}else{
						sql += "and A."+item.getConditionD()+" = '"+item.getConditionDOpt()+"' ";
					}
				}
				
				if(!item.getConditionD().equals("recieve_date") && !item.getConditionD().equals("sampling_date") && !item.getConditionD().equals("last_period") && !item.getConditionD().equals("age") && !item.getConditionD().equals("ges_week")&& !item.getConditionD().equals("NT") && !item.getConditionD().equals("raw_data") && !item.getConditionD().equals("Q30") && !item.getConditionD().equals("align_rate") && !item.getConditionD().equals("dup_rate") && !item.getConditionD().equals("ur_num") &&  !item.getConditionD().equals("GC")&&  !item.getConditionD().equals("Z13") && !item.getConditionD().equals("Z18") && !item.getConditionD().equals("Z21")){  //判断是否精确查询
					sql += "and A."+item.getConditionD()+" = '"+item.getConditionDName()+"' ";
				}
			}
			sql += " ORDER BY substring_index(A.index_num,'B',-1)+0 ASC ";
		}*/
		return sql;
	}
	
	public static boolean isNotNullOrEmpty(String s){
		if (null==s){
			return false;
		}
		if (s.trim().equals("")){
			return false;
		}
		return true;
	} 
}
