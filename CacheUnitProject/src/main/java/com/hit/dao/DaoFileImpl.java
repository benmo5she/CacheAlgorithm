package com.hit.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import com.hit.dm.DataModel;

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
	private String filePath;

	public DaoFileImpl(java.lang.String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void delete(DataModel<T> entity) {
		FileInputStream streamIn = null;
		ObjectInputStream objectinputstream = null;
		try {
			File pagesStorage = new File(filePath);
			// If file does not exist it will create it,otherwise will just use the existing
			// one
			pagesStorage.createNewFile();
			streamIn = new FileInputStream(pagesStorage);
			objectinputstream = new ObjectInputStream(streamIn);
			HashMap<Long, DataModel<T>> foundPages = (HashMap<Long, DataModel<T>>) objectinputstream.readObject();
			foundPages.remove(entity.getDataModelId());
			FileOutputStream os = new FileOutputStream(pagesStorage, false);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(foundPages);
		} catch (Exception e) {
			e.printStackTrace();
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

	@Override
	public DataModel<T> find(Long id) {
		FileInputStream streamIn = null;
		ObjectInputStream objectinputstream = null;
		DataModel<T> resultPage = null;
		try {
			streamIn = new FileInputStream(filePath);
			objectinputstream = new ObjectInputStream(streamIn);
			HashMap<Long, DataModel<T>> foundPages = (HashMap<Long, DataModel<T>>) objectinputstream.readObject();
			resultPage = foundPages.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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

	@Override
	public void save(DataModel<T> t) {
		try {
/*			if (find(t.getDataModelId()) != null) {
				// If the page already exists we do not need to save again
				return;
			}*/
			// String
			File pagesStorage = new File(filePath);
			// If file does not exist it will create it,otherwise will just use the existing
			// one
			pagesStorage.createNewFile();

			HashMap<Long, DataModel<T>> map = null;
			FileInputStream fis = new FileInputStream(pagesStorage);
			ObjectInputStream ois = null;
			try
			{
				ois = new ObjectInputStream(fis);
				map = (HashMap) ois.readObject();
			}
			catch(EOFException ex)
			{
				//If the file is empty create new has map
				map = new HashMap<>();
			}
			if(ois != null)
			{
				ois.close();	
			}
			if(fis != null)
			{
				fis.close();
			}
			
			map.put(t.getDataModelId(), t);
			FileOutputStream  os = new FileOutputStream(pagesStorage, false);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(map);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
