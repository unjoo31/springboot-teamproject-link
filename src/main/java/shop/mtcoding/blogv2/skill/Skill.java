package shop.mtcoding.blogv2.skill;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.notice.Notice;
import shop.mtcoding.blogv2.resume.Resume;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "skill_tb")
@Entity
public class Skill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String css;
    private String springboot;
    private String jps;
    private String android;
    private String ajax;
    private String flutter;
    private String html;
    private String mysql;
    private String java;
    private String javascript;
    private String git;
    private String aws;

    @Builder
    public Skill(Integer id, String css, String springboot, String jps, String android, String ajax, String flutter,
            String html, String mysql, String java, String javascript, String git, String aws) {
        this.id = id;
        this.css = css;
        this.springboot = springboot;
        this.jps = jps;
        this.android = android;
        this.ajax = ajax;
        this.flutter = flutter;
        this.html = html;
        this.mysql = mysql;
        this.java = java;
        this.javascript = javascript;
        this.git = git;
        this.aws = aws;
    }       
}
