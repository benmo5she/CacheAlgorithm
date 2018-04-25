package com.hit.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.hit.dm.DataModel;

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
	private String filePath;

	public DaoFileImpl(java.lang.String filePath) {
		this.filePath = filePath;
	}

	// This method provides a service which enables to remove items from the hard
	// disk file
	// That is Managed by the DAO
	@Override
	public void delete(DataModel<T> entity) {
		FileInputStream streamIn = null;
		ObjectInputStream objectinputstream = null;
		ObjectOutputStream oos = null;
		FileOutputStream os = null;
		try {
			File pagesStorage = new File(filePath);
			// If file does not exist it will create it,otherwise will just use the existing
			// one
			pagesStorage.createNewFile();
			streamIn = new FileInputStream(pagesStorage);
			objectinputstream = new ObjectInputStream(streamIn);
			// Before removing an item from the file, we should first fetch the items and
			// see if it exists.
			// Remove the item from the map if so, and overwrite the file with the updated
			// map.
			HashMap<Long, DataModel<T>> foundPages = (HashMap<Long, DataModel<T>>) objectinputstream.readObject();
			DataModel<T> removedPage = foundPages.remove(entity.getDataModelId());
			// We only need to write to the file if the item was found and removed,
			// Otherwise we dont need to write to the file
			if (removedPage != null) {
				os = new FileOutputStream(pagesStorage, false);
				oos = new ObjectOutputStream(os);
				oos.writeObject(foundPages);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Begin closing the streams if necessary.
		} finally {
			try {
				if (streamIn != null) {
					streamIn.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (objectinputstream != null) {
					objectinputstream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	// This method will let us find in our HDD item by it's id, if found it will
	// Return the item,otherwise null
	@Override
	public DataModel<T> find(Long id) {
		FileInputStream streamIn = null;
		ObjectInputStream objectinputstream = null;
		DataModel<T> resultPage = null;
		try {
			// Open and read the file into a map, and return(get) the item by the id.
			streamIn = new FileInputStream(filePath);
			objectinputstream = new ObjectInputStream(streamIn);
			HashMap<Long, DataModel<T>> foundPages = (HashMap<Long, DataModel<T>>) objectinputstream.readObject();
			resultPage = foundPages.get(id);
		} catch (EOFException ex) {
			// If the file is empty no need to read it.
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			// Begin closing the streams if necesary
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
		return resultPage;
	}

	// This method will let the user save DataModels into the HDD
	// Stored in the file configured by the path sent to the class.
	@Override
	public void save(DataModel<T> t) {
		FileOutputStream os = null;
		ObjectOutputStream oos = null;
		try {
			File pagesStorage = new File(filePath);
			// If file does not exist it will create it,otherwise will just use the existing
			// one
			pagesStorage.createNewFile();

			HashMap<Long, DataModel<T>> map = null;
			FileInputStream fis = new FileInputStream(pagesStorage);
			ObjectInputStream ois = null;
			// First get the current content of the file
			try {
				ois = new ObjectInputStream(fis);
				map = (HashMap) ois.readObject();
			} catch (EOFException ex) {
				// If the file is empty create new hash map
				map = new HashMap<>();
			}
			if (ois != null) {
				ois.close();
			}
			if (fis != null) {
				fis.close();
			}
			// Add the new item to the map and prepare to save the updated map into the
			// file.
			map.put(t.getDataModelId(), t);
			os = new FileOutputStream(pagesStorage, false);
			oos = new ObjectOutputStream(os);
			oos.writeObject(map);
		} catch (Exception e) {
			e.printStackTrace();
			// Start closing the streams if necessary
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

}
