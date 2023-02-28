package melonproject.melon.entity.artist.song;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SoundQuality {
    MP3, FLAC;

    @JsonCreator
    public static SoundQuality from(String name) {
        // for (AlbumType type : AlbumType.values()) {
        //     if (type.getName().equals(name)) {
        //         return type;
        //     }
        // }
        // return null;
        try {
            return SoundQuality.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}
