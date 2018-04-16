package com.hit.memory;

import java.util.ArrayList;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

public class CacheUnit<T> {
	private IDao<Long, DataModel<T>> dao;
	private IAlgoCache<Long, DataModel<T>> algo;

	public CacheUnit(IAlgoCache<Long, DataModel<T>> algo,
			IDao<Long, DataModel<T>> dao) {
		this.dao = dao;
		this.algo = algo;
	}

	public DataModel<T>[] getDataModels(java.lang.Long[] ids)
			throws java.lang.ClassNotFoundException, java.io.IOException {
		 ArrayList<DataModel<T>> resultPages = new ArrayList<DataModel<T>>();
		for(Long DMId : ids)
		{
			DataModel<T> foundPage = algo.getElement(DMId);
			if(foundPage == null)
			{
				foundPage = dao.find(DMId);
			}
			//Is the page missing from the cache? If so we need to find the page in HDD(dao)
			//And add it to the cache
			if(foundPage == null)
			{
				//Seems this id was not found in the HDD as well, carry on to the next id
				continue;
			}
			resultPages.add(foundPage);
		}		
		DataModel<T>[] result = (DataModel<T>[])new Object[resultPages.size()];
		return resultPages.toArray(result);
	}
}
