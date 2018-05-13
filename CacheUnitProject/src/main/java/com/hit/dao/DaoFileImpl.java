package com.hit.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.hit.dm.DataModel;

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
	private String filePath;
	private HashMap<Long, DataModel<T>> memoryCache = null;

	public void saveToFile() {
		FileOutputStream os = null;
		ObjectOutputStream oos = null;
		try {
			File pagesStorage = new File(filePath);
			// If file does not exist it will create it,otherwise will just use the existing
			// one
			pagesStorage.createNewFile();
			os = new FileOutputStream(pagesStorage, false);
			oos = new ObjectOutputStream(os);
			oos.writeObject(memoryCache);
			oos.flush();
			
		} catch (IOException e) {
			// If file not found print the exception and dont do anything.
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (oos != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void readFileToCache() {
		FileInputStream streamIn = null;
		ObjectInputStream objectinputstream = null;
		try {
			// Open and read the file into a map, and return(get) the item by the id.
			streamIn = new FileInputStream(filePath);
			objectinputstream = new ObjectInputStream(streamIn);
			memoryCache = (HashMap<Long, DataModel<T>>) objectinputstream.readObject();
			// If the file is empty no need to read it.
		} catch (Exception ex) {
			// Begin closing the streams if necessary
		} finally {
			if (streamIn != null) {
				try {
					streamIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (objectinputstream != null) {
				try {
					objectinputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public DaoFileImpl(java.lang.String filePath) {
		this.filePath = filePath;
		readFileToCache();
	}

	// This method provides a service which enables to remove items from the hard
	// disk file
	// That is Managed by the DAO
	@Override
	public void delete(DataModel<T> entity) {
		if(memoryCache != null)
		{
			memoryCache.remove(entity.getDataModelId());
			saveToFile();			
		}

	}

	// This method will let us find in our HDD item by it's id, if found it will
	// Return the item,otherwise null
	@Override
	public DataModel<T> find(Long id) {
		DataModel<T> resultPage = null;
		if (memoryCache != null) {
			resultPage = memoryCache.get(id);
		}
		return resultPage;

	}

	// This method will let the user save DataModels into the HDD
	// Stored in the file configured by the path sent to the class.
	@Override
	public void save(DataModel<T> t) {
		if (memoryCache == null) {
			memoryCache = new HashMap<>();
		}
		DataModel<T> foundPage = memoryCache.get(t.getDataModelId());
		if (foundPage == null || foundPage.getContent() != t.getContent()) {
			memoryCache.put(t.getDataModelId(), t);
			saveToFile();
		}
		// Check if the data currently in file(id and content), if so avoid unnecessary
		// writing to file,
		// otherwise continue writing to file
	}

}
