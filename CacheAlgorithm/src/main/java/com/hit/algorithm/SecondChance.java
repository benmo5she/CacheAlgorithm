package com.hit.algorithm;

import java.util.*;

public class SecondChance<K, V> extends AbstractAlgoCache<K, V> {

	/// Create this class to store the bit status of each of our keys in the memory
	/// without the need to create separate
	// Container or access to a the disk
	// The ram will be a container which contain KeyValuePair<K,Boolean> elements
	// When O(K) serves as the index, and the P(Boolean) serves as the bit value of
	/// said node
	private class KeyValuePair<O, P> {
		private O index;
		private P value;

		public O getIndex() {
			return index;
		}

		public void setIndex(O index) {
			this.index = index;
		}

		public P getValue() {
			return value;
		}

		public void setValue(P value) {
			this.value = value;
		}
	}

	// As second chance algorithm uses FIFO,we should use a queue to store our
	// values(using KeyValuePair as described above)
	// Use hashmap to store the disk values for quick access to values
	PriorityQueue<KeyValuePair<K, Boolean>> ramMemory;
	HashMap<K, V> diskMemory;

	SecondChance(int capacity) {
		super(capacity);
		diskMemory = new HashMap<>();
		ramMemory = new PriorityQueue<>();
	}

	@Override
	public V getElement(K key) {
		V resultObj = null;
		// Iterate over the queue and look for the key.
		for (KeyValuePair<K, Boolean> foundKey : ramMemory) {
			// If we found a match, we need to set the node bit to be true as the algorithm
			// dictates
			// break the loop as theres no need to continue
			if (foundKey.getIndex().equals(key)) { //
				foundKey.setValue(true);
				resultObj = diskMemory.get(foundKey);
				break;
			}
		}
		return resultObj;
	}

	@Override
	public V putElement(K key, V value) {
		// Is the queue full? if so we need to remove one element using the algorithm.
		// Iterate over the queue(polling) to find the first node which has its bit
		// value to be false.
		// While meeting elements with bit value of true, set them to false and put them
		// at the back of the line as the algorithm dictates
		// to provide them with a second chance
		// When met a node with bit value of false, remove it from the queue to make
		// space for the new node.
		if (ramMemory.size() == _capacity) {
			KeyValuePair<K, Boolean> head = ramMemory.poll();
			while (head.getValue() == true) {
				head.setValue(false);
				ramMemory.add(head);
				head = ramMemory.poll();
			}
		}
		// Insert the new value to be inserted both to the ram and the disk
		ramMemory.add(createNewRamNode(key));
		diskMemory.put(key, value);
		return value;
	}

	private KeyValuePair<K, Boolean> createNewRamNode(K key) {
		KeyValuePair<K, Boolean> newObject = new KeyValuePair<>();
		newObject.setIndex(key);
		newObject.setValue(false);
		return newObject;
	}

	@Override
	public void removeElement(K key) {
		// Iterate over the queue to find a matching node,if found one, remove it and
		// break the loop.
		for (KeyValuePair<K, Boolean> foundKey : ramMemory) {
			if (foundKey.getIndex().equals(key)) {
				ramMemory.remove(foundKey);
				diskMemory.remove(key);
				break;
			}
		}
	}

}
