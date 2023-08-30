package shop.mtcoding.blogv2.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.boomark.Bookmark;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "user_tb")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String email;

    private Boolean companyUser;

    @Column(nullable = true)
    private String picUrl;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String phonenumber;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private Date age;
    
    @Column(nullable = true)
    private String business;

    @Column(nullable = true)
    private String form;

    @Column(nullable = true)
    private String performance;

    @Builder
    public User(Integer id, String username, String password, String email, Boolean companyUser, String picUrl,
            String name, String phonenumber, String address, Date age, String business, String form,
            String performance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.companyUser = companyUser;
        this.picUrl = picUrl;
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
        this.age = age;
        this.business = business;
        this.form = form;
        this.performance = performance;
    }   
}
