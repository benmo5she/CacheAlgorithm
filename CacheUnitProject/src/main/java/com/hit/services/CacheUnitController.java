package com.hit.services;

import com.hit.dm.DataModel;

//This class is used to operate the CacheService unit providing simple methods for 
//access to information.
public class CacheUnitController<T> {
	private CacheUnitService<T> cacheService;

	public CacheUnitController() {
		cacheService = new CacheUnitService<>();
	}

	public boolean update(DataModel<T>[] dataModels) {
		synchronized (cacheService) {
			return cacheService.update(dataModels);
		}

	}

	public boolean delete(DataModel<T>[] dataModels) {
		synchronized (cacheService) {
			return cacheService.delete(dataModels);
		}
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		synchronized (cacheService) {
			return cacheService.get(dataModels);
		}
	}
}
