package com.hit.algorithm;

import java.util.HashMap;
import java.util.PriorityQueue;

public class LRUAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V> {
	// Use queue for this implementation as we need quick access to the least
	// recently used node, and queue helps us to achive that as we
	// have cheap access to the head of the queue(poll) and cheaper addition to the
	// queue.
	PriorityQueue<K> ramMemory;
	HashMap<K, V> valuesRepository = new HashMap<K, V>();

	public LRUAlgoCacheImpl(int capacity) {
		super(capacity);
		ramMemory = new PriorityQueue<>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public V getElement(K key) {
		// since we are accessing a node(if exists) we will need to change its order as
		// it was used right now,so it should go to the back of the queue.
		// to achieve that we will need to find said node and remove it,and add it back.
		if(ramMemory.remove(key))
		{
			ramMemory.add(key);
		}		
		return valuesRepository.get(key);
	}

	@Override
	public V putElement(K key, V value) {
		// If the queue is full,remove the current head of the queue.
		// Insert the new node to the ram..
		if (ramMemory.size() == _capacity) {
			valuesRepository.remove(ramMemory.poll());
		}
		ramMemory.add(key);
		valuesRepository.put(key, value);
		return value;
	}

	@Override
	public void removeElement(K key) {
		// Use the collection remove method to remove the object if exists.
		ramMemory.remove(key);
		valuesRepository.remove(key);

	}

}
