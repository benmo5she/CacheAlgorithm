package com.hit.memory;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
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
	public void init() {
		try {
			pagesStorage.delete();
			pagesStorage.createNewFile();
			dao = new DaoFileImpl<>(pagesStorage.getAbsolutePath());
			Random rand = new Random();
			int content = rand.nextInt();
			Long id = rand.nextLong();
			initItem = new DataModel<Integer>(id, content);
		} catch (IOException e) {
			Assert.fail(e.getMessage());
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
		dao.save(initItem);
		DataModel<Integer>[] resultArray = null;
		try {
			Long originalId = initItem.getDataModelId();
			resultArray = testCU.getDataModels(new Long[] { originalId, originalId + 1, originalId + 2 });
			Assert.assertEquals(1, resultArray.length);
			Assert.assertEquals((Integer) initItem.getContent(), (Integer) resultArray[0].getContent());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
