package com.flf.service;

import com.flf.entity.User;
import com.flf.entity.Warn;

import java.util.List;

public interface WarnService {

	List<Warn> listAllWarn();

	List<Warn> listPageWarn(Warn warn);

	void insertWarn(Warn warn);

	void updateWarn(Warn warn);
	
}
