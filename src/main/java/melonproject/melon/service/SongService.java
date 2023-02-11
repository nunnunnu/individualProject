package melonproject.melon.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.repository.artist.song.SongInfoRepository;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongInfoRepository sognRepo;

    public Map<String, Object> songAdd(){
        Map<String, Object> map = new LinkedHashMap<>();



        return map;
    }
}
