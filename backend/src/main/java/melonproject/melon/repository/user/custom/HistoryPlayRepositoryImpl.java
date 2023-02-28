package melonproject.melon.repository.user.custom;

import java.util.List;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import melonproject.melon.entity.artist.song.QSongInfoEntity;
import melonproject.melon.entity.info.QGenreInfoEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.QHistoryPlayEntity;
import melonproject.melon.vo.Member.chartVO.UserGenreVO;

public class HistoryPlayRepositoryImpl implements HistoryPlayRepositoryCustom {

    private final JPAQueryFactory queryfactory;

    public HistoryPlayRepositoryImpl(EntityManager em) {
        this.queryfactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserGenreVO> countListenGenre(MemberInfoEntity member) {
        return queryfactory.select(Projections.fields(
            UserGenreVO.class,
            ExpressionUtils.as(JPAExpressions
                .select(QGenreInfoEntity.genreInfoEntity.giName)
                .from(QGenreInfoEntity.genreInfoEntity)
                .where(QGenreInfoEntity.genreInfoEntity.eq(QSongInfoEntity.songInfoEntity.genre)), "genre"),
                    QHistoryPlayEntity.historyPlayEntity.song.count().as("count")
            )
        )
        .from(QHistoryPlayEntity.historyPlayEntity)
        .join(QHistoryPlayEntity.historyPlayEntity.song, QSongInfoEntity.songInfoEntity)
        .where(
            QHistoryPlayEntity.historyPlayEntity.member.eq(member)
        )
        .groupBy(QSongInfoEntity.songInfoEntity.genre)
        .fetch();  
    }
    
}
