package shop.mtcoding.blogv2._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import shop.mtcoding.blogv2._core.interceptor.LoginInterceptor;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/images/**")
            .addResourceLocations("file:"+"./images/")
            .setCachePeriod(10)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/updateCorporationForm", "/corporationResume", "/corporationSupport/{{sessionUser.id}}", "/corporationSeeker")
                .addPathPatterns("/updateSeekerForm", "/seekerResumeForm", "/seekerSupport", "/seekerCompanies")
                .addPathPatterns("/applyNotice/**")
                .addPathPatterns("/board/**/updateForm", "/board/saveBoard")
                .excludePathPatterns("/board")
                .excludePathPatterns("/board/{id:[0-9]+}");
    }    
}
