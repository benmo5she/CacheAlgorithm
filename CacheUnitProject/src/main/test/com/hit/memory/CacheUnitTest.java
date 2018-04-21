package com.hit.memory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

public class CacheUnitTest {
	@Test
	public void testLRUPut() {
		
		IDao<Long,DataModel<Integer>> dao = new DaoFileImpl<>("C:\\study\\javaTest.txt");
		dao.save(new DataModel<Integer>((long)1,2));
		System.out.println(dao.find((long)1).getContent());
		
		
/*		File pagesStorage = new File("C:\\study\\javaTest.txt");
		// If file does not exist it will create it,otherwise will just use the existing
		// one
		try {
			pagesStorage.createNewFile();

		HashMap<Long, DataModel<Integer>> map = new HashMap<>();
		//FileInputStream fis = new FileInputStream(pagesStorage);
		//ObjectInputStream ois = new ObjectInputStream(fis);
		//map = (HashMap) ois.readObject();
		//ois.close();
		//fis.close();
		map.put((long)1, new DataModel<Integer>((long)1,2));
		FileOutputStream  os = new FileOutputStream(pagesStorage, false);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(map);
        oos.close();
        os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
/*		IAlgoCache<Long, DataModel<Integer>> algo = new LRUAlgoCacheImpl<Long, DataModel<Integer>>(3);
		IDao<Long,DataModel<Integer>> dao = new DaoFileImpl<>("C:\\study\\javaTest.txt");
		CacheUnit<Integer> testCU = new CacheUnit<Integer>(algo, dao);		
		dao.save(new DataModel<Integer>((long) 1, 2));*/
	}
}
