package melonproject.melon.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.security.provider.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;


}
