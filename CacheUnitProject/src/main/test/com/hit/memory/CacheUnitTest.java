package com.hit.memory;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

public class CacheUnitTest {

	private static File pagesStorage = new File("C:\\study\\javaTest.txt");
	private static IDao<Long, DataModel<Integer>> dao = null;
	private static DataModel<Integer> initItem = null;
		
	@Before
	public void init()
	{
		try {
			pagesStorage.delete();
			pagesStorage.createNewFile();
			dao = new DaoFileImpl<>(pagesStorage.getAbsolutePath());
			Random rand = new Random();
			int content = rand.nextInt();
			Long id = rand.nextLong();
			initItem = new DataModel<Integer>(id, content); 
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Test
	public void testDAOSave() {		
		dao.save(initItem);
		DataModel<Integer> foundPage = dao.find(initItem.getDataModelId());
		Assert.assertEquals(initItem.getDataModelId(), foundPage.getDataModelId());
		Assert.assertEquals((Integer) initItem.getContent(), (Integer) foundPage.getContent());
	}

	@Test
	public void testDAOFind() {
		
		Assert.assertNull(dao.find(initItem.getDataModelId()));
		dao.save(initItem);
		Assert.assertNotNull(dao.find(initItem.getDataModelId()));
	}
	
	@Test
	public void testDAODelete() {
		dao.save(initItem);
		dao.delete(initItem);
		Assert.assertNull(dao.find(initItem.getDataModelId()));
	}

	@Test
	public void testCacheUnit() {
		IAlgoCache<Long, DataModel<Integer>> algo = new LRUAlgoCacheImpl<Long, DataModel<Integer>>(3);
		CacheUnit<Integer> testCU = new CacheUnit<Integer>(algo, dao);
		dao.save(new DataModel<Integer>((long) 1, 2));
		DataModel<Integer>[] resultArray = null;
		try {
			resultArray = testCU.getDataModels(new Long[] { (long) 1, (long) 2, (long) 3 });
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.assertEquals((Integer) 2, (Integer) resultArray[0].getContent());
		Assert.assertEquals((Integer) 1, (Integer) resultArray.length);
	}

}
