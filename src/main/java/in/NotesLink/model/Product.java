package in.NotesLink.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data  //Creates Getter and Setter for all Variables
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //This will generate the ID Automatically
    private int id;
    private String name;
    private  String description;
    private  String brand;
    private BigDecimal price;
    private  String category;
    private Date releaseDate;
    private Boolean productAvailable;
    private int stockQuantity;

    //These three below fields are specifically for storing the image
    private String imageName;
    private String imageType;
    //We can store the image in some cloud Storage and then can get the URL but for now we are using this
    @Lob
    private byte[] imageData;

}
