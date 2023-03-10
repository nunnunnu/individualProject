package melonproject.melon.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.playlist.PlayListInfoEntity;
import melonproject.melon.entity.user.playlist.PlayListSongEntity;
import melonproject.melon.error.custom.MemberNotFound;
import melonproject.melon.error.custom.NoContentException;
import melonproject.melon.error.custom.NotFoundPlaylistException;
import melonproject.melon.error.custom.NotFoundSongException;
import melonproject.melon.error.custom.RequiredValueOmission;
import melonproject.melon.repository.artist.song.SongInfoRepository;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.repository.user.playlist.PlayListInfoRepository;
import melonproject.melon.repository.user.playlist.PlayListSongRepository;
import melonproject.melon.vo.playlist.PlayListInfoVO;
@Service
@RequiredArgsConstructor
public class PlayListService {
    
    private final MemberInfoRepository mRepo;
    private final PlayListInfoRepository pRepo;
    private final SongInfoRepository sRepo;
    private final PlayListSongRepository psRepo;

    public Map<String, Object> createPlayList(UserDetails userDetails, String name) {
        Map<String, Object> map = new LinkedHashMap<>();
        if(!StringUtils.hasText(name)){
            throw new RequiredValueOmission();
        }
        MemberInfoEntity member = mRepo.findByMiId(userDetails.getUsername());
        if(member==null){
            throw new MemberNotFound();
        }
        PlayListInfoEntity playlist = new PlayListInfoEntity(null, name, member, null);
        
        pRepo.save(playlist);

        map.put("status", false);
        map.put("message", "저장성공");
        map.put("code", HttpStatus.OK);

        return map;
    }
    public List<PlayListInfoVO> showMyPlaylist(UserDetails userDetails){
        MemberInfoEntity member = mRepo.findByMiId(userDetails.getUsername());
        if(member==null){
            throw new MemberNotFound();
        }
        List<PlayListInfoEntity> playlist = pRepo.findByMember(member);
        if(playlist.size()==0){
            throw new NoContentException();
        }

        return playlist.stream().map(p->new PlayListInfoVO(p)).collect(Collectors.toList());
    }
    public Map<String, Object> playlistAddSong(UserDetails userDetails, Long playlistseq, Long songSeq){
        MemberInfoEntity member = mRepo.findByMiId(userDetails.getUsername());
        if(member==null){
            throw new MemberNotFound();
        }
        PlayListInfoEntity playlist = pRepo.findById(playlistseq).orElseThrow(()-> new NotFoundPlaylistException());

        SongInfoEntity song = sRepo.findById(songSeq).orElseThrow(()->new NotFoundSongException());
        

        PlayListSongEntity entity = new PlayListSongEntity(null, playlist, song, playlist.getSongs().size()+1);
        psRepo.save(entity);

        Map<String, Object> map = new LinkedHashMap<>();

        map.put("status", true);
        map.put("message", "저장완료");

        return map;
    }
}
