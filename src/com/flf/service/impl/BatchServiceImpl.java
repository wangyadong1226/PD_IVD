package com.flf.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.flf.entity.Role;
import com.flf.entity.TbBatch;
import com.flf.entity.TbSample;
import com.flf.entity.User;
import com.flf.mapper.BatchMapper;
import com.flf.mapper.RoleMapper;
import com.flf.mapper.SampleMapper;
import com.flf.service.BatchService;
import com.flf.util.Const;

public class BatchServiceImpl implements BatchService {

	private BatchMapper batchMapper;
	private SampleMapper sampleMapper;
	private RoleMapper roleMapper;

	public SampleMapper getSampleMapper() {
		return sampleMapper;
	}

	public void setSampleMapper(SampleMapper sampleMapper) {
		this.sampleMapper = sampleMapper;
	}

	public BatchMapper getBatchMapper() {
		return batchMapper;
	}

	public void setBatchMapper(BatchMapper batchMapper) {
		this.batchMapper = batchMapper;
	}

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	public List<TbBatch> listPageBatch(TbBatch batch){
		List<TbBatch> list=batchMapper.listPageBatch(batch);
		return list;
	}

	public void deleteBatch(Long batchId) {
		batchMapper.deleteBatch(batchId);
		
	}

	public TbBatch getBatchById(Long batchId) {
		// TODO Auto-generated method stub
		return batchMapper.getBatchById(batchId);
	}

	public boolean insertBatch(TbBatch batch) {
		// TODO Auto-generated method stub
			batchMapper.insertBatch(batch);
			return true;
//		}
	}


}
