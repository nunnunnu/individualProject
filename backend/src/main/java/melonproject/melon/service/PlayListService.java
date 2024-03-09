package melonproject.melon.service;

import static org.springframework.util.StringUtils.*;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.playlist.PlayListInfoEntity;
import melonproject.melon.entity.user.playlist.PlayListSongEntity;
import melonproject.melon.error.custom.MemberNotFound;
import melonproject.melon.error.custom.NoContentException;
import melonproject.melon.error.custom.NotFoundPlayListSong;
import melonproject.melon.error.custom.NotFoundPlaylistException;
import melonproject.melon.error.custom.NotFoundSongException;
import melonproject.melon.error.custom.RequiredValueOmission;
import melonproject.melon.reader.MemberReader;
import melonproject.melon.repository.artist.song.SongInfoRepository;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.repository.user.playlist.PlayListInfoRepository;
import melonproject.melon.repository.user.playlist.PlayListSongRepository;
import melonproject.melon.vo.playlist.PlayListInfoVO;
import melonproject.melon.vo.song.SongInfoVO;
@Service
@RequiredArgsConstructor
@Slf4j
public class PlayListService {
    
    private final MemberInfoRepository mRepo;
    private final PlayListInfoRepository pRepo;
    private final SongInfoRepository sRepo;
    private final PlayListSongRepository psRepo;
    private final MemberReader memberReader;

    public Map<String, Object> createPlayList(UserDetails userDetails, String name) {
        Map<String, Object> map = new LinkedHashMap<>();
        MemberInfoEntity member = memberReader.findByMemberIdNotFoundError(userDetails.getUsername());
        PlayListInfoEntity playlist = new PlayListInfoEntity(null, name, member, null);
        
        pRepo.save(playlist);

        map.put("status", false);
        map.put("message", "저장성공");
        map.put("code", HttpStatus.OK);

        return map;
    }
    public List<PlayListInfoVO> showMyPlaylist(UserDetails userDetails){
        MemberInfoEntity member = memberReader.findByMemberIdNotFoundError(userDetails.getUsername());
        if(member==null){
            throw new MemberNotFound();
        }
        List<PlayListInfoEntity> playlist = pRepo.findByMemberOrderByCreatedDate(member);
        if(CollectionUtils.isEmpty(playlist)){
            throw new NoContentException();
        }

        return playlist.stream().map(PlayListInfoVO::new).collect(Collectors.toList());
    }
    public Map<String, Object> playlistAddSong(UserDetails userDetails, Long playlistseq, Long songSeq){
        MemberInfoEntity member = memberReader.findByMemberIdNotFoundError(userDetails.getUsername());
        if(member==null){
            throw new MemberNotFound();
        }
        PlayListInfoEntity playlist = pRepo.findById(playlistseq).orElseThrow(NotFoundPlaylistException::new);

        SongInfoEntity song = sRepo.findById(songSeq).orElseThrow(NotFoundSongException::new);
        

        PlayListSongEntity entity = new PlayListSongEntity(null, playlist, song, playlist.getSongs().size()+1);
        psRepo.save(entity);

        Map<String, Object> map = new LinkedHashMap<>();

        map.put("status", true);
        map.put("message", "저장완료");

        return map;
    }
    public Map<String, Object> showPlayListSongList(Long seq, UserDetails userDetails){
        Map<String, Object> map = new LinkedHashMap<>();
        MemberInfoEntity member = memberReader.findByMemberIdNotFoundError(userDetails.getUsername());
        if(member==null){
            throw new MemberNotFound();
        }

        PlayListInfoEntity playlist = pRepo.findByPlayiSeqAndMember(seq, member);
        if(playlist==null){
            throw new NotFoundPlaylistException();
        }

        List<PlayListSongEntity> songInPlayList = psRepo.findByPlayOrderByPsOrder(playlist);

        if(CollectionUtils.isEmpty(songInPlayList)){
            throw new NoContentException();
        }
        List<SongInfoVO> result = songInPlayList.stream().map(SongInfoVO::new).toList();

        map.put("status", true);
        map.put("message", "조회완료");
        map.put("data", result);

        return map;
    }
    public Map<String, Object> delPlaylist(Long seq, UserDetails userDetails){
        Map<String, Object> map = new LinkedHashMap<>();
        MemberInfoEntity member = memberReader.findByMemberIdNotFoundError(userDetails.getUsername());
        PlayListInfoEntity playlist = pRepo.findByPlayiSeqAndMember(seq, member);
        if(playlist==null){
            throw new NotFoundPlaylistException();
        }
        List<PlayListSongEntity> songs = psRepo.findByPlay(playlist);
        psRepo.deleteAll(songs);
        pRepo.delete(playlist);
        map.put("status", false);
        map.put("message", "삭재했습니다.");


        return map;
    }
    public Map<String, Object> delPlaylistSong(UserDetails userDetails, Long playseq, Integer order){
        Map<String, Object> map = new LinkedHashMap<>();

        MemberInfoEntity member = memberReader.findByMemberIdNotFoundError(userDetails.getUsername());
        PlayListInfoEntity playlist = pRepo.findByPlayiSeqAndMember(playseq, member);
        if(ObjectUtils.isEmpty(playlist)){
            throw new NotFoundPlaylistException();
        }

        PlayListSongEntity entity = psRepo.findByPlayAndPsOrder(playlist, order);
        if(ObjectUtils.isEmpty(entity)){
            throw new NotFoundPlayListSong();
        }

        psRepo.delete(entity);

        psRepo.playlistOrderSet(playlist, order);

        map.put("status", true);
        map.put("message", "삭제성공");

        return map;
    }
}
