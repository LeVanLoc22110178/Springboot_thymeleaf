package jpa2.vn.services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jpa2.vn.Dao.ICategoryDao;
import jpa2.vn.entity.Category;
import jpa2.vn.services.ICategoryService;

@Service // Đánh dấu lớp này là một service
public class CategoryService implements ICategoryService {
    
    private final ICategoryDao cateDao;

    @Autowired // Tiêm dependency
    public CategoryService(ICategoryDao cateDao) {
        this.cateDao = cateDao;
    }

    @Override
    public int count() {
        return cateDao.count();
    }

    @Override
    public List<Category> findAll(int page, int pagesize) {
        return cateDao.findAll(page, pagesize);
    }

    @Override
    public List<Category> findByCategoryname(String catname) {
        return cateDao.findByCategoryname(catname);
    }

    @Override
    public List<Category> findAll() {
        return cateDao.findAll();
    }

    @Override
    public Category findById(int cateid) {
        return cateDao.findById(cateid);
    }

    @Override
    public void delete(int cateid) throws Exception {
        cateDao.delete(cateid);
    }

    @Override
    public void update(Category category) {
        cateDao.update(category);
    }

    @Override
    public void insert(Category category) {
        cateDao.insert(category);
    }
}