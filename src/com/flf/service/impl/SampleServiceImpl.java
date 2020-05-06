package com.flf.service.impl;

import java.io.IOException;
import java.util.List;

import com.flf.entity.TbSample;
import com.flf.mapper.BatchMapper;
import com.flf.mapper.SampleMapper;
import com.flf.service.SampleService;

public class SampleServiceImpl implements SampleService {
	private static final int DEFAULT_BUFFER_SIZE = 16 * 1024;
	private static final int DEFAULT_PARALLELISM = 5;

	private SampleMapper sampleMapper;
	private BatchMapper batchMapper;

	public BatchMapper getBatchMapper() {
		return batchMapper;
	}

	public void setBatchMapper(BatchMapper batchMapper) {
		this.batchMapper = batchMapper;
	}

	public SampleMapper getSampleMapper() {
		return sampleMapper;
	}

	public void setSampleMapper(SampleMapper sampleMapper) {
		this.sampleMapper = sampleMapper;
	}


	public void deleteSample(Long batchId) {
		// TODO Auto-generated method stub
		sampleMapper.deleteSample(batchId);
	}

	public void insertSample(TbSample sample) {
		sampleMapper.insertSample(sample);
	}

	public TbSample getSampleById(Long tbSampleId) {
		// TODO Auto-generated method stub
		return sampleMapper.getSampleById(tbSampleId);
	}

	public List<TbSample> getSampleByBatchId(Long batchId) {
		// TODO Auto-generated method stub
		return sampleMapper.getSampleByBatchId(batchId);
	}

	public List<TbSample> listSample(TbSample sample) {
		// TODO Auto-generated method stub
		return sampleMapper.listSample(sample);
	}

	public void updateStatus(TbSample sample) {
		// TODO Auto-generated method stub
		sampleMapper.updateStatus(sample);
	}

	public TbSample getSampleBysampleId(String sampleId) {
		return sampleMapper.getSampleBysampleId(sampleId);
	}

	public List<TbSample> getSampleIds(List<String> sampleId) {
		return sampleMapper.getSampleIds(sampleId);
	}

	public List<TbSample> listPageSampleByBatchId(TbSample sample) {
		// TODO Auto-generated method stub
		return sampleMapper.listPageSampleByBatchId(sample);
	}

	/*public void updateReportAuditState(TbSample tbSample) {
		// TODO Auto-generated method stub
		sampleMapper.updateReportAuditState(tbSample);
		TbSample sample= new TbSample();
		sample.setSql(" and (tips='0' or tips='4') and status = '2'");//低风险 ,已分析
		sample.setBatchId(sampleMapper.getSampleById(tbSample.getTbSampleId()).getBatchId());
		List<TbSample> list = sampleMapper.listSampleByBatchId(sample);
		boolean flag = true;
		int count=0;
		for(TbSample sampleNew :list){
			if(sampleNew.getAuditStatus().equals("0")){//未审核
				flag = false;
				break;
			}
			count++;
		}
		TbBatch batch=batchMapper.getBatchById(new Long(sample.getBatchId()));
		if(flag){
			batch.setAuditStatus("2");//已审核
			batchMapper.updateReportAuditState(batch);
		}else if(batch.getAuditStatus().equals("0")){
			batch.setAuditStatus("1");//部分审核
			batchMapper.updateReportAuditState(batch);
		}
	}

	public List<TbSample> listSampleByBatchId(TbSample sample) {
		// TODO Auto-generated method stub
		return sampleMapper.listSampleByBatchId(sample);
	}

	public void updateGeneratePdf(List<TbSample> sampleList, String prefix,HttpServletResponse response,HttpSession session) throws IOException {
		// TODO Auto-generated method stub
		List<String> pdfnames = new ArrayList<String>();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String isSuper = session.getAttribute(Const.SESSION_USER_ISSUPER).toString();
		
		try {
			if (sampleList.size() < DEFAULT_PARALLELISM) {
				for (int i = 0; i < sampleList.size(); i++) {
					TbSample tbSample = sampleList.get(i);
					pdfnames.add(CreateWorker.create(tbSample, prefix,0,isSuper));
				}
			} else {
				List<Future<String>> tmpList = new ArrayList<Future<String>>();
				ExecutorService multiCreateExector = Executors.newFixedThreadPool(DEFAULT_PARALLELISM);
		        for (TbSample tbSample : sampleList) {
					tmpList.add(multiCreateExector.submit(new CreateWorker(tbSample, prefix,0,isSuper)));
				}
		        //用于关闭启动线程，如果不调用该语句，jvm不会关闭
		        multiCreateExector.shutdown();
		        
		        //用于等待子线程结束，再继续执行下面的代码，这里是1天
		        multiCreateExector.awaitTermination(1, TimeUnit.DAYS);
				for(int j = 0; j < tmpList.size(); j++) {
					if (tmpList.get(j).isDone()) {
						pdfnames.add(tmpList.get(j).get());
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if (pdfnames.size() > 0) {
			zipAndDownload(pdfnames, response);
		}
		for(TbSample s:sampleList){
			sampleMapper.updateDownLoadStatus(s);
			DownLoadDetail detail=new DownLoadDetail();
			detail.setDownloader(user.getUsername());
			detail.setDownloadTime(new Date());
			detail.setSampleId(s.getTbSampleId());
			downLoadMapper.insertDownLoad(detail);
		}
		TbSample sample=new TbSample();
		sample.setBatchId(sampleList.get(0).getBatchId());//已审核
		List<TbSample> list = sampleMapper.listSampleByBatchId(sample);
		boolean flag = true;
		for(TbSample sampleNew :list){
			if(sampleNew.getIfControl() == 0 && sampleNew.getDownloadStatus().equals("0")){//未下载
				flag = false;
				break;
			}
		}
		TbBatch batch = batchMapper.getBatchById(new Long(sample.getBatchId()));
		if(batch.getDownloadStatus().equals("0") && !flag){
			batch.setDownloadStatus("1");//部分下载
			batchMapper.updateDownloadStatus(batch);
		}
		if(flag){
			batch.setDownloadStatus("2");//全部下载
			batchMapper.updateDownloadStatus(batch);
		}
	}
	
	
	public void generateSuPdf(List<TbSample> sampleList, String prefix,HttpServletResponse response,HttpSession session,String type) throws IOException {
				List<String> pdfnames = new ArrayList<String>();
				User user = (User)session.getAttribute(Const.SESSION_USER);
				
				try {
					if (sampleList.size() < DEFAULT_PARALLELISM) {
						for (int i = 0; i < sampleList.size(); i++) {
							TbSample tbSample = sampleList.get(i);
							pdfnames.add(CreateWorker.create(tbSample, prefix,0,type));
						}
					} else {
						List<Future<String>> tmpList = new ArrayList<Future<String>>();
						ExecutorService multiCreateExector = Executors.newFixedThreadPool(DEFAULT_PARALLELISM);
				        for (TbSample tbSample : sampleList) {
							tmpList.add(multiCreateExector.submit(new CreateWorker(tbSample, prefix,0,type)));
						}
				        //用于关闭启动线程，如果不调用该语句，jvm不会关闭
				        multiCreateExector.shutdown();
				        
				        //用于等待子线程结束，再继续执行下面的代码，这里是1天
				        multiCreateExector.awaitTermination(1, TimeUnit.DAYS);
						for(int j = 0; j < tmpList.size(); j++) {
							if (tmpList.get(j).isDone()) {
								pdfnames.add(tmpList.get(j).get());
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				if (pdfnames.size() > 0) {
					zipAndDownload(pdfnames, response);
				}
				for(TbSample s:sampleList){
					//sampleMapper.updateSuDownLoadStatus(s);
					DownLoadDetail detail=new DownLoadDetail();
					detail.setDownloader(user.getUsername());
					detail.setDownloadTime(new Date());
					detail.setSampleId(s.getTbSampleId());
					//downLoadMapper.insertDownLoad(detail);
				}
	}
	*//**
	 * @param pdfnames
	 * @throws IOException 
	 *//*
	private void zipAndDownload(List<String> pdfnames, HttpServletResponse response) throws IOException {
		HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String path = httpRequest.getSession().getServletContext().getRealPath("/report");
		if (pdfnames.size() == 1) {
			for (String name : pdfnames) {
				File file = new File(path, name);  
				response.setHeader( "Content-Disposition", "attachment;filename=" + new String(name.getBytes("gb2312"), "ISO8859-1" ) );
				ServletOutputStream outputStream = response.getOutputStream();
				FileInputStream fis = new FileInputStream(file);
				
				int len = 0;
				byte[] b = new byte[DEFAULT_BUFFER_SIZE];
				while ((len = fis.read(b)) != -1) {
					outputStream.write(b, 0, len);
				}
				fis.close();
			}
		} else {
			SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
			String times = simp.format(new Date()).trim();	
			String tmpFileName = times + ".zip";
			String strZipPath = path + tmpFileName;
			
			ZipOutputStream out = null;
			//TODO 待对比时间 （多线程但是同步   VS 单线程）,如果多线程同步效果不好要删除common-compress包
			try {
				out = new ZipOutputStream(new FileOutputStream(strZipPath));
				if (pdfnames.size() < DEFAULT_PARALLELISM) {
					for (int i = 0; i < pdfnames.size(); i++) {
						String name =  pdfnames.get(i);
						zip(name, path, out);
					}
				} else {//多线程压缩
					ExecutorService multiZipExector = Executors.newFixedThreadPool(DEFAULT_PARALLELISM);
			        for (int i = 0; i < pdfnames.size(); i++) {
			        	String name =  pdfnames.get(i);
			        	multiZipExector.submit(new ZipWorker(name, path, out));
					}
			        multiZipExector.shutdown();
			        multiZipExector.awaitTermination(1, TimeUnit.DAYS);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				out.close();
			}
			
			*//*out = new ZipOutputStream(new FileOutputStream(strZipPath));
			for (int i = 0; i < pdfnames.size(); i++) {
				String name =  pdfnames.get(i);
				zip(name, path, out);
			}
			out.close();*//*
			
			//下载最终压缩文件
			try {
				String pathZip = strZipPath;
				File file = new File(pathZip);
				if (file.exists()) {
					InputStream ins = new FileInputStream(pathZip);
					BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
					OutputStream outs = response.getOutputStream();// 获取文件输出IO流
					BufferedOutputStream bouts = new BufferedOutputStream(outs);
					response.setContentType("application/x-download");// 设置response内容的类型
					response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(tmpFileName,"UTF-8"));// 设置头部信息
					int bytesRead = 0;
					byte[] buffer1 = new byte[DEFAULT_BUFFER_SIZE];
					// 开始向网络传输文件流
					while ((bytesRead = bins.read(buffer1, 0, DEFAULT_BUFFER_SIZE)) != -1) {
						bouts.write(buffer1, 0, bytesRead);
					}
					bouts.flush();// 这里一定要调用flush()方法
					ins.close();
					bins.close();
					outs.close();
					bouts.close();
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	*//**
	 * 把单个PDF压缩进OUT,得同步
	 * @param pdfnames
	 * @param path
	 * @param out
	 *//*
	public static synchronized void zip(String name, String path, ZipOutputStream out) {
		
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];	
		File file = new File(path, name);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			out.putNextEntry(new ZipEntry(file.getName()));
			// 设置压缩文件内的字符编码，不然会变成乱码
			//out.setEncoding("GBK");
			int len;
			// 读入需要下载的文件的内容，打包到zip文件
			while ((len = fis.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.closeEntry();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	*//**
	 * 压缩多线程worker
	 * @author 	youxingyang
	 * @Date	2017-5-25 下午4:25:53
	 *//*
	private static class ZipWorker implements Runnable {
		private String name;
		
		private String path;
		
		private ZipOutputStream out;
		
		public ZipWorker(String name, String path, ZipOutputStream out) {
			this.name = name;
			this.path = path;
			this.out = out;
		}
		
		public void run() {
			zip(name, path, out);
		}
	}
	public List<String> readPdf(List<TbSample> sampleList, String prefix,String type) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<String> pdfnames = new ArrayList<String>();
		try {
			if (sampleList.size() < DEFAULT_PARALLELISM) {
				for (int i = 0; i < sampleList.size(); i++) {
					TbSample tbSample = sampleList.get(i);
					if (null != type  && "2".equals(type)) {
						tbSample = sampleMapper.getSampleById(tbSample.getTbSampleId());
					}
					pdfnames.add(CreateWorker.create(tbSample, prefix,1,type));
				}
			} else {
				List<Future<String>> tmpList = new ArrayList<Future<String>>();
				ExecutorService multiCreateExector = Executors.newFixedThreadPool(DEFAULT_PARALLELISM);
		        for (TbSample tbSample : sampleList) {
		        	if (null != type  && "2".equals(type)) {
						tbSample = sampleMapper.getSampleById(tbSample.getTbSampleId());
					}
					tmpList.add(multiCreateExector.submit(new CreateWorker(tbSample, prefix,1,type)));
				}
		        //用于关闭启动线程，如果不调用该语句，jvm不会关闭
		        multiCreateExector.shutdown();
		        
		        //用于等待子线程结束，再继续执行下面的代码，这里是1天
		        multiCreateExector.awaitTermination(1, TimeUnit.DAYS);
				for(int j = 0; j < tmpList.size(); j++) {
					if (tmpList.get(j).isDone()) {
						pdfnames.add(tmpList.get(j).get());
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return pdfnames;
	}

	public List<DownLoadDetail> getDownloadDetail(Long sampleId) {
		// TODO Auto-generated method stub
		return downLoadMapper.listDownLoad(sampleId);
	}
	*//**
	 * 导出Excel
	 *//*
	public void exportExcel(TbSample sample,String templetePath, OutputStream out,Integer type) throws Exception {
		sample.setDownloadStatus("1");//已下载的样本
		if(Tools.notEmpty(sample.getSampleId())){
			String temp=sample.getSampleId();
			sample.setSampleId(temp.substring(2, temp.length()));
		}
		if(Tools.notEmpty(sample.getsRecieveDate())){
			String temp=sample.getsRecieveDate();
			sample.setsRecieveDate(temp.substring(0,4)+temp.substring(5,7)+temp.substring(8,10));
		}
		if(Tools.notEmpty(sample.geteRecieveDate())){
			String temp=sample.geteRecieveDate();
			sample.seteRecieveDate(temp.substring(0,4)+temp.substring(5,7)+temp.substring(8,10));
		}
		List<TbSample> sampleList = sampleMapper.listSampleByBatchId(sample);
		ExcelUtils excel= new ExcelUtils(templetePath);
		if(sampleList != null && sampleList.size() > 0){
			int rownum =  1;
			String tempHint = " ";
			for (TbSample sample2 : sampleList) {
				excel.setValue(0, rownum, 0, sample2.getOutpatientNum());	//门诊号/住院号
				if(Tools.notEmpty(sample2.getRecieveDate()) && sample2.getRecieveDate().length()==8){
					String year=sample2.getRecieveDate().substring(0, 4);
					String month=sample2.getRecieveDate().substring(4, 6);
					String day=sample2.getRecieveDate().substring(6, 8);
					excel.setValue(0, rownum, 1, year+"-"+month+"-"+day);	//送检日期
				}
				excel.setValue(0, rownum, 2, sample2.getSampleId());	//样本编号
				excel.setValue(0, rownum, 3, sample2.getName()==null? "":sample2.getName());	//孕妇姓名
				excel.setValue(0, rownum, 4, sample2.getReportDate()==null? "":sample2.getReportDate());	//报告生成日期
				excel.setValue(0, rownum, 5, sample2.getZ13()==null? "":sample2.getZ13());	//13-三体风险指数
				if(type == 2){
					if(null != sample2.getTbRes13()){
						if( sample2.getTbRes13().equals("low_risk")){
							tempHint = "低风险";
						}else if(sample2.getTbRes13().equals("grey")){
							tempHint = "灰区";
						}else {
							tempHint =sample2.getTbRes13();
						}
					}else{
						tempHint = "";
					} 
					excel.setValue(0, rownum, 6, tempHint);	//13-三体风险
					if(null != sample2.getTbRes18()){
						if( sample2.getTbRes18().equals("low_risk")){
							tempHint = "低风险";
						}else if(sample2.getTbRes18().equals("grey")){
							tempHint = "灰区";
						}else {
							tempHint =sample2.getTbRes18();
						}
					}else{
						tempHint = "";
					}
					excel.setValue(0, rownum, 8, tempHint);	//18-三体风险
					if(null != sample2.getTbRes21()){
						if( sample2.getTbRes21().equals("low_risk")){
							tempHint = "低风险";
						}else if(sample2.getTbRes21().equals("grey")){
							tempHint = "灰区";
						}else {
							tempHint =sample2.getTbRes21();
						}
					}else{
						tempHint = "";
					}
					excel.setValue(0, rownum, 10, tempHint);	//21-三体风险
				}else {
					excel.setValue(0, rownum, 6, sample2.getT13Name()==null? "":sample2.getT13Name());	//13-三体风险
					excel.setValue(0, rownum, 8, sample2.getT18Name()==null? "":sample2.getT18Name());	//18-三体风险
					excel.setValue(0, rownum, 10,sample2.getT21Name()==null? "":sample2.getT21Name());	//21-三体风险
				}
				excel.setValue(0, rownum, 7, sample2.getZ18()==null? "":sample2.getZ18());	//18-三体风险指数
				excel.setValue(0, rownum, 9, sample2.getZ21()==null? "":sample2.getZ21());	//21-三体风险指数
				rownum++;
			}
		}
		excel.writer(out);
	}
	public String insertClinicalEditDetail(ClinicalEditDetail clinical,HttpSession session) {
		// TODO Auto-generated method stub
		TbSample sample=sampleMapper.getSampleBysampleId(clinical.getSampleId().toString());//原样本信息
		TbResult res = clinicalEditMapper.getResultBySampleId(sample.getTbSampleId());
		TbResult result = new TbResult();
		result.setSampleId(sample.getTbSampleId());
//		if(null != clinical.getName() && !"".equals(clinical.getName())&& clinical.getName().equals(sample.getName())&& clinical.getAge().equals(sample.getAge())
//				&& clinical.getOutpatientNum().equals(sample.getOutpatientNum())
//				&& clinical.getGesWeek().equals(sample.getGesWeek())
//				&& clinical.getSampleType().equals(sample.getSampleType())
//				&& clinical.getLastPeriod().equals(sample.getLastPeriod())
//				&& clinical.getPregHistory().equals(sample.getPregHistory())
//				&& clinical.getIvfGestation().equals(sample.getIvfGestation())
//				&& clinical.getFetusNum().equals(sample.getFetusNum())		
//				&& clinical.getSamplingDate().equals(sample.getSamplingDate())
//				&& clinical.getDiagnosis().equals(sample.getDiagnosis())
//				&& clinical.getDoctorFrom().equals(sample.getDoctorFrom())
//				&& clinical.getRecieveDate().equals(sample.getRecieveDate())
//		){
//			return "fail";
//		}
		if(null != clinical.getName() && !"".equals(clinical.getName())&& !clinical.getName().equals(sample.getName())){
			clinical.setNameOld(sample.getName());
			sample.setName(clinical.getName());
		}else if (null == clinical.getName() || "".equals(clinical.getName())) {
			clinical.setName("");
			sample.setName(clinical.getName());
		}else {
			clinical.setName("");
		}
		if(null != clinical.getAge() && !"".equals(clinical.getAge())&& !clinical.getAge().equals(sample.getAge())){
			clinical.setAgeOld(sample.getAge());
			sample.setAge(clinical.getAge());
		}else if (null == clinical.getAge() || "".equals(clinical.getAge())) {
			clinical.setAge(null);
			sample.setAge(clinical.getAge());
		}else {
			clinical.setAge(null);
		}
		if(null != clinical.getOutpatientNum() && !"".equals(clinical.getOutpatientNum())&& !clinical.getOutpatientNum().equals(sample.getOutpatientNum())){
			clinical.setOutpatientNumOld(sample.getOutpatientNum());
			sample.setOutpatientNum(clinical.getOutpatientNum());
		}else if (null == clinical.getOutpatientNum() || "".equals(clinical.getOutpatientNum())) {
			clinical.setOutpatientNum("");
			sample.setOutpatientNum(clinical.getOutpatientNum());
		}else {
			clinical.setOutpatientNum("");
		}
		if(null !=clinical.getGesWeek() && !"".equals(clinical.getGesWeek())&& !clinical.getGesWeek().equals(sample.getGesWeek())){
			clinical.setGesWeekOld(sample.getGesWeek());
			sample.setGesWeek(clinical.getGesWeek());
		}else if (null == clinical.getGesWeek() || "".equals(clinical.getGesWeek())) {
			clinical.setGesWeek("");
			sample.setGesWeek(clinical.getGesWeek());
		}else {
			clinical.setGesWeek("");
		}
		if(null != clinical.getSampleType() && !"".equals(clinical.getSampleType())&& !clinical.getSampleType().equals(sample.getSampleType())){
			clinical.setSampleTypeOld(sample.getSampleType());
			sample.setSampleType(clinical.getSampleType());
		}else if (null == clinical.getSampleType() || "".equals(clinical.getSampleType())) {
			clinical.setSampleType("");
			sample.setSampleType(clinical.getSampleType());
		}else {
			clinical.setSampleType("");
		}
		if(null != clinical.getLastPeriod() && !"".equals(clinical.getLastPeriod())&& !clinical.getLastPeriod().equals(sample.getLastPeriod())){
			clinical.setLastPeriodOld(sample.getLastPeriod());
			sample.setLastPeriod(clinical.getLastPeriod());
		}else if (null == clinical.getLastPeriod() || "".equals(clinical.getLastPeriod())) {
			clinical.setLastPeriod("");
			sample.setLastPeriod(clinical.getLastPeriod());
		}else {
			clinical.setLastPeriod("");
		}
		if(null != clinical.getPregHistory() && !"".equals(clinical.getPregHistory())&& !clinical.getPregHistory().equals(sample.getPregHistory())){
			clinical.setPregHistoryOld(sample.getPregHistory());
			sample.setPregHistory(clinical.getPregHistory());
		}else if (null == clinical.getPregHistory() || "".equals(clinical.getPregHistory())) {
			clinical.setPregHistory("");
			sample.setPregHistory(clinical.getPregHistory());
		}else {
			clinical.setPregHistory("");
		}
		if(null != clinical.getIvfGestation() &&!"".equals(clinical.getIvfGestation())&& !clinical.getIvfGestation().equals(sample.getIvfGestation())){
			clinical.setIvfGestationOld(sample.getIvfGestation());
			sample.setIvfGestation(clinical.getIvfGestation());
		}else if(null == clinical.getIvfGestation() || "".equals(clinical.getIvfGestation())){
			clinical.setIvfGestation("");
			sample.setIvfGestation(clinical.getIvfGestation());
		}else {
			clinical.setIvfGestation("");
		}
		if(null != clinical.getFetusNum() && !"".equals(clinical.getFetusNum())&& !clinical.getFetusNum().equals(sample.getFetusNum())){
			clinical.setFetusNumOld(sample.getFetusNum());
			sample.setFetusNum(clinical.getFetusNum());
		}else if(null == clinical.getFetusNum() || "".equals(clinical.getFetusNum())){
			clinical.setFetusNum("");
			sample.setFetusNum(clinical.getFetusNum());
		}else {
			clinical.setFetusNum("");
		}
		if(null != clinical.getSamplingDate() && !"".equals(clinical.getSamplingDate())&& !clinical.getSamplingDate().equals(sample.getSamplingDate())){
			clinical.setSamplingDateOld(sample.getSamplingDate());
			sample.setSamplingDate(clinical.getSamplingDate());
		}else if(null == clinical.getSamplingDate() || "".equals(clinical.getSamplingDate())){
			clinical.setSamplingDate("");
			sample.setSamplingDate(clinical.getSamplingDate());
		}else {
			clinical.setSamplingDate("");
		}
		if(null != clinical.getDiagnosis() && !"".equals(clinical.getDiagnosis())&& !clinical.getDiagnosis().equals(sample.getDiagnosis())){
			clinical.setDiagnosisOld(sample.getDiagnosis());
			sample.setDiagnosis(clinical.getDiagnosis());
		}else if(null == clinical.getDiagnosis() || "".equals(clinical.getDiagnosis())){
			clinical.setDiagnosis("");
			sample.setDiagnosis(clinical.getDiagnosis());
		}else{
			clinical.setDiagnosis("");
			
		}
		if(null != clinical.getDoctorFrom() && !"".equals(clinical.getDoctorFrom())&& !clinical.getDoctorFrom().equals(sample.getDoctorFrom())){
			clinical.setDoctorFromOld(sample.getDoctorFrom());
			sample.setDoctorFrom(clinical.getDoctorFrom());
		}else if(null == clinical.getDoctorFrom() || "".equals(clinical.getDoctorFrom())){
			clinical.setDoctorFrom("");
			sample.setDoctorFrom(clinical.getDoctorFrom());
		}else {
			clinical.setDoctorFrom("");
		}
		if(null != clinical.getRecieveDate() && !"".equals(clinical.getRecieveDate())&& !clinical.getRecieveDate().equals(sample.getRecieveDate())){
			clinical.setRecieveDateOld(sample.getRecieveDate());
			sample.setRecieveDate(clinical.getRecieveDate());
		}else if(null == clinical.getRecieveDate() || "".equals(clinical.getRecieveDate())){
			clinical.setRecieveDate("");
			sample.setRecieveDate(clinical.getRecieveDate());
		}else {
			clinical.setRecieveDate("");
		}
		if(null != clinical.getZ21New() && !clinical.getZ21New().equals(sample.getZ21New())){
			result.setZ21New(clinical.getZ21New());
			sample.setZ21New(clinical.getZ21New());
			clinical.setZ21Old(sample.getZ21New());
		}else {
			clinical.setZ21New(null);
		}
		if(null != clinical.getZ18New() && !clinical.getZ18New().equals(sample.getZ18New())){
			clinical.setZ18Old(sample.getZ18New());
			result.setZ18New(clinical.getZ18New());
			sample.setZ18New(clinical.getZ18New());
		}else {
			clinical.setZ18New(null);
		}
		if(null != clinical.getZ13New() && !clinical.getZ13New().equals(sample.getZ13New())){
			clinical.setZ13Old(sample.getZ13New());
			result.setZ13New(clinical.getZ13New());
			sample.setZ13New(clinical.getZ13New());
		}else {
			clinical.setZ13New(null);
		}
		if(null != clinical.getTbRes21New() && !"".equals(clinical.getTbRes21New())&& !clinical.getTbRes21New().equals(sample.getTbRes21New())){
			clinical.setTbRes21Old(sample.getTbRes21New());
			result.setTbRes21New(clinical.getTbRes21New());
			sample.setTbRes21New(clinical.getTbRes21New());
		}else {
			clinical.setTbRes21New("");
		}
		if(null != clinical.getTbRes18New() && !"".equals(clinical.getTbRes18New())&& !clinical.getTbRes18New().equals(sample.getTbRes18New())){
			clinical.setTbRes18Old(sample.getTbRes18New());
			result.setTbRes18New(clinical.getTbRes18New());
			sample.setTbRes18New(clinical.getTbRes18New());
		}else {
			clinical.setTbRes18New("");
		}
		if(null != clinical.getTbRes13New() && !"".equals(clinical.getTbRes13New())&& !clinical.getTbRes13New().equals(sample.getTbRes13New())){
			clinical.setTbRes13Old(sample.getTbRes13New());
			result.setTbRes13New(clinical.getTbRes13New());
			sample.setTbRes13New(clinical.getTbRes13New());
		}else {
			clinical.setTbRes13New("");
		}
		User user = (User)session.getAttribute(Const.SESSION_USER);
		clinical.setEditor(user.getUsername());
		clinical.setEditTime(new Date());
		clinical.setSampleId(sample.getTbSampleId());
		clinicalEditMapper.insertClinicalEdit(clinical);
		if(null != result.getTbRes21New() || null != result.getTbRes18New() ||  null != result.getTbRes13New()|| null != result.getZ21New() ||  null != result.getZ13New()|| null != result.getZ18New()) {
			result.setLinicaId(clinical.getId());
			clinicalEditMapper.insertResult(result);	
		}
		sampleMapper.updateclinical(sample);
		return "success";
	}

	public List<ClinicalEditDetail> listClinicalEdit(Long sampleId) {
		// TODO Auto-generated method stub
		return clinicalEditMapper.listClinicalEdit(sampleId);
	}

	public List<TbSample> listPageTipsEdit(TbSample sample) {
		// TODO Auto-generated method stub
		return sampleMapper.listPageTipsEdit(sample);
	}

	public TbResult getResultBySampleId(Long tbSampleId) {
		// TODO Auto-generated method stub
		return clinicalEditMapper.getResultBySampleId(tbSampleId);
	}

	public List<TbResult> listResultBySampleId(Long sampleId) {
		// TODO Auto-generated method stub
		return clinicalEditMapper.listResultBySampleId(sampleId);
	}

	public List<TbSample> listPageAuditSample(TbSample sample) {
		// TODO Auto-generated method stub
		return sampleMapper.listPageAuditSample(sample);
	}

	public void downPic(TbSample sample, HttpServletResponse response, HttpServletRequest request) throws Exception {
//		try {
//            String downloadFilename = sample.getLibId()+".zip";//文件的名称
//            downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");//转换中文否则可能会产生乱码
////            String  teString = request.getSession().getServletContext().getRealPath(request.getRequestURI());
//            response.setContentType("application/octet-stream");// 指明response的返回对象是文件流 
//            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名
//            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
//            String path = request.getContextPath();
//            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//            String chr1 = sample.getChr1ImagePath() == null ? "":sample.getChr1ImagePath();
//            String chr2 = sample.getChr2ImagePath() == null ? "":sample.getChr2ImagePath();
//            String chr3 = sample.getChr3ImagePath() == null ? "":sample.getChr3ImagePath();
//            String chr4 = sample.getChr4ImagePath() == null ? "":sample.getChr4ImagePath();
//            String chr5 = sample.getChr5ImagePath() == null ? "":sample.getChr5ImagePath();
//            String chr6 = sample.getChr6ImagePath() == null ? "":sample.getChr6ImagePath();
//            String chr7 = sample.getChr7ImagePath() == null ? "":sample.getChr7ImagePath();
//            String chr8 = sample.getChr8ImagePath() == null ? "":sample.getChr8ImagePath();
//            String chr9 = sample.getChr9ImagePath() == null ? "":sample.getChr9ImagePath();
//            String chr10 = sample.getChr10ImagePath() == null ? "":sample.getChr10ImagePath();
//            String chr11 = sample.getChr11ImagePath() == null ? "":sample.getChr11ImagePath();
//            String chr12 = sample.getChr12ImagePath() == null ? "":sample.getChr12ImagePath();
//            String chr13 = sample.getChr13ImagePath() == null ? "":sample.getChr13ImagePath();
//            String chr14 = sample.getChr14ImagePath() == null ? "":sample.getChr14ImagePath();
//            String chr15 = sample.getChr15ImagePath() == null ? "":sample.getChr15ImagePath();
//            String chr16 = sample.getChr16ImagePath() == null ? "":sample.getChr16ImagePath();
//            String chr17 = sample.getChr17ImagePath() == null ? "":sample.getChr17ImagePath();
//            String chr18 = sample.getChr18ImagePath() == null ? "":sample.getChr18ImagePath();
//            String chr19 = sample.getChr19ImagePath() == null ? "":sample.getChr19ImagePath();
//            String chr20 = sample.getChr20ImagePath() == null ? "":sample.getChr20ImagePath();
//            String chr21 = sample.getChr21ImagePath() == null ? "":sample.getChr21ImagePath();
//            String chr22 = sample.getChr22ImagePath() == null ? "":sample.getChr22ImagePath();
//            String[] files = new String[]{chr1,chr2,chr3,chr4,chr5,chr6,chr7,chr8,
//            		chr9,chr10,chr11,chr12,chr13,chr14,chr15,chr16,chr17,
//            		chr18,chr19,chr20,chr21,chr22};
//            int tem = 0;
//            for (int i=0;i<files.length;i++) {
//            	if (!("").equals(files[i])) {
//            		URL url = new URL(files[i]);
//            		tem = i+1;
//            		zos.putNextEntry(new ZipEntry("染色体"+tem+".png"));
//            		InputStream fis = url.openConnection().getInputStream();   
//            		byte[] buffer = new byte[1024];     
//            		int r = 0;     
//            		while ((r = fis.read(buffer)) != -1) {     
//            			zos.write(buffer, 0, r);     
//            		}     
//            		fis.close();   
//            	}
//              }  
//            zos.flush();     
//            zos.close();
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }  
		
		
		String zipName =  sample.getLibId()+".zip";
        response.setContentType("APPLICATION/OCTET-STREAM");  
        response.setHeader("Content-Disposition","attachment; filename="+zipName);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try {
        	String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        	 String chr1 = sample.getChr1ImagePath() == null ? "":sample.getChr1ImagePath();
             String chr2 = sample.getChr2ImagePath() == null ? "":sample.getChr2ImagePath();
             String chr3 = sample.getChr3ImagePath() == null ? "":sample.getChr3ImagePath();
             String chr4 = sample.getChr4ImagePath() == null ? "":sample.getChr4ImagePath();
             String chr5 = sample.getChr5ImagePath() == null ? "":sample.getChr5ImagePath();
             String chr6 = sample.getChr6ImagePath() == null ? "":sample.getChr6ImagePath();
             String chr7 = sample.getChr7ImagePath() == null ? "":sample.getChr7ImagePath();
             String chr8 = sample.getChr8ImagePath() == null ? "":sample.getChr8ImagePath();
             String chr9 = sample.getChr9ImagePath() == null ? "":sample.getChr9ImagePath();
             String chr10 = sample.getChr10ImagePath() == null ? "":sample.getChr10ImagePath();
             String chr11 = sample.getChr11ImagePath() == null ? "":sample.getChr11ImagePath();
             String chr12 = sample.getChr12ImagePath() == null ? "":sample.getChr12ImagePath();
             String chr13 = sample.getChr13ImagePath() == null ? "":sample.getChr13ImagePath();
             String chr14 = sample.getChr14ImagePath() == null ? "":sample.getChr14ImagePath();
             String chr15 = sample.getChr15ImagePath() == null ? "":sample.getChr15ImagePath();
             String chr16 = sample.getChr16ImagePath() == null ? "":sample.getChr16ImagePath();
             String chr17 = sample.getChr17ImagePath() == null ? "":sample.getChr17ImagePath();
             String chr18 = sample.getChr18ImagePath() == null ? "":sample.getChr18ImagePath();
             String chr19 = sample.getChr19ImagePath() == null ? "":sample.getChr19ImagePath();
             String chr20 = sample.getChr20ImagePath() == null ? "":sample.getChr20ImagePath();
             String chr21 = sample.getChr21ImagePath() == null ? "":sample.getChr21ImagePath();
             String chr22 = sample.getChr22ImagePath() == null ? "":sample.getChr22ImagePath();
             String[] files = new String[]{chr1,chr2,chr3,chr4,chr5,chr6,chr7,chr8,
             		chr9,chr10,chr11,chr12,chr13,chr14,chr15,chr16,chr17,
             		chr18,chr19,chr20,chr21,chr22};
             
             for (int i=0;i<files.length;i++) {
                ZipUtils.doCompress(files[i], out);
                response.flushBuffer();
            }
             
 			//TODO 待对比时间 （多线程但是同步   VS 单线程）,如果多线程同步效果不好要删除common-compress包
			if (files.length< DEFAULT_PARALLELISM) {
				for (int i = 0; i < files.length; i++) {
					String name =  files[i];
					zip(name, path, out);
				}
			} else {//多线程压缩
				ExecutorService multiZipExector = Executors.newFixedThreadPool(DEFAULT_PARALLELISM);
		        for (int i = 0; i <files.length; i++) {
		        	String name = files[i];
		        	multiZipExector.submit(new ZipWorker(name, path, out));
				}
		        multiZipExector.shutdown();
		        multiZipExector.awaitTermination(1, TimeUnit.DAYS);
			}
             
             
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
		
	}

	public List<TbSample> listPageSampleDetail(TbSample sample) {
		// TODO Auto-generated method stub
		return sampleMapper.listPageSampleDetail(sample);
	}

	public String getTips(TbSample tbSample) {
		String temp ="";
		// TODO Auto-generated method stub
		if (tbSample.getIfControl()==0 && null != tbSample.getRes1() && "abnormal".equals(tbSample.getRes1())) {
			temp += "1号染色体异常 ;";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes2() && "abnormal".equals(tbSample.getRes2())) {
			temp += "2号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes3() && "abnormal".equals(tbSample.getRes3())) {
			temp += "3号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes4() && "abnormal".equals(tbSample.getRes4())) {
			temp += "4号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes5() && "abnormal".equals(tbSample.getRes5())) {
			temp += "5号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes6() && "abnormal".equals(tbSample.getRes6())) {
			temp += "6号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes7() && "abnormal".equals(tbSample.getRes7())) {
			temp += "7号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes8() && "abnormal".equals(tbSample.getRes8())) {
			temp += "8号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes9() && "abnormal".equals(tbSample.getRes9())) {
			temp += "9号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes10() && "abnormal".equals(tbSample.getRes10())) {
			temp += "10号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes11() && "abnormal".equals(tbSample.getRes11())) {
			temp += "11号染色体异常 ; ";
		}
		
		if (tbSample.getIfControl()==0 && null != tbSample.getRes12() && "abnormal".equals(tbSample.getRes12())) {
			temp += "12号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes14() && "abnormal".equals(tbSample.getRes14())) {
			temp += "14号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes15() && "abnormal".equals(tbSample.getRes15())) {
			temp += "15号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes16() && "abnormal".equals(tbSample.getRes16())) {
			temp += "16号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes17() && "abnormal".equals(tbSample.getRes17())) {
			temp += "17号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes19() && "abnormal".equals(tbSample.getRes19())) {
			temp += "19号染色体异常 ; ";
		}
		if (tbSample.getIfControl()==0 && null != tbSample.getRes20() && "abnormal".equals(tbSample.getRes20())) {
			temp += "20号染色体异常 ;";
		}
		return temp;
	}

	public List<TbSample> listAuditSample(TbSample sample) {
		// TODO Auto-generated method stub
		return sampleMapper.listAuditSample(sample);
	}

	public List<TbSample> listSampleDetail(TbSample sample) {
		// TODO Auto-generated method stub
		return sampleMapper.listSampleDetail(sample);
	}

	public void updateSupplementState(TbSample sample) {
		// TODO Auto-generated method stub
		sampleMapper.updateSupplementState(sample);
	}

	public List<DownLoadDetail> getSDownloadDetail(Long sampleId) {
		// TODO Auto-generated method stub
		return downLoadMapper.getSDownloadDetail(sampleId);
	}

	public boolean exportBatch(TbBatch batch, HttpServletResponse response, HttpServletRequest request) throws Exception {
	        String strUrl = batch.getReturnData();
	        if (null == strUrl) {
	        	return false;
			}
	        String filename = strUrl.substring(strUrl.lastIndexOf("/")+1);
	        filename = filename.substring(filename.lastIndexOf("_")+1);
	        filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
	        String path = strUrl;
	        File file = new File(path);
	        //如果文件不存在
	        if(!file.exists()){
	        	return false;
	        }
	        //设置响应头，控制浏览器下载该文件
	        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
	        //读取要下载的文件，保存到文件输入流
	        FileInputStream in = new FileInputStream(path);
	        //创建输出流
	        OutputStream out = response.getOutputStream();
	        //缓存区
	        byte buffer[] = new byte[1024];
	        int len = 0;
	        //循环将输入流中的内容读取到缓冲区中
	        while((len = in.read(buffer)) > 0){
	            out.write(buffer, 0, len);
	        }
	        //关闭
	        in.close();
	        out.close();
	        return true;
		}

	public List<TbSample> listSampleForExcel(TbSample sample) {
		String sql = createSQL(sample);
		sample.setSql(sql);
		return sampleMapper.listSampleForExcel(sample);
	}
	public String createSQL(TbSample item){
		String sql = "SELECT D.detail sgs,substring(A.sample_id, - 2) AS a,B.batch_num,A.*, C.* FROM	tb_sample A LEFT JOIN tb_batch B ON A.batch_id = B.id LEFT JOIN tb_sample_result C ON A.id = C.tb_sample_id LEFT JOIN tb_result_detail D ON A.sgst = D.sgst where 1=1 ";
		if (null!=item){
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
				if(item.getConditionA().equals("age") || item.getConditionA().equals("ges_week") || item.getConditionA().equals("NT") || item.getConditionA().equals("raw_data") || item.getConditionA().equals("q30") || item.getConditionA().equals("align_rate") ||  item.getConditionA().equals("dup_rate") || item.getConditionA().equals("ur_num") ||  item.getConditionA().equals("GC")){  
					if(item.getConditionA().equals("ges_week")){
						item.setMoreA1(isNotNullOrEmpty(item.getMoreA1()) ? item.getMoreA1()+"w" : "");
						item.setMoreA2(isNotNullOrEmpty(item.getMoreA2()) ? item.getMoreA2()+"w" : "");
					}
					if(item.getConditionA().equals("q30")){
						item.setMoreA1(isNotNullOrEmpty(item.getMoreA1()) ? 
								(item.getMoreA1().endsWith("%")? 
										String.valueOf(new BigDecimal(item.getMoreA1().substring(0, item.getMoreA1().length()-1))):"") : "");
						item.setMoreA2(isNotNullOrEmpty(item.getMoreA2()) ? 
								(item.getMoreA2().endsWith("%")? 
										String.valueOf(new BigDecimal(item.getMoreA2().substring(0, item.getMoreA2().length()-1))):"") : "");
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
							sql += "and A."+item.getConditionA()+" >= 4 ";
						}
						if(item.getConditionAOpt().equals("low_risk")){
							sql += "and A."+item.getConditionA()+" <= 3 ";
						}
						if(item.getConditionAOpt().equals("grey")){
							sql += "and A."+item.getConditionA()+" > 3  and A."+item.getConditionA()+"  < 4 ";
						}
					}else{
						sql += "and A."+item.getConditionA()+" = '"+item.getConditionAOpt()+"' ";
					}
				}
				
				if(!item.getConditionA().equals("recieve_date") && !item.getConditionA().equals("sampling_date") && !item.getConditionA().equals("last_period") && !item.getConditionA().equals("age") && !item.getConditionA().equals("ges_week") && !item.getConditionA().equals("NT") && !item.getConditionA().equals("raw_data") && !item.getConditionA().equals("q30") && !item.getConditionA().equals("align_rate") && !item.getConditionA().equals("dup_rate") && !item.getConditionA().equals("ur_num") &&  !item.getConditionA().equals("GC") && !item.getConditionA().equals("Z13") && !item.getConditionA().equals("Z18") && !item.getConditionA().equals("Z21")){  //判断是否精确查询
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
					if(item.getConditionB().equals("q30")){
						item.setMoreB1(isNotNullOrEmpty(item.getMoreB1()) ? 
								(item.getMoreB1().endsWith("%")? 
										String.valueOf(new BigDecimal(item.getMoreB1().substring(0, item.getMoreB1().length()-1))):"") : "");
						item.setMoreB2(isNotNullOrEmpty(item.getMoreB2()) ? 
								(item.getMoreB2().endsWith("%")? 
										String.valueOf(new BigDecimal(item.getMoreB2().substring(0, item.getMoreB2().length()-1))):"") : "");
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
							sql += "and A."+item.getConditionB()+" >= 4 ";
						}
						if(item.getConditionBOpt().equals("low_risk")){
							sql += "and A."+item.getConditionB()+" <= 3 ";
						}
						if(item.getConditionBOpt().equals("grey")){
							sql += "and A."+item.getConditionB()+" > 3  and A."+item.getConditionB()+"  < 4 ";;
						}
					}else{
						sql += "and A."+item.getConditionB()+" = '"+item.getConditionBOpt()+"' ";
					}
				}
				
				if(!item.getConditionB().equals("recieve_date") && !item.getConditionB().equals("sampling_date") && !item.getConditionB().equals("last_period") && !item.getConditionB().equals("age") && !item.getConditionB().equals("ges_week")&& !item.getConditionB().equals("NT") && !item.getConditionB().equals("raw_data") && !item.getConditionB().equals("q30") && !item.getConditionB().equals("align_rate") && !item.getConditionB().equals("dup_rate") && !item.getConditionB().equals("ur_num") &&  !item.getConditionB().equals("GC") && !item.getConditionB().equals("Z13") && !item.getConditionB().equals("Z18") && !item.getConditionB().equals("Z21")){  //判断是否精确查询
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
				if(item.getConditionC().equals("age") || item.getConditionC().equals("ges_week") || item.getConditionC().equals("NT") || item.getConditionC().equals("raw_data") || item.getConditionC().equals("q30") || item.getConditionC().equals("align_rate") ||  item.getConditionC().equals("dup_rate") || item.getConditionC().equals("ur_num") ||  item.getConditionC().equals("GC")){
					if(item.getConditionC().equals("ges_week")){
						item.setMoreC1(isNotNullOrEmpty(item.getMoreC1()) ? item.getMoreC1()+"w" : "");
						item.setMoreC2(isNotNullOrEmpty(item.getMoreC2()) ? item.getMoreC2()+"w" : "");
					}
					if(item.getConditionC().equals("q30")){
						item.setMoreC1(isNotNullOrEmpty(item.getMoreC1()) ? 
								(item.getMoreC1().endsWith("%")? 
										String.valueOf(new BigDecimal(item.getMoreC1().substring(0, item.getMoreC1().length()-1))):"") : "");
						item.setMoreC2(isNotNullOrEmpty(item.getMoreC2()) ? 
								(item.getMoreC2().endsWith("%")? 
										String.valueOf(new BigDecimal(item.getMoreC2().substring(0, item.getMoreC2().length()-1))):"") : "");
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
							sql += "and A."+item.getConditionC()+" >= 4 ";
						}
						if(item.getConditionCOpt().equals("low_risk")){
							sql += "and A."+item.getConditionC()+" <= 3 ";
						}
						if(item.getConditionCOpt().equals("grey")){
							sql += "and A."+item.getConditionC()+" > 3  and A."+item.getConditionC()+"  < 4 ";
						}
					}else{
						sql += "and A."+item.getConditionC()+" = '"+item.getConditionCOpt()+"' ";
					}
				}
				
				if(!item.getConditionC().equals("recieve_date") && !item.getConditionC().equals("sampling_date") && !item.getConditionC().equals("last_period") && !item.getConditionC().equals("age") && !item.getConditionC().equals("ges_week")&& !item.getConditionC().equals("NT") && !item.getConditionC().equals("raw_data") && !item.getConditionC().equals("q30") && !item.getConditionC().equals("align_rate") && !item.getConditionC().equals("dup_rate") && !item.getConditionC().equals("ur_num") &&  !item.getConditionC().equals("GC")&& !item.getConditionC().equals("Z13") && !item.getConditionC().equals("Z18") && !item.getConditionC().equals("Z21")){  //判断是否精确查询
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
					if(item.getConditionD().equals("q30")){
						item.setMoreD1(isNotNullOrEmpty(item.getMoreD1()) ? 
								(item.getMoreD1().endsWith("%")? 
										String.valueOf(new BigDecimal(item.getMoreD1().substring(0, item.getMoreD1().length()-1))):"") : "");
						item.setMoreD2(isNotNullOrEmpty(item.getMoreD2()) ? 
								(item.getMoreD2().endsWith("%")? 
										String.valueOf(new BigDecimal(item.getMoreD2().substring(0, item.getMoreD2().length()-1))):"") : "");
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
							sql += "and A."+item.getConditionD()+" >= 4 ";
						}
						if(item.getConditionCOpt().equals("low_risk")){
							sql += "and A."+item.getConditionD()+" <= 3 ";
						}
						if(item.getConditionCOpt().equals("grey")){
							sql += "and A."+item.getConditionD()+" > 3  and A."+item.getConditionD()+"  < 4 ";
						}
					}else{
						sql += "and A."+item.getConditionD()+" = '"+item.getConditionDOpt()+"' ";
					}
				}
				
				if(!item.getConditionD().equals("recieve_date") && !item.getConditionD().equals("sampling_date") && !item.getConditionD().equals("last_period") && !item.getConditionD().equals("age") && !item.getConditionD().equals("ges_week")&& !item.getConditionD().equals("NT") && !item.getConditionD().equals("raw_data") && !item.getConditionD().equals("Q30") && !item.getConditionD().equals("align_rate") && !item.getConditionD().equals("dup_rate") && !item.getConditionD().equals("ur_num") &&  !item.getConditionD().equals("GC")&&  !item.getConditionD().equals("Z13") && !item.getConditionD().equals("Z18") && !item.getConditionD().equals("Z21")){  //判断是否精确查询
					sql += "and A."+item.getConditionD()+" = '"+item.getConditionDName()+"' ";
				}
			}
			sql += " ORDER BY FIELD(A.tips,0,4,1,2,3,5) desc ,FIELD(a,'-R','-3','-2')desc ";
		}
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

	public List<TbSample> listPageSampleTrack(TbSample sample) {
		 List<TbSample> list = sampleMapper.listPageSampleTrack(sample);
		 String [] temp = null ;
		 List<TbSample> sList = null;
		 for (TbSample s : list) {
			 temp = s.getLibId().split("-");//文库编号打成数组
			 if (temp.length>1) {
				 sList = sampleMapper.getSampleByLibId(temp[0]);
				if (sList.size()==1) {
					s.setTips2(Integer.parseInt(sList.get(0).getTips()));
					s.setSeqDate2(sList.get(0).getSeqDate());
					s.setBatchNum2(sList.get(0).getBatchNum());
					s.setSampleId(s.getSampleId()+"/"+sList.get(0).getSampleId());
					if (sList.get(0).getTips().equals("0")) {
						s.setReportState(0);
					}else {
						s.setReportState(1);
					}
				}else if (sList.size()==2) {
					s.setTips2(Integer.parseInt(sList.get(0).getTips()));
					s.setSeqDate2(sList.get(0).getSeqDate());
					s.setBatchNum2(sList.get(0).getBatchNum());
					s.setTips3(Integer.parseInt(sList.get(1).getTips()));
					s.setSeqDate3(sList.get(1).getSeqDate());
					s.setBatchNum3(sList.get(1).getBatchNum());
					s.setSampleId(s.getSampleId()+"/"+sList.get(0).getSampleId()+"/"+sList.get(1).getSampleId());
					if (sList.get(1).getTips().equals("0")) {
						s.setReportState(0);
					}else {
						s.setReportState(1);
					}
				}else {
					if (null != s.getTips() && s.getTips().equals("0")) {
						s.setReportState(0);
					}else {
						s.setReportState(1);
					}
					continue;
				} 
			}else continue;
		}
		return list;
	} */
}
