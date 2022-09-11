package hu.bme.aut.retelab2.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Ad {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private int price;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public void setPrice(int price) {this.price = price;}

    public int getPrice(){return price;}

    public LocalDateTime getCreatedAt(){return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt){this.createdAt = createdAt;}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
