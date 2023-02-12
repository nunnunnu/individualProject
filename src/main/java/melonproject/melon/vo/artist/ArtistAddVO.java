package melonproject.melon.vo.artist;

import java.time.LocalDate;

import lombok.Data;
@Data
public class ArtistAddVO {
    private String name;
    private LocalDate debut;
    private String country;
    private Long agency;
    private String dtype;
    private LocalDate birth;
    private LocalDate split;
}
