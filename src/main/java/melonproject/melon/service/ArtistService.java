package melonproject.melon.service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.repository.artist.ArtistInfoRepository;

@RequiredArgsConstructor
public class ArtistService {
    private final ArtistInfoRepository aRepo;
}
