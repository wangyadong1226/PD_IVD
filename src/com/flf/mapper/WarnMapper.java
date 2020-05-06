package com.flf.mapper;

import com.flf.entity.Warn;

import java.util.List;

public interface WarnMapper {

	List<Warn> listAllWarn();

	List<Warn> listPageWarn(Warn warn);

	void insertWarn(Warn warn);

	void updateWarn(Warn warn);
}
