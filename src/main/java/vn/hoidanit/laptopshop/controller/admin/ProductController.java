package vn.hoidanit.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;

import org.springframework.web.bind.annotation.PostMapping;

import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;



@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProductPage(Model model, @ModelAttribute("newProduct") @Valid Product newProduct, BindingResult newProductBindingResult, @RequestParam("imgFile") MultipartFile file) {
        //Validate
        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String imageProduct = this.uploadService.handleSaveUploadFile(file, "product");
        
        newProduct.setImage(imageProduct);

        this.productService.handleSaveProduct(newProduct);
        
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetail(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getProductUpdatePage(Model model, @PathVariable long id) {
        Product currentProduct = this.productService.getProductById(id);
        model.addAttribute("product", currentProduct);
        model.addAttribute("id", id);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateProduct(@ModelAttribute("product") @Valid Product pr, BindingResult newProductBindingResult, @RequestParam("imgFile") MultipartFile file) {
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }

        Product curProduct = this.productService.getProductById(pr.getId());
        if (curProduct != null) {
            if (!file.isEmpty()) {
                String img = this.uploadService.handleSaveUploadFile(file, "product");
                curProduct.setImage(img);
            }

            curProduct.setName(pr.getName());
            curProduct.setPrice(pr.getPrice());
            curProduct.setQuantity(pr.getQuantity());
            curProduct.setDetailDesc(pr.getDetailDesc());
            curProduct.setShortDesc(pr.getShortDesc());
            curProduct.setFactory(pr.getFactory());
            curProduct.setTarget(pr.getTarget());

            this.productService.handleSaveProduct(curProduct);
        }

        return "redirect:/admin/product";
    }
    
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        Product product = new Product();
        product.setId(id);
        model.addAttribute("newProduct", product);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProductPage(Model model, @ModelAttribute("newProduct") Product newProduct) {
        this.productService.deleteProductById(newProduct.getId());
        return "redirect:/admin/product";
    }
}
