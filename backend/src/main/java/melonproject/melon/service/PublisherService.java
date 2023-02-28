package melonproject.melon.service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.repository.info.PublisherInfoRepository;

@RequiredArgsConstructor
public class PublisherService {
    private final PublisherInfoRepository pubRepo;
}
