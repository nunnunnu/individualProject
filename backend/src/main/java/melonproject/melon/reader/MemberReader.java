package melonproject.melon.reader;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.error.custom.MemberNotFound;
import melonproject.melon.repository.user.MemberInfoRepository;

@Service
@RequiredArgsConstructor
public class MemberReader {
	private final MemberInfoRepository memberRepo;

	public MemberInfoEntity findByMemberIdNotFoundError(String memberId) {
		return memberRepo.findByMiId(memberId).orElseThrow(MemberNotFound::new);
	}
}
