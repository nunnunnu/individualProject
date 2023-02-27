package melonproject.melon.security.vo;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
//임의로 만든거 
@Component
@ConfigurationProperties(prefix = "permission")
@Data
public class PermitSettings {
    String[] permitAllUrls;
}
