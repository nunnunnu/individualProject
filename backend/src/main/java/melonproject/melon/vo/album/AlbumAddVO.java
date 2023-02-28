package melonproject.melon.vo.album;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import melonproject.melon.entity.artist.album.AlbumType;
@Data
public class AlbumAddVO {
    private LocalDate regDt;
    // private Long genre;
    private Long publisher;
    private Long agency;
    private AlbumType type;
    // private MultipartFile cover;
    private Long artist;
    private String name;
}
