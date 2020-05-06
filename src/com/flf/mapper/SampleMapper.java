package com.flf.mapper;

import java.util.List;

import com.flf.entity.TbSample;

public interface SampleMapper  {
	List<TbSample> listPageSample(TbSample sample);
	List<TbSample> listSample(TbSample sample);
	List<TbSample> getSampleByBatchId(Long batchId);
	TbSample getSampleById(Long tbSampleId);
	void deleteSample(Long batchId);
	void insertSample(TbSample sample);
	void updateStatus(TbSample sample);
	TbSample getSampleBysampleId(String sampleId);
	List<TbSample> getSampleIds(List<String> sampleId);
	List<TbSample> listPageSampleByBatchId(TbSample sample);
	void updateReportAuditState(TbSample sample);
	List<TbSample> listSampleByBatchId(TbSample sample);
	void updateDownLoadStatus(TbSample sample);
	void updateclinical(TbSample sample);
	/**
	 * 修改样本处理建议
	 * @param sample
	 */
	void updateTips(TbSample sample);
	/**
	 * 显示修改过样本处理建议的列表 
	 * @param sample
	 * @return
	 */
	List<TbSample> listPageTipsEdit(TbSample sample);
	List<TbSample> listPageAuditSample(TbSample sample);
	List<TbSample> listAuditSample(TbSample sample);
	List<TbSample> listPageSampleDetail(TbSample sample);
	List<TbSample> listSampleDetail(TbSample sample);
	void updateSupplementState(TbSample sample);
	void updateSuDownLoadStatus(TbSample sample);
	List<TbSample> listSampleForExcel(TbSample sample);
	List<TbSample> listPageSampleTrack(TbSample sample);
	List<TbSample> getSampleByLibId(String libId);
}
