package melonproject.melon.security.service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.repository.user.MemberInfoRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberInfoRepository memberMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return createUserDetails(memberMapper.findByMiId(username));
    }
    public UserDetails createUserDetails(MemberInfoEntity member) {
        return User.builder().username(member.getMiId())
                .password(passwordEncoder.encode(member.getMiPwd()))
                .roles(member.getMiRole())
                .build();
    }
}

