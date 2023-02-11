package melonproject.melon.service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.repository.info.AgencyInfoRepository;

@RequiredArgsConstructor
public class AgencyService {
    private final AgencyInfoRepository agency;
}
