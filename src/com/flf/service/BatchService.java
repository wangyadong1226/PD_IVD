package com.flf.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.flf.entity.TbBatch;

public interface BatchService {
	
	TbBatch getBatchById(Long batchId);
	List<TbBatch> listPageBatch(TbBatch batch);
	void deleteBatch(Long batchId);
	boolean insertBatch(TbBatch batch);
}
