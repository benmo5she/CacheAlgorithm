package com.hit.memory;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

public class CacheUnitTest {
	@Test
	public void testLRUPut() {
		File pagesStorage = new File("C:\\study\\javaTest.txt");
		try {
			pagesStorage.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IAlgoCache<Long, DataModel<Integer>> algo = new LRUAlgoCacheImpl<Long, DataModel<Integer>>(3);
		IDao<Long, DataModel<Integer>> dao = new DaoFileImpl<>(pagesStorage.getAbsolutePath());
		CacheUnit<Integer> testCU = new CacheUnit<Integer>(algo, dao);
		dao.save(new DataModel<Integer>((long) 1, 2));
		DataModel<Integer>[] resultArray = null;
		try {
			resultArray = testCU.getDataModels(new Long[] { (long) 1,(long) 2,(long) 3 });
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Assert.assertTrue(resultArray.length == 2 && resultArray[0].getContent() == 2);
		Assert.assertEquals((Integer)2, (Integer)resultArray[0].getContent());
		Assert.assertEquals((Integer)1, (Integer)resultArray.length);
	}
}
