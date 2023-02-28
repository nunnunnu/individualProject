package melonproject.melon.vo.song;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SongAddVO {
    private String name;
    private  Long album;
    private  Long genre;
    private  Boolean title; //타이틀 여부
    private  Integer order;
    private  String lyrics;
    private  LocalDate regDt;
}
