package com.flf.mapper;

import java.util.List;

import com.flf.entity.TbBatch;

public interface BatchMapper  {
	TbBatch getBatchById(Long batchId);
	List<TbBatch> listPageBatch(TbBatch batch);
	void deleteBatch(Long batchId);
	void insertBatch(TbBatch batch);
}
