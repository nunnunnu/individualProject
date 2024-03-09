package melonproject.melon.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.album.AlbumCommentEntity;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.song.SoundQuality;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.error.custom.CommentInputException;
import melonproject.melon.error.custom.MemberNotFound;
import melonproject.melon.error.custom.NotFoundAlbumException;
import melonproject.melon.error.custom.NotFoundComment;
import melonproject.melon.reader.MemberReader;
import melonproject.melon.repository.artist.album.AlbumCommentRepository;
import melonproject.melon.repository.artist.album.AlbumInfoRepository;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.vo.comment.CommentInputVO;
import melonproject.melon.vo.comment.CommentListVO;
import melonproject.melon.vo.song.SongInfoVO;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final AlbumCommentRepository acRepo;
    private final MemberInfoRepository mRepo;
    private final AlbumInfoRepository aRepo;
    private final FileService fService;
    private final MemberReader memberReader;

    public Map<String, Object> albumCommentAdd(UserDetails userDetails, CommentInputVO data, BindingResult bindingResult, MultipartFile file) throws AmazonServiceException, SdkClientException, IOException{
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa"+data.getComment());
        if (bindingResult.hasErrors()) {
            List<String> errorMessage = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMessage.add(error.getDefaultMessage());
            });
            throw new CommentInputException(errorMessage);
        }
        AlbumCommentEntity parentComment =null;
        if(data.getParentSeq()!=null){
            parentComment = acRepo.findById(data.getParentSeq()).orElseThrow(()->new NotFoundComment());
        }

        MemberInfoEntity member = memberReader.findByMemberIdNotFoundError(userDetails.getUsername());

        AlbumInfoEntity album = aRepo.findById(data.getAlbum()).orElseThrow(()->new NotFoundAlbumException());

        AlbumCommentEntity comment;
        if(data.getParentSeq()==null && file!=null){
            comment = fService.saveCommentFile(file);
        }else{
            comment = new AlbumCommentEntity();
        }

        comment.setting(member, album, data.getComment(), parentComment);

        acRepo.save(comment);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", true);
        map.put("message", "저장성공");

        return map;
    }
    public Map<String, Object> getComment(Long albumSeq, Pageable page){
        Map<String, Object> map = new LinkedHashMap<>();

        AlbumInfoEntity album = aRepo.findById(albumSeq).orElseThrow(()->new NotFoundAlbumException());

        Page<AlbumCommentEntity> list = acRepo.findByAlbumAndParentIsNull(album, page);
        Page<CommentListVO> result = list.map(
                c->new CommentListVO(
                    c, acRepo.findByParent(c)
                ));
        
        map.put("status", true);
        map.put("message", "조회성공");
        map.put("data", result);
        return map;
    }
}
