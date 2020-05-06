package com.flf.service;
import com.flf.entity.TbSample;
import java.util.List;
public interface SampleService {
	void deleteSample(Long batchId);

	void insertSample(TbSample sample);
	
	TbSample getSampleById(Long tbSampleId);
	List<TbSample> listSample(TbSample sample);

	List<TbSample> listPageSampleByBatchId(TbSample sample);
	/*List<TbSample> getSampleByBatchId(Long batchId);
	
	List<TbSample> listPageSample(TbSample sample);
	
	List<TbSample> listSample(TbSample sample);
	

	void updateStatus(TbSample sample);
	
	TbSample getSampleBysampleId(String sampleId);
	
	List<TbSample> getSampleIds(List<String> sampleId);

	void updateReportAuditState(TbSample sample);

	List<TbSample> listSampleByBatchId(TbSample sample);

	void updateGeneratePdf(List<TbSample> sampleList, String prefix,HttpServletResponse response,HttpSession session) throws IOException;
	List<String> readPdf(List<TbSample> sampleList, String prefix,String isSuper);

	List<DownLoadDetail> getDownloadDetail(Long sampleId);

	void exportExcel(TbSample sample, String templetePath, OutputStream out, Integer type)throws Exception ;

	String insertClinicalEditDetail(ClinicalEditDetail clinicalEditDetail,  HttpSession session);

	List<ClinicalEditDetail> listClinicalEdit(Long sampleId);

	List<TbSample> listPageTipsEdit(TbSample sample);

	TbResult getResultBySampleId(Long tbSampleId);

	List<TbResult> listResultBySampleId(Long sampleId);

	List<TbSample> listPageAuditSample(TbSample sample);

	void downPic(TbSample sample, HttpServletResponse response, HttpServletRequest request)throws Exception ;

	List<TbSample> listPageSampleDetail(TbSample sample);

	String getTips(TbSample tbSample);

	List<TbSample> listAuditSample(TbSample sample);

	List<TbSample> listSampleDetail(TbSample sample);

	void updateSupplementState(TbSample sample);

	void generateSuPdf(List<TbSample> sampleList, String prefix, HttpServletResponse response, HttpSession session,
			String reporttype)throws IOException;

	List<DownLoadDetail> getSDownloadDetail(Long sampleId);

	boolean exportBatch(TbBatch batch, HttpServletResponse response, HttpServletRequest request) throws Exception;

	List<TbSample> listSampleForExcel(TbSample sample);

	List<TbSample> listPageSampleTrack(TbSample sample);*/

}
