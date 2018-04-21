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
	
	public DataModel<T>[] getDataModels(Long[] ids)
			throws java.lang.ClassNotFoundException, java.io.IOException {
		 ArrayList<DataModel<T>> resultPages = new ArrayList<DataModel<T>>();
		for(Long DMId : ids)
		{
			DataModel<T> foundPage = algo.getElement(DMId);
			//Is the page in the ram? if not we need to check the HDD
			if(foundPage == null)
			{				
				foundPage = dao.find(DMId);
<<<<<<< HEAD
				//If the page was found in HDD add to ram				
=======
>>>>>>> branch 'master' of https://github.com/benmo5she/CacheAlgorithm
				if(foundPage != null)
				{
					algo.putElement(DMId, foundPage);
				}
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
		@SuppressWarnings("unchecked")
		DataModel<T>[] arr = new DataModel[resultPages.size()];
		return resultPages.toArray(arr);
	}
}
