package jpa2.vn.Dao;

import java.util.List; // Import cho List
import jpa2.vn.entity.Category; // Import cho Category


public interface ICategoryDao {

	int count();

	List<Category> findAll(int page, int pageSize);

	List<Category> findByCategoryname(String catname);

	List<Category> findAll();

	Category findById(int cateid);

	void delete(int cateid) throws Exception;

	void update(Category category);

	void insert(Category category);

}