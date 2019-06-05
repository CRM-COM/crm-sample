package crm.redis;

import lombok.*;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisMember {

    @Indexed
    private String externalId;

    @Indexed
    private String email;

    @Indexed
    private String nickname;
}
