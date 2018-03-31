package com.hit.algorithm;

//This interface will be used to operate the MMU with different implementation of paging algorithms
public interface IAlgoCache<K, V> {
	V getElement(K key);

	V putElement(K key, V value);

	void removeElement(K key);
}
