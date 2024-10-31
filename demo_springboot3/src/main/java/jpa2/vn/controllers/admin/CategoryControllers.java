package jpa2.vn.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jpa2.vn.entity.Category;
import jpa2.vn.services.ICategoryService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryControllers {

    private final ICategoryService cateService;

    @Autowired
    public CategoryControllers(ICategoryService cateService) {
        this.cateService = cateService;
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        List<Category> list = cateService.findAll();
        model.addAttribute("listcate", list);
        return "admin/category-list"; // This corresponds to /views/admin/category-list.jsp
    }
}
