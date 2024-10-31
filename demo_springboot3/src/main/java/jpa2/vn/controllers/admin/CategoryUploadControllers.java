package jpa2.vn.controllers.admin;

import java.io.File;
import java.io.IOException;

import jpa2.vn.entity.Category;
import jpa2.vn.services.ICategoryService;
import jpa2.vn.services.Impl.CategoryService;
import jpa2.vn.utils.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CategoryUploadControllers {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryUploadControllers(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/categories/upload")
    public String showUploadForm() {
        return "admin/category-upload"; // Return the name of the Thymeleaf template
    }

    @PostMapping("/admin/categories/upload")
    public String handleUpload(
            @RequestParam("name-cate") String name,
            @RequestParam("status") String status,
            @RequestParam("image") MultipartFile file,
            Model model) throws IOException {

        // Validate category name
        if (name == null || name.isEmpty()) {
            model.addAttribute("error", "Category name cannot be empty");
            return "admin/error"; // Return the name of the error page template
        }

        // Create a new Category instance
        Category category = new Category();
        category.setCategoryName(name);
        category.setStatus(status);

        // Handle file upload
        if (file != null && !file.isEmpty()) {
            // Generate a file name and save the file
            String fileName = file.getOriginalFilename();
            String uploadPath = Constant.UPLOAD_DIRECTORY + File.separator + fileName;

            // Save the file on disk
            file.transferTo(new File(uploadPath));

            // Update the image path in the category
            category.setImages(fileName);
        }

        // Save the category
        categoryService.insert(category);

        // Redirect to category list
        return "redirect:/admin/categories"; // Redirect to the category list page
    }
}
