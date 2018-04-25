package com.hit.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V> {
	// As there is no need to keep track of the nodes activity for this
	// implementation,we should use a ArrayList for easier access to our nodes by
	// index.
	ArrayList<K> ramMemory;
	HashMap<K, V> valuesRepository = new HashMap<K, V>();

	public RandomAlgoCacheImpl(int capacity) {
		super(capacity);
		ramMemory = new ArrayList<K>();
	}

	@Override
	public V getElement(K key) {
		return valuesRepository.get(key);
	}

	@Override
	public V putElement(K key, V value) {
		/// If the array is full, activate the algorithm
		// as the algorithm dictates, we should choose a node at random and remove it.
		// Get a random number in the appropriate range,and remove it from our content
		/// repository and ram.
		// Finally,add the new node
		if (ramMemory.size() == _capacity) {
			Random ran = new Random();
			int randomNumber = ran.nextInt(_capacity - 1);
			removeElement(ramMemory.get(randomNumber));
		}
		ramMemory.add(key);
		valuesRepository.put(key, value);
		return value;
	}

	@Override
	public void removeElement(K key) {
		// Use the collection remove method to remove the node(if exists) from the
		// content repository
		// and the ram.
		ramMemory.remove(key);
		valuesRepository.remove(key);
	}

}
