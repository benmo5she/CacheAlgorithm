package com.hit.memory;

import java.util.ArrayList;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

public class CacheUnit<T> {
	private IDao<java.io.Serializable, DataModel<T>> dao;
	private IAlgoCache<java.lang.Long, DataModel<T>> algo;

	public CacheUnit(com.hit.algorithm.IAlgoCache<java.lang.Long, DataModel<T>> algo,
			IDao<java.io.Serializable, DataModel<T>> dao) {
		this.dao = dao;
		this.algo = algo;
	}

	public DataModel<T>[] getDataModels(java.lang.Long[] ids)
			throws java.lang.ClassNotFoundException, java.io.IOException {
		 ArrayList<DataModel<T>> result = new ArrayList<DataModel<T>>();
		for(Long DMId : ids)
		{
			
		}
	}
}
