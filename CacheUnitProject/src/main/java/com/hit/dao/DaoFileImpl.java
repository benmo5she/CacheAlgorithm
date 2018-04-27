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

	public void saveToFile(HashMap<Long, DataModel<T>> content) throws IOException {
		FileOutputStream os = null;
		ObjectOutputStream oos = null;
		try {
			File pagesStorage = new File(filePath);
			// If file does not exist it will create it,otherwise will just use the existing
			// one
			pagesStorage.createNewFile();
			os = new FileOutputStream(pagesStorage, false);
			oos = new ObjectOutputStream(os);
			oos.writeObject(content);
			oos.flush();
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

	private HashMap<Long, DataModel<T>> readFile() throws ClassNotFoundException, IOException {
		HashMap<Long, DataModel<T>> result = null;
		FileInputStream streamIn = null;
		ObjectInputStream objectinputstream = null;
		try {
			// Open and read the file into a map, and return(get) the item by the id.
			streamIn = new FileInputStream(filePath);
			objectinputstream = new ObjectInputStream(streamIn);
			result = (HashMap<Long, DataModel<T>>) objectinputstream.readObject();
		} catch (EOFException ex) {
			// If the file is empty no need to read it.
			return null;
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
		return result;
	}

	public DaoFileImpl(java.lang.String filePath) {
		this.filePath = filePath;
	}

	// This method provides a service which enables to remove items from the hard
	// disk file
	// That is Managed by the DAO
	@Override
	public void delete(DataModel<T> entity) {
		HashMap<Long, DataModel<T>> foundPages = null;
		try {
			foundPages = readFile();
			DataModel<T> removedPage = foundPages.remove(entity.getDataModelId());
			// It is only necessary to write to the file if the item was found and removed,
			// Otherwise do not need to write to the file
			if (removedPage != null) {
				saveToFile(foundPages);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	// This method will let us find in our HDD item by it's id, if found it will
	// Return the item,otherwise null
	@Override
	public DataModel<T> find(Long id) {
		DataModel<T> resultPage = null;
		HashMap<Long, DataModel<T>> foundPages;
		try {
			foundPages = readFile();
			if (foundPages != null) {
				resultPage = foundPages.get(id);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return resultPage;

	}

	// This method will let the user save DataModels into the HDD
	// Stored in the file configured by the path sent to the class.
	@Override
	public void save(DataModel<T> t) {

		HashMap<Long, DataModel<T>> fileContent;
		try {
			fileContent = readFile();
			if(fileContent == null)
			{
				fileContent = new HashMap<>();
			}
			if (fileContent != null) {
				DataModel<T> foundPage = fileContent.get(t.getDataModelId());
				if (foundPage == null || foundPage.getContent() != t.getContent()) {
					fileContent.put(t.getDataModelId(), t);
					saveToFile(fileContent);
				}
			}
			// Check if the data currently in file(id and content), if so avoid unnecessary
			// writing to file,
			// otherwise continue writing to file
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

}
