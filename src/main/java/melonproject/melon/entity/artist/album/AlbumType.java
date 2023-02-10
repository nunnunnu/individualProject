package melonproject.melon.entity.artist.album;

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

    public String getName(){return name;}
}
