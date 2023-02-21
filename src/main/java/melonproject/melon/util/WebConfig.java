package melonproject.melon.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
     @Override
     public void addCorsMappings(CorsRegistry registry){
          registry.addMapping("/**") //모든 매핑 경로에 대해(특정 메핑만 허용이면 /api/* 이런식으로 적어주면됨)
          .allowedOrigins("*") //모든 사용자에 대해 (특정아이피만 허용이면 여기 아이피넣으면됨)
          .allowedMethods("*"); //GET, POST, PUT, DELETE, PATCH, OPTION 모든 메소드를 허용함.
     }
}
