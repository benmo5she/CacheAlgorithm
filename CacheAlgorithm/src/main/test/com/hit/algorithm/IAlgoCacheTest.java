package com.hit.algorithm;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class IAlgoCacheTest {

	
	private void prepareMemory(IAlgoCache<Integer,String> result,int capacity)
	{
		for(int i=1;i<=capacity;i++)
		{
			result.putElement(i, Integer.toString(i));	
		}
	}	
	
	@Test
	public void testLRUPut()
	{
		LRUAlgoCacheImpl<Integer, String> testLRU = new LRUAlgoCacheImpl<>(3);
		prepareMemory(testLRU,3);
		testLRU.putElement(4, "4");
		Assert.assertEquals(null, testLRU.getElement(1));
		Assert.assertEquals("4", testLRU.getElement(4));
	}
	
	@Test
	public void testLRUGet()
	{
		LRUAlgoCacheImpl<Integer, String> testLRU = new LRUAlgoCacheImpl<>(3);
		prepareMemory(testLRU,3);
		Assert.assertEquals("1", testLRU.getElement(1));
		Assert.assertEquals("2", testLRU.getElement(2));
		Assert.assertEquals("3", testLRU.getElement(3));
		Assert.assertEquals(null, testLRU.getElement(4));
	}
	
	@Test
	public void testLRURemove()
	{
		LRUAlgoCacheImpl<Integer, String> testLRU = new LRUAlgoCacheImpl<>(3);
		prepareMemory(testLRU,3);
		testLRU.removeElement(2);
		Assert.assertEquals("1", testLRU.getElement(1));
		Assert.assertEquals(null, testLRU.getElement(2));
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void testLMRUPut()
	{
		MRUAlgoCacheImpl<Integer, String> testMRU = new MRUAlgoCacheImpl<>(3);
		prepareMemory(testMRU,3);
		testMRU.putElement(4, "4");
		Assert.assertEquals(null, testMRU.getElement(3));
		Assert.assertEquals("4", testMRU.getElement(4));
	}
	
	@Test
	public void testMRUGet()
	{
		MRUAlgoCacheImpl<Integer, String> testMRU = new MRUAlgoCacheImpl<>(3);
		prepareMemory(testMRU,3);
		Assert.assertEquals("1", testMRU.getElement(1));
		Assert.assertEquals("2", testMRU.getElement(2));
		Assert.assertEquals("3", testMRU.getElement(3));
		Assert.assertEquals(null, testMRU.getElement(4));
	}
	
	@Test
	public void testMRURemove()
	{
		MRUAlgoCacheImpl<Integer, String> testMRU = new MRUAlgoCacheImpl<>(3);
		prepareMemory(testMRU,3);
		testMRU.removeElement(2);
		Assert.assertEquals("1", testMRU.getElement(1));
		Assert.assertEquals(null, testMRU.getElement(2));
	}
	
	
	
}
