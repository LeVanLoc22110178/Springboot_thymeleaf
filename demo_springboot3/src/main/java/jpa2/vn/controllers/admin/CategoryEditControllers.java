package jpa2.vn.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jpa2.vn.entity.Category;
import jpa2.vn.services.ICategoryService;
import jpa2.vn.utils.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/categories")
public class CategoryEditControllers {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/edit")
    public String editCategory(@RequestParam("id") int id, Model model) {
        Category category = categoryService.findById(id);
        
        if (category != null) {
            model.addAttribute("cate", category);
            return "admin/category-edit"; // Thymeleaf template
        } else {
            return "error/404"; // Return an error page if not found
        }
    }

    @PostMapping("/edit")
    public String updateCategory(@RequestParam("categoryId") int categoryId,
                                  @ModelAttribute("cate") Category category,
                                  @RequestParam("image") MultipartFile imageFile,
                                  Model model) throws IOException {
        // Set the categoryId to the category object
        

        // Handle file upload
        if (imageFile != null && !imageFile.isEmpty()) {
            // Generate a file name and save the file
            String fileName = Paths.get(imageFile.getOriginalFilename()).getFileName().toString();
            String uploadPath = Constant.UPLOAD_DIRECTORY + File.separator + fileName;

            // Save the file on disk
            imageFile.transferTo(new File(uploadPath));

            // Update the image path in the category
            category.setImages(fileName);
        }

        // Check if categoryName is set
        if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
            model.addAttribute("errorMessage", "Category Name cannot be null or empty.");
            model.addAttribute("cate", category); // Preserve the current category data
            return "admin/category-edit"; // Return to the edit page with an error message
        }

        // Update the category in the database
        categoryService.update(category);
        
        // Redirect to the category list
        return "redirect:/admin/categories";
    }
}
