package com.hit.dao;

import com.hit.dm.DataModel;

public class DaoFileImpl<T> implements IDao<java.lang.Long, DataModel<T>> {
private String filePath;
	public DaoFileImpl(java.lang.String filePath)
	{
		this.filePath = filePath;
	}
	
	@Override
	public void delete(DataModel<T> entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DataModel<T> find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(DataModel<T> t) {
		// TODO Auto-generated method stub
		
	}

}
