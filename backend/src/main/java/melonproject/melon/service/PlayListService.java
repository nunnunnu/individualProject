package melonproject.melon.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
@Service
public class PlayListService {
    
    public Map<String, Object> createPlayList(UserDetails userDetails){
        Map<String, Object> map = new LinkedHashMap<>();

        return map;
    }
}
