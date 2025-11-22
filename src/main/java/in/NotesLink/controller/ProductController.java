package in.NotesLink.controller;

import in.NotesLink.model.Product;
import in.NotesLink.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return  new ResponseEntity<>(service.getAllProducts(),HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<String> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try {
            service.addProduct(product, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully!");
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        return new ResponseEntity<>(service.getProductById(id),HttpStatus.OK);
    }

    @GetMapping("product/{id}/image")
    public ResponseEntity<byte[]> getProductImageById(@PathVariable int id){
        Product product = service.getProductById(id);
        return  ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(product.getImageData());
    }

    @PutMapping("product/{id}")
    public ResponseEntity<String> updateProductById(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) throws IOException {
        Product product1;
        try {
            product1 = service.updateProductById(id, product, imageFile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("IOException Found");
        }
        if (product1 == null) {
            return ResponseEntity.badRequest().body("Product Received is Null");
        }else{
            return  ResponseEntity.ok().body("Product Updated Successfully");
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable int id){
        Product product = service.getProductById(id);
        if(product != null) {
            service.deleteProductById(id);
            return ResponseEntity.ok().body("Product Deleted Successfully");
        }else{
            return  ResponseEntity.badRequest().body("Product Not Available to Delete");
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        return new ResponseEntity<>(service.searchProductByKeyword(keyword), HttpStatus.OK );
    }

}
