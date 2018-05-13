package com.hit.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitService<T> {
	CacheUnit<T> memory;
	public CacheUnitService()  {
		File pagesStorage = new File("src/main/resources/datasource.txt");
		IAlgoCache<Long, DataModel<T>> algo = new LRUAlgoCacheImpl<Long, DataModel<T>>(3);
		memory = new CacheUnit<T>(algo, new DaoFileImpl<>(pagesStorage.getAbsolutePath()));
	}

	public boolean update(DataModel<T>[] dataModels) {
		DataModel<T>[] foundModels = get(dataModels);
		boolean updatedAll = true;
		try {
			for (DataModel<T> memoryModel : foundModels) {
				for (DataModel<T> userModel : dataModels) {
					if (userModel.getDataModelId().equals(memoryModel.getDataModelId())) {
						memoryModel.setContent(userModel.getContent());
						break;
					}
				}
			}
		} catch (Exception ex) {
			updatedAll = false;
		}
		return updatedAll;
	}

	public boolean delete(DataModel<T>[] dataModels) {
		DataModel<T>[] foundModels = get(dataModels);
		boolean removedAll = true;
		try {
			for (DataModel<T> model : foundModels) {
				model.setContent(null);
			}
		} catch (Exception ex) {
			removedAll = false;
		}
		return removedAll;
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		ArrayList<Long> modelsIds = new ArrayList<>();
		for (DataModel<T> item : dataModels) {
			modelsIds.add(item.getDataModelId());
		}
		DataModel<T>[] resultArr = null;
		try {
			resultArr = memory.getDataModels(modelsIds.toArray(new Long[modelsIds.size()]));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return resultArr;
	}
}
