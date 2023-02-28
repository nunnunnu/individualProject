package melonproject.melon.entity.artist.song;

import com.fasterxml.jackson.annotation.JsonCreator;

import melonproject.melon.entity.artist.album.AlbumType;

public enum SongWirter {
    작사,작곡,편곡;

    @JsonCreator
    public static SongWirter from(String name) {
        // for (AlbumType type : AlbumType.values()) {
        //     if (type.getName().equals(name)) {
        //         return type;
        //     }
        // }
        // return null;
        try {
            return SongWirter.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}
