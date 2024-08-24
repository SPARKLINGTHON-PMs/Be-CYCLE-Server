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
import gdsc.sparkling_thon.user.domain.UserCategoryEntity;
import gdsc.sparkling_thon.user.domain.UserEntity;
import gdsc.sparkling_thon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class DefaultBookRepository {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<BookEntity> getBooks(String userTel, Set<SearchCategoryEnum> options, double latitude, double longitude) {
        double rangeMeter = 200;
        QCategoryEntity categoryEntity = QCategoryEntity.categoryEntity;
        QBookEntity bookEntity = QBookEntity.bookEntity;

        Optional<UserEntity> userEntity = userRepository.findByTelNum(userTel);

        List<BookEntity> books = bookRepository.findAll();
        Stream<BookEntity> bookStream = books.stream();
        if (options.contains(SearchCategoryEnum.NEARBY)) {
            bookStream = bookStream.filter(book -> {
                double distance = calculateDistance(latitude, longitude, book.getUser().getLongitude(), book.getUser().getLongitude());
                return distance <= rangeMeter;
            });
        }
        else if (options.contains(SearchCategoryEnum.NEW)) {
            bookStream = bookStream.filter(book -> book.getStatus() == BookStateEnum.NEW);
        }
        else if (options.contains(SearchCategoryEnum.INTERESTED)&& userEntity.isPresent()) {
            Set<UserCategoryEntity> categories = userEntity.get().getUserCategories();
            bookStream = bookStream.filter(book -> book.getCategories().stream().anyMatch(categories::contains));
        }

        return bookStream.toList();
    }

    // 거리 계산 메서드 (단위: 미터)
    private double calculateDistance(double userLat, double userLon, double toiletLat, double toiletLon) {
        double earthRadius = 6371000; // 미터 단위
        double dLat = Math.toRadians(toiletLat - userLat);
        double dLon = Math.toRadians(toiletLon - userLon);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(toiletLat)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
}
