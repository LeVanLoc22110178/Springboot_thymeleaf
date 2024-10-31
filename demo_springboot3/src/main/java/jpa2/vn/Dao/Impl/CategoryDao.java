package jpa2.vn.Dao.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jpa2.vn.Dao.ICategoryDao;
import jpa2.vn.entity.Category;

import java.util.List;

@Repository
public class CategoryDao implements ICategoryDao {

    private final EntityManager entityManager;

    @Autowired
    public CategoryDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void insert(Category category) {
        entityManager.persist(category);
    }

    @Override
    @Transactional
    public void update(Category category) {
        String jpql = "UPDATE Category c SET c.categoryName = :name, c.images = :images, c.status = :status WHERE c.categoryId = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("name", category.getCategoryName());
        query.setParameter("images", category.getImages());
        query.setParameter("status", category.getStatus());
        query.setParameter("id", category.getCategoryId());
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void delete(int cateid) throws Exception {
        Category cate = entityManager.find(Category.class, cateid);
        if (cate != null) {
            entityManager.remove(cate);
        } else {
            throw new Exception("Không tìm thấy danh mục");
        }
    }

    @Override
    public Category findById(int cateid) {
        return entityManager.find(Category.class, cateid);
    }

    @Override
    public List<Category> findAll() {
        TypedQuery<Category> query = entityManager.createNamedQuery("Category.findAll", Category.class);
        return query.getResultList();
    }

    @Override
    public List<Category> findByCategoryname(String catname) {
        String jpql = "SELECT c FROM Category c WHERE c.category_name LIKE :catname"; // Đảm bảo tên trường đúng
        TypedQuery<Category> query = entityManager.createQuery(jpql, Category.class);
        query.setParameter("catname", "%" + catname + "%");
        return query.getResultList();
    }

    @Override
    public List<Category> findAll(int page, int pageSize) {
        TypedQuery<Category> query = entityManager.createNamedQuery("Category.findAll", Category.class);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public int count() {
        String jpql = "SELECT count(c) FROM Category c";
        Query query = entityManager.createQuery(jpql);
        return ((Long) query.getSingleResult()).intValue();
    }
}
