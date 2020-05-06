package com.flf.service.impl;

import com.flf.service.WarnService;
import com.flf.entity.Warn;
import com.flf.mapper.WarnMapper;
import java.util.List;

public class WarnServiceImpl implements WarnService {

	private WarnMapper warnMapper;

	public WarnMapper getWarnMapper() {
		return warnMapper;
	}

	public void setWarnMapper(WarnMapper warnMapper) {
		this.warnMapper = warnMapper;
	}

	@Override
	public List<Warn> listAllWarn() {
		return warnMapper.listAllWarn();
	}

	@Override
	public List<Warn> listPageWarn(Warn warn) {
		return warnMapper.listPageWarn(warn);
	}

	@Override
	public void insertWarn(Warn warn) {
		warnMapper.insertWarn(warn);
	}

	@Override
	public void updateWarn(Warn warn) {
		warnMapper.updateWarn(warn);
	}
}
