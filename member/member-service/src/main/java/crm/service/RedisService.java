package crm.service;

import crm.redis.RedisMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, RedisMember> template;

    public void save(RedisMember member) {
        template.<String, RedisMember> opsForHash().put("member:externalId", member.getExternalId(), member);
        template.<String, RedisMember> opsForHash().put("member:email", member.getEmail(), member);
        template.<String, RedisMember> opsForHash().put("member:nickname", member.getNickname(), member);
    }

    public RedisMember findByNickname(String nickname) {
        return template.<String, RedisMember> opsForHash().get("member:nickanme", nickname);
    }

    public boolean existsByNickname(String nickname) {
        return template.<String, RedisMember> opsForHash().hasKey("member:nickanme", nickname);
    }

    public RedisMember getByEmail(String email) {
        return template.<String, RedisMember> opsForHash().get("member:email", email);
    }

    public boolean existsByEmail(String email) {
        return template.<String, RedisMember> opsForHash().hasKey("member:email", email);
    }
}
