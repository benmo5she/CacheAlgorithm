package com.hit.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

//This class is used to operate the CacheUnit using the provided API to
//get the relevant objects and modify them accordingly.
public class CacheUnitService<T> {
	CacheUnit<T> memory;

	public CacheUnitService() {
		// For now we use arbitrary implementation of LRU and DAO of size 3.
		File pagesStorage = new File("src/main/resources/datasource.txt");
		IAlgoCache<Long, DataModel<T>> algo = new LRUAlgoCacheImpl<Long, DataModel<T>>(3);
		memory = new CacheUnit<T>(algo, new DaoFileImpl<>(pagesStorage.getAbsolutePath()));
	}

	// Let the user update content of existing dataModels in the CacheUnit
	public boolean update(DataModel<T>[] dataModels) {
		// Get the existing models according to the input models id's
		DataModel<T>[] foundModels = get(dataModels);
		// We will return true as long as the operation has been completed successfully.
		boolean updatedAll = true;
		try {
			for (DataModel<T> memoryModel : foundModels) {
				for (DataModel<T> userModel : dataModels) {
					// Find the matching model and update it's content accordingly.
					if (userModel.getDataModelId().equals(memoryModel.getDataModelId())) {
						memoryModel.setContent(userModel.getContent());
						break;
					}
				}
			}
			// If we reached this catch it means the process has not been completed.
			// Return false to the user accordingly.
		} catch (Exception ex) {
			updatedAll = false;
			ex.printStackTrace();
		}
		return updatedAll;
	}

	// This method allow to remove(change content to null) of existing models in the
	// CacheUnit.
	public boolean delete(DataModel<T>[] dataModels) {
		// Get filtered collection of models that has the same id's as the input
		DataModel<T>[] foundModels = get(dataModels);
		boolean removedAll = true;
		try {
			// Start setting the content of the existing models found to null.
			for (DataModel<T> model : foundModels) {
				model.setContent(null);
			}
			// Return false to the client if the operation was not completed.
		} catch (Exception ex) {
			removedAll = false;
		}
		return removedAll;
	}

	// This method will return collection of existing models that has the same id's
	// as the input accepted.
	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		ArrayList<Long> modelsIds = new ArrayList<>();
		//Get the ids of the models
		for (DataModel<T> item : dataModels) {
			modelsIds.add(item.getDataModelId());
		}
		DataModel<T>[] resultArr = null;
		try {
			//Get actual models from the memory,
			resultArr = memory.getDataModels(modelsIds.toArray(new Long[modelsIds.size()]));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return null;
		}
		return resultArr;
	}
}
