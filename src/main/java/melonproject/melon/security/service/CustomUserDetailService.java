package melonproject.melon.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.repository.user.MemberInfoRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberInfoRepository mRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return createUserDetails(mRepo.findByMiId(username)); //자동실행 메소드
    }
    public UserDetails createUserDetails(MemberInfoEntity member) {
        return User.builder().username(member.getMiId())
                .password(passwordEncoder.encode(member.getMiPwd()))
                .roles(member.getMiRole())
                .build(); //저장
    }
}

