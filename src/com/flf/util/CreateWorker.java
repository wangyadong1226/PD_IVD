package com.flf.util;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

/**
 * 多线程生成PDF报告
 * @author limingmeng
 *
 */
public class CreateWorker{
/*
//public class CreateWorker implements Callable<String> {/*
	
	//private static ReportGenerationService reportGenerationService = ApplicationContextHelper.getBean(ReportGenerationService.class);
	
	private TbSample tbSample;
	
	private String prefix;
	
	private Integer sign;
	private String isSuper;
	
	public CreateWorker(TbSample tbSample, String prefix,Integer sign,String isSuper) {
		this.tbSample = tbSample;
		this.prefix = prefix;
		this.sign = sign;
		this.isSuper = isSuper;
	}
	
	public String call() throws Exception {
		return create(tbSample, prefix, sign,isSuper);
	}

	*//**
	 * 真正产生报告方法
	 * @param sampleAnalysisInfo
	 * @param prefix 
	 * @return
	 *//*
	public static String create(TbSample tbSample, String prefix,Integer sign,String isSuper) {
		String sampleCode = tbSample.getSampleId();
		String res = null;
		PdfReader reader = null;
		try {
			//获取模板路径
	        String uploadFileSavePath = prefix;
	        String modelsvalue = tbSample.getReportTemplate();
	        if(null != isSuper && isSuper.equals("1")){
	        	modelsvalue ="tonggyong01.pdf";
	        }else if(null != isSuper && isSuper.equals("2")){
	        	modelsvalue ="supplement.pdf";
			}
	        //String modelsvalue = "jiangsusheng.pdf";//
	        reader = new PdfReader(uploadFileSavePath+"/"+modelsvalue);
	        //reader = new PdfReader(uploadFileSavePath);
			SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
			String times = simp.format(new Date()).trim();	
			String gravidaName = tbSample.getName();
			
			//拼接一个生成名称
			String deskFileName = times +"_"+sampleCode+"_"+gravidaName+"_"+"无创产前DNA检测报告";
			res = deskFileName + ".pdf";
			String root = uploadFileSavePath + File.separator;
			if (!new File(root).exists())
				new File(root).mkdirs();
			File deskFile = new File(root, deskFileName + ".pdf");
			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(deskFile));
			// 取出报表模板中的所有字段 
			AcroFields form = stamp.getAcroFields();
			form.setField("hospital", "hospital");
			// 为字段赋值,注意字段名称是区分大小写的 
			if(modelsvalue.equals("jiangsusheng.pdf")){
				jSrnHospital(tbSample, form, modelsvalue,stamp,prefix,sign);
			}else if(isSuper.equals("1")){//工程师版
				GeneralTemplateWithSuper(tbSample, form);
			}else if(isSuper.equals("2")){//补充报告
				GeneralTemplateWithSupplement(tbSample, form);
			}else{
				//通用模板
				GeneralTemplate(tbSample, form);
			}
			stamp.setFormFlattening(true);
			//必须要调用这个，否则文档不会生成的 
			stamp.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
		return res;
	}

	*//**
	 * 日期格式化
	 * @param str
	 * @param strFormat
	 * @param parseFormat
	 * @return
	 *//*
	public static String parseDateToStr(String str, String strFormat, String parseFormat){
		try {
			DateFormat dateFormat = new SimpleDateFormat(strFormat);
			Date date = dateFormat.parse(str);
			return new SimpleDateFormat(parseFormat).format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	*//**
	 * 生成报告日期 
	 * @param form
	 * @throws IOException
	 * @throws DocumentException
	 *//*
	private static void GenerateReportDate(TbSample tbSample,AcroFields form) throws IOException,DocumentException {
		String reportDate = tbSample.getReportDate();
		if(null==reportDate || reportDate.equals("")){
			reportDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		}
		String s_nd = reportDate.substring(0, 4); // 年份
		String s_yf = reportDate.substring(5, 7); // 月份
		String s_rq = reportDate.substring(8, 10); // 日期
		form.setField("s_nd",s_nd);
		form.setField("s_yf",s_yf);
		form.setField("s_rq",s_rq);
	}
	
	
	
	*//**
	 * 江苏省人民医院
	 * @param reportGeneration
	 * @param form
	 * @throws IOException
	 * @throws DocumentException
	 *//*
	private static void jSrnHospital(TbSample tbSample,AcroFields form,String modelsvalue,PdfStamper stamp,String prefix,Integer sign) throws IOException, DocumentException {
		form.setField("name",tbSample.getName()==null ? "":tbSample.getName());//姓名 
		form.setField("hospitalNum",tbSample.getOutpatientNum()==null ? "":tbSample.getOutpatientNum());//门诊号
		form.setField("hospital", tbSample.getUnitFrom()==null ? "":tbSample.getUnitFrom());	//送检单位
		form.setField("resultDescription", tbSample.getSampleId()==null ? "":tbSample.getSampleId());	
		form.setField("sampleNum", tbSample.getSampleId());
		form.setField("age", tbSample.getAge()==null ? "":tbSample.getAge().toString() );
		form.setField("pregnantPeriod", tbSample.getGesWeek()==null ? "":tbSample.getGesWeek());//孕周
		form.setField("lastMenstruation", tbSample.getLastPeriod()==null ? "":tbSample.getLastPeriod()); //末次月经
		form.setField("pregnantTimes", tbSample.getPregHistory()!=null && tbSample.getPregHistory().length()==6 ? tbSample.getPregHistory().substring(1, 2):"");//孕产史 
		form.setField("bearTimes",  tbSample.getPregHistory()!=null && tbSample.getPregHistory().length()==6 ? tbSample.getPregHistory().substring(4, 5):""); //孕产史  
		form.setField("submitdoctor", tbSample.getDoctorFrom()==null ? "":tbSample.getDoctorFrom());//送检医生
		form.setField("sampleType", tbSample.getSampleType()==null ? "":tbSample.getSampleType());//样本类型
		form.setField("submitDate", tbSample.getSamplingDate()==null ? "":tbSample.getSamplingDate()); //采样日期 
		form.setField("receiveDate", tbSample.getRecieveDate()==null ? "": tbSample.getRecieveDate()); //接收日期
		form.setField("reportDate", tbSample.getReportDate()==null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString():tbSample.getReportDate()); //报告日期
		String index13 = tbSample.getTbRes13();
		String index18 = tbSample.getTbRes18();
		String index21 = tbSample.getTbRes21();
		String tempHint = " ";
		if(index13.equals("low_risk")){
			tempHint = "低风险";
		}else if(index13.equals("grey")){
			tempHint = "灰区";
		}else {
			tempHint =index13;
		}
		form.setField("13fx", tempHint);
		if(index18.equals("low_risk")){
			tempHint = "低风险";
		}else if(index18.equals("grey")){
			tempHint = "灰区";
		}else {
			tempHint =index18;
		}
		form.setField("18fx", tempHint);
		if(index21.equals("low_risk")){
			tempHint = "低风险";
		}else if(index21.equals("grey")){
			tempHint = "灰区";
		}else {
			tempHint =index21;
		}
		form.setField("21fx", tempHint);
		form.setField("13ti", tbSample.getZ13()==null ? "":tbSample.getZ13().toString());
		form.setField("18ti", tbSample.getZ18()==null ? "":tbSample.getZ18().toString());
		form.setField("21ti", tbSample.getZ21()==null ? "":tbSample.getZ21().toString());
		//==1审核时预览报告
		if(sign!=1){
			//添加  报告人 复核人  审核人
		    Image gif = Image.getInstance(prefix+"/jangsu_sign.jpg");
		    //gif.setDpi(10, 10);
		    gif.setBorderWidth(600);
		    gif.scaleAbsolute(40, 25); 
		    gif.setAbsolutePosition(143, 95);
		    PdfContentByte over = stamp.getOverContent(1);
		    over.addImage(gif);
		}
	}
	*//**
	 * 通用模板填充
	 * @param tbSample
	 * @param form
	 * @throws IOException
	 * @throws DocumentException
	 *//*
	private static void GeneralTemplate(TbSample tbSample, AcroFields form) throws IOException, DocumentException {
		form.setField("hospital", tbSample.getUnitFrom()==null? "":tbSample.getUnitFrom());//送检单位
		form.setField("name", tbSample.getName()==null? "":tbSample.getName());//孕妇姓名
		form.setField("sampleNum", "AB"+tbSample.getSampleId());//样本编号
		form.setField("age", tbSample.getAge()==null? "":tbSample.getAge().toString());//孕妇年龄
		form.setField("lastMenstruation", tbSample.getLastPeriod()==null? "":tbSample.getLastPeriod());//末次月经
		form.setField("sampleType", tbSample.getSampleType()==null? "":tbSample.getSampleType());//样本类型
		form.setField("hospitalNum", tbSample.getOutpatientNum()==null? "":tbSample.getOutpatientNum());//住院门诊号
		form.setField("clinicalDiagnosis",  tbSample.getDiagnosis()==null? "":tbSample.getDiagnosis());//临床诊断
		//待定
		form.setField("pregnantTimes", tbSample.getPregHistory()!=null && tbSample.getPregHistory().length()==6 ? tbSample.getPregHistory().substring(1, 2):"");//孕产史 
		form.setField("bearTimes",  tbSample.getPregHistory()!=null && tbSample.getPregHistory().length()==6 ? tbSample.getPregHistory().substring(4, 5):""); //孕产史  
		form.setField("ivfGestation", tbSample.getIvfGestation()==null? "":tbSample.getIvfGestation());//妊娠类型
		form.setField("pregnantPeriod", tbSample.getGesWeek()==null? "":tbSample.getGesWeek());//孕周
		form.setField("simplePolyembryony", tbSample.getFetusNum()==null? "":tbSample.getFetusNum());//单胎/多胎
		form.setField("submitDate", tbSample.getSamplingDate()==null? "":tbSample.getSamplingDate());//采样日期
		form.setField("submitdoctor", tbSample.getDoctorFrom()==null? "":tbSample.getDoctorFrom());//送检医生
		
		form.setField("receiveDate", tbSample.getRecieveDate()==null? "":tbSample.getRecieveDate());//接收日期
		form.setField("21ti", tbSample.getZ21Name()==null ? "":tbSample.getZ21Name().toString());
		form.setField("18ti", tbSample.getZ18Name()==null ? "":tbSample.getZ18Name().toString());
		form.setField("13ti", tbSample.getZ13Name()==null ? "":tbSample.getZ13Name().toString());
		form.setField("13fx", tbSample.getT13Name()==null ? "":tbSample.getT13Name());
		form.setField("18fx", tbSample.getT18Name()==null ? "":tbSample.getT18Name());
		form.setField("21fx", tbSample.getT21Name()==null ? "":tbSample.getT21Name());
		//生成报告日期
		GenerateReportDate(tbSample, form);
	}
	*//**
	 * 通用模板填充-工程师-补充报告
	 * @param tbSample
	 * @param form
	 * @throws IOException
	 * @throws DocumentException
	 *//*
	private static void GeneralTemplateWithSupplement(TbSample tbSample, AcroFields form) throws IOException, DocumentException {
		form.setField("name", tbSample.getName()==null? "":tbSample.getName());//孕妇姓名
		form.setField("sampleNum", tbSample.getSampleId());//样本编号
		form.setField("outpatientNum", tbSample.getOutpatientNum()==null? "":tbSample.getOutpatientNum());//住院门诊号
		form.setField("age", tbSample.getAge()==null? "":tbSample.getAge().toString());//孕妇年龄
		form.setField("ivfGestation", tbSample.getIvfGestation()==null? "":tbSample.getIvfGestation());//妊娠类型
		form.setField("sampleType", tbSample.getSampleType()==null? "":tbSample.getSampleType());//样本类型
		form.setField("gesWeek", tbSample.getGesWeek()==null? "":tbSample.getGesWeek());//孕周
		form.setField("lastMenstruation", tbSample.getLastPeriod()==null? "":tbSample.getLastPeriod());//末次月经
		form.setField("pregHistory", tbSample.getPregHistory()==null? "":tbSample.getPregHistory());//孕产史 
		form.setField("samplingDate", tbSample.getSamplingDate()==null? "":tbSample.getSamplingDate());//采样日期
		form.setField("submitdoctor", tbSample.getDoctorFrom()==null? "":tbSample.getDoctorFrom());//送检医生
		form.setField("sampleType", tbSample.getSampleType()==null ? "":tbSample.getSampleType());
		form.setField("state", "正常");
		form.setField("receiveDate", tbSample.getRecieveDate()==null? "":tbSample.getRecieveDate());//接收日期
		form.setField("diagnosis", tbSample.getDiagnosis()==null ? "":tbSample.getDiagnosis());
		form.setField("doctorFrom", tbSample.getDoctorFrom()==null ? "":tbSample.getDoctorFrom());
		form.setField("fetusNum", tbSample.getFetusNum()==null ? "":tbSample.getFetusNum());
		form.setField("tipsName", tbSample.getTipsName()==null ? "":tbSample.getTipsName());
		form.setField("tips", tbSample.getTipsTotal()==null ? "":tbSample.getTipsTotal());
		form.setField("supplementDate",new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());
	}
	*//**
	 * 通用模板填充-工程师版
	 * @param tbSample
	 * @param form
	 * @throws IOException
	 * @throws DocumentException
	 *//*
	private static void GeneralTemplateWithSuper(TbSample tbSample, AcroFields form) throws IOException, DocumentException {
		form.setField("hospital", tbSample.getUnitFrom()==null? "":tbSample.getUnitFrom());//送检单位
		form.setField("name", tbSample.getName()==null? "":tbSample.getName());//孕妇姓名
		form.setField("sampleNum", tbSample.getSampleId());//样本编号
		form.setField("age", tbSample.getAge()==null? "":tbSample.getAge().toString());//孕妇年龄
		form.setField("lastMenstruation", tbSample.getLastPeriod()==null? "":tbSample.getLastPeriod());//末次月经
		form.setField("sampleType", tbSample.getSampleType()==null? "":tbSample.getSampleType());//样本类型
		form.setField("hospitalNum", tbSample.getOutpatientNum()==null? "":tbSample.getOutpatientNum());//住院门诊号
		form.setField("clinicalDiagnosis",  tbSample.getDiagnosis()==null? "":tbSample.getDiagnosis());//临床诊断
		//待定
		form.setField("pregnantTimes", tbSample.getPregHistory()!=null && tbSample.getPregHistory().length()==6 ? tbSample.getPregHistory().substring(1, 2):"");//孕产史 
		form.setField("bearTimes",  tbSample.getPregHistory()!=null && tbSample.getPregHistory().length()==6 ? tbSample.getPregHistory().substring(4, 5):""); //孕产史  
		form.setField("ivfGestation", tbSample.getIvfGestation()==null? "":tbSample.getIvfGestation());//妊娠类型
		form.setField("pregnantPeriod", tbSample.getGesWeek()==null? "":tbSample.getGesWeek());//孕周
		form.setField("simplePolyembryony", tbSample.getFetusNum()==null? "":tbSample.getFetusNum());//单胎/多胎
		form.setField("submitDate", tbSample.getSamplingDate()==null? "":tbSample.getSamplingDate());//采样日期
		form.setField("submitdoctor", tbSample.getDoctorFrom()==null? "":tbSample.getDoctorFrom());//送检医生
		form.setField("bloodNum", tbSample.getBloodId()==null ? "":tbSample.getBloodId());
		form.setField("sampleId", tbSample.getSampleId()==null ? "":tbSample.getSampleId());
		form.setField("state", "正常");
		form.setField("diagnosis", tbSample.getDiagnosis()==null ? "":tbSample.getDiagnosis());
		form.setField("hospitalCode", tbSample.getHospitalCode()==null ? "":tbSample.getHospitalCode());
		form.setField("fetusNum", tbSample.getFetusNum()==null ? "":tbSample.getFetusNum());
		form.setField("unitFrom", tbSample.getUnitFrom()==null ? "":tbSample.getUnitFrom());
		form.setField("receiveDate", tbSample.getRecieveDate()==null? "":tbSample.getRecieveDate());//接收日期
		form.setField("21ti", tbSample.getZ21New()==null ? "":tbSample.getZ21New().toString());
		form.setField("18ti", tbSample.getZ18New()==null ? "":tbSample.getZ18New().toString());
		form.setField("13ti", tbSample.getZ13New()==null ? "":tbSample.getZ13New().toString());
		form.setField("13fx", tbSample.getTbRes13New()==null ? "":tbSample.getTbRes13New());
		form.setField("18fx", tbSample.getTbRes18New()==null ? "":tbSample.getTbRes18New());
		form.setField("21fx", tbSample.getTbRes21New()==null ? "":tbSample.getTbRes21New());
		//生成报告日期
		GenerateReportDate(tbSample, form);
	}*/
}
