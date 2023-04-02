package melonproject.melon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
     @Override
     public void addCorsMappings(CorsRegistry registry) {
          registry.addMapping("/**")
          .allowedOrigins("http://watermelonfront.s3-website.ap-northeast-2.amazonaws.com/")
          .allowedMethods(
               HttpMethod.GET.name(),
               HttpMethod.HEAD.name(),
               HttpMethod.POST.name(),
               HttpMethod.PUT.name(),
               HttpMethod.DELETE.name()
          )
          .allowedHeaders("*")
          .allowCredentials(true);
     }
}
