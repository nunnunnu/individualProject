package melonproject.melon.entity.artist.album;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AlbumType {
    정규("LP"),
    싱글("SINGLE"),
    EP("미니"),
    디지털싱글("DIGITAL SINGLE"),
    리패키지("스페셜에디션"),
    리믹스("remix"),
    믹스테잎("mixtape"),
    옴니버스("스플릿"),
    라이브("콘서트");

    private String name;

    AlbumType(String name){this.name=name;}

    @JsonValue
    public String getName(){return name;}

    @JsonCreator
    public static AlbumType from(String name) {
        // for (AlbumType type : AlbumType.values()) {
        //     if (type.getName().equals(name)) {
        //         return type;
        //     }
        // }
        // return null;
        try {
            return AlbumType.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
    
}
