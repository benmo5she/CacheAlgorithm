package com.hit.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
			streamIn = new FileInputStream(filePath);
			objectinputstream = new ObjectInputStream(streamIn);
			HashMap<Long,DataModel<T>> foundPages = (HashMap<Long,DataModel<T>>) objectinputstream.readObject();
			foundPages.remove(entity.getDataModelId());
			//Write back to file the foundPages after the removal
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
			HashMap<Long,DataModel<T>> foundPages = (HashMap<Long,DataModel<T>>) objectinputstream.readObject();
			resultPage = foundPages.get(id);
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
		return resultPage;
	}

	@Override
	public void save(DataModel<T> t) {
		OutputStream os;
		try {
			if(find(t.getDataModelId()) != null)
			{
				//If the page already exists we do not need to save again
				return;
			}
			os = new FileOutputStream(new File(filePath), true);
			os.write("data".getBytes(), 0, "data".length());
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
