package gdsc.sparkling_thon.book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gdsc.sparkling_thon.book.domain.entity.BookEntity;
import gdsc.sparkling_thon.book.domain.entity.CategoryEntity;
import gdsc.sparkling_thon.book.domain.entity.QBookEntity;
import gdsc.sparkling_thon.book.domain.entity.QCategoryEntity;
import gdsc.sparkling_thon.book.domain.enums.BookStateEnum;
import gdsc.sparkling_thon.book.domain.enums.SearchCategoryEnum;
import gdsc.sparkling_thon.user.domain.QUserEntity;
import gdsc.sparkling_thon.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class DefaultBookRepository {
    private final JPAQueryFactory queryFactory;

    public List<BookEntity> getBooks(String userTel, Set<SearchCategoryEnum> options, double latitude, double longitude) {
        if (options == null) {
            options = new HashSet<>();
        }
        double rangeMeter = 200;
        QCategoryEntity categoryEntity = QCategoryEntity.categoryEntity;
        QBookEntity bookEntity = QBookEntity.bookEntity;
        QUserEntity userEntity = QUserEntity.userEntity;
        JPAQuery<BookEntity> query = queryFactory.selectFrom(bookEntity);

        UserEntity searcher = queryFactory.selectFrom(userEntity)
            .where(userEntity.telNum.eq(userTel))
            .fetchOne();

        if (options.contains(SearchCategoryEnum.NEW)) {
            query = query.where(bookEntity.status.eq(BookStateEnum.NEW));
        }
//
//        if (options.contains(SearchCategoryEnum.INTERESTED)) {
//            assert searcher != null;
//            query = query.where(bookEntity.categories.any().id.eq(searcher.getId()));
//        }
//
//        if (options.contains(SearchCategoryEnum.NEARBY)) {
//            query = query.join(bookEntity.user, userEntity)
//                .where(
//                    Expressions.booleanTemplate(
//                        "ST_DWithin(ST_SetSRID(ST_MakePoint({0}, {1}), 4326)::geography, ST_SetSRID(ST_MakePoint({2}, {3}), 4326)::geography, {4})",
//                        userEntity.latitude, userEntity.latitude, longitude, latitude, rangeMeter
//            ));
//        }



        return query.fetch();
    }
}
