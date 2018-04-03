package com.hit.algorithm;

//This class will serve as a base for each paging algorithm implementation.
//Like actual ram,it should have a finite predetermined capacity by the user.
//And as such,this class will dictate each algorithm implementation to abide that restriction.
public abstract class AbstractAlgoCache<K, V> implements IAlgoCache<K, V> {
	int _capacity;

	AbstractAlgoCache(int capacity) {
		_capacity = capacity;
	}
}
