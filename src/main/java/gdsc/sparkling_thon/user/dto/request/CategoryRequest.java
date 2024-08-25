package gdsc.sparkling_thon.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryRequest {
    private Long id;
    private String name;

    @Builder
    public CategoryRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
