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

		/*
		 * IDao<Long,DataModel<Integer>> dao = new
		 * DaoFileImpl<>("C:\\study\\javaTest.txt"); dao.save(new
		 * DataModel<Integer>((long)1,2));
		 * System.out.println(dao.find((long)1).getContent());
		 */

		File pagesStorage = new File("C:\\study\\javaTest.txt");
		// If file does not exist it will create it,otherwise will just use the existing
		// one
/*		try {
			pagesStorage.createNewFile();
			HashMap<Long, DataModel<Integer>> map = new HashMap<>();
			map.put((long) 1, new DataModel<Integer>((long) 1, 2));
			FileOutputStream os = new FileOutputStream(pagesStorage, false);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(map);
			oos.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			pagesStorage.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IAlgoCache<Long, DataModel<Integer>> algo = new LRUAlgoCacheImpl<Long, DataModel<Integer>>(3);
		IDao<Long, DataModel<Integer>> dao = new DaoFileImpl<>("C:\\study\\javaTest.txt");
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
