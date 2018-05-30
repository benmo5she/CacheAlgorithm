package com.hit.model;

import java.util.Observable;

public class CacheUnitModel extends Observable implements Model {
	private CacheUnitClient clientSocket;

	public CacheUnitModel() {
		clientSocket = new CacheUnitClient();
	}

	@Override
	public <T> void updateModelData(T t) {
		// TODO Auto-generated method stub

	}

}
