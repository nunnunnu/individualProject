package melonproject.melon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import melonproject.melon.repository.artist.ArtistConnectionRepository;
import melonproject.melon.repository.artist.ArtistGroupInfoRepository;
import melonproject.melon.repository.artist.ArtistInfoRepository;
import melonproject.melon.repository.artist.ArtistSoloInfoRepository;
import melonproject.melon.repository.artist.album.AlbumCommentRepository;
import melonproject.melon.repository.artist.album.AlbumGradeRepository;
import melonproject.melon.repository.artist.album.AlbumInfoRepository;
import melonproject.melon.repository.artist.song.SongCreatorRepository;
import melonproject.melon.repository.artist.song.SongFileRepository;
import melonproject.melon.repository.artist.song.SongInfoRepository;
import melonproject.melon.repository.info.AgencyInfoRepository;
import melonproject.melon.repository.info.GenreInfoRepository;
import melonproject.melon.repository.info.PublisherInfoRepository;
import melonproject.melon.repository.info.TicketInfoRepository;
import melonproject.melon.repository.user.ArtistFanRepository;
import melonproject.melon.repository.user.HistoryPlayRepository;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.repository.user.SongLikesRepository;
import melonproject.melon.repository.user.TicketMemberRepository;
import melonproject.melon.repository.user.playlist.PlayListInfoRepository;
import melonproject.melon.repository.user.playlist.PlayListSongRepository;

@SpringBootTest
class MelonApplicationTests {

	@Autowired AlbumCommentRepository acRepo;
	@Autowired AlbumGradeRepository agRepo;
	@Autowired AlbumInfoRepository albumRepo;
	@Autowired SongCreatorRepository scRepo;
	@Autowired SongFileRepository sfRepo;
	@Autowired SongInfoRepository siRepo;
	@Autowired ArtistConnectionRepository artcRepo;
	@Autowired ArtistGroupInfoRepository artgRepo;
	@Autowired ArtistSoloInfoRepository artsRepo;
	@Autowired ArtistInfoRepository artRepo;
	@Autowired AgencyInfoRepository aiRepo;
	@Autowired GenreInfoRepository giRepo;
	@Autowired PublisherInfoRepository piRepo;
	@Autowired TicketInfoRepository tiRepo;
	@Autowired PlayListInfoRepository pliRepo;
	@Autowired PlayListSongRepository plsRepo;
	@Autowired ArtistFanRepository artfRepo;
	@Autowired HistoryPlayRepository hpRepo;
	@Autowired MemberInfoRepository miRepo;
	@Autowired SongLikesRepository slRepo;
	@Autowired TicketMemberRepository tmRepo;

	@Test
	void contextLoads() {
		// System.out.println(artcRepo.findAll().get(0));
		System.out.println(artgRepo.findAll().get(0).getArtName());
		System.out.println(artsRepo.findAll().get(0).getArtName());
		// System.out.println(artRepo.findAll().get(0));
	}

}
