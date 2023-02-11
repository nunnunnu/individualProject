package melonproject.melon.service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.repository.info.GenreInfoRepository;

@RequiredArgsConstructor
public class GenreInfoService {
    private final GenreInfoRepository genreRepo;
}
