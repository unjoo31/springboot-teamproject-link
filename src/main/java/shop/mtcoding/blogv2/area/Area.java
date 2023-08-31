package shop.mtcoding.blogv2.area;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.notice.Notice;
import shop.mtcoding.blogv2.resume.Resume;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "area_tb")
@Entity
public class Area {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String busan;

    private String seoul;

    private String gyeonggi;

    private String daegu;

    @Builder
    public Area(Integer id, String busan, String seoul, String gyeonggi, String daegu) {
        this.id = id;
        this.busan = busan;
        this.seoul = seoul;
        this.gyeonggi = gyeonggi;
        this.daegu = daegu;
    }     
}
