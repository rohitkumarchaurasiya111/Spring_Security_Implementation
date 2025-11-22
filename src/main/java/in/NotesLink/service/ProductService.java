package in.NotesLink.service;

import in.NotesLink.model.Product;
import in.NotesLink.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void addProduct(Product product, MultipartFile imageFile) throws IOException {

        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        repository.save(product);

    }

    public Product getProductById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Product updateProductById(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getName());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return repository.save(product);
    }

    public void deleteProductById(int id) {
        repository.deleteById(id);
    }

    public List<Product> searchProductByKeyword(String keyword) {
        return repository.searchProductByKeyword(keyword);
    }
}
