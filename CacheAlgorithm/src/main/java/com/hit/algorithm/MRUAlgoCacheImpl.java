package com.hit.algorithm;

import java.util.HashMap;
import java.util.Stack;

public class MRUAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V> {
	Stack<K> ramMemory;
	HashMap<K, V> valuesRepository = new HashMap<K, V>();

	public MRUAlgoCacheImpl(int capacity) {
		super(capacity);
		// Use Stack for this implementation as we need the most recently used value and
		// we can easily access it(pop)
		// And when needed to change the status of "recently used" just pop and push it
		// back in for cheaper cost than for example a list.
		ramMemory = new Stack<>();
	}

	@Override
	public V getElement(K key) {
		V resultObject = null;
		// Check if the stack has said value, if it does try to remove it and we will
		// push it back in afterwards
		boolean result = ramMemory.remove(key);
		if (result) {
			ramMemory.push(key);
			resultObject = valuesRepository.get(key);
		}
		return resultObject;
	}

	@Override
	public V putElement(K key, V value) {
		// If at capacity we need to dispose of one of the pages, get rid of the one
		// that is at the head of the stack
		// Since it was the most recently used,afterwards we have space for the new
		// value, push it in to the stack and insert to the content repository with the
		// index.
		if (ramMemory.size() == _capacity)
			ramMemory.pop();
		ramMemory.push(key);
		valuesRepository.put(key, value);
		return valuesRepository.get(key);
	}

	@Override
	public void removeElement(K key) {
		// Use the implemented remove method to find and remove(if exists) the item in
		// question.
		ramMemory.remove(key);
		valuesRepository.remove(key);
	}

}
