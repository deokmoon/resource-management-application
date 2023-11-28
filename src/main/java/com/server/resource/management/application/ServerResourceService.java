package com.server.resource.management.application;

import com.server.resource.management.domain.ServerResource;
import com.server.resource.management.domain.User;
import com.server.resource.management.domain.UserResource;
import com.server.resource.management.domain.repository.ServerResourceRepository;
import com.server.resource.management.domain.repository.UserRepository;
import com.server.resource.management.domain.repository.UserResourceRepository;
import com.server.resource.management.ui.dto.ServerResourceRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ServerResourceService {

    private final UserRepository userRepository;
    private final ServerResourceRepository serverResourceRepository;
    private final UserResourceRepository userResourceRepository;

    @Transactional
    public void requestNewResource(ServerResourceRequestDto requestDto) {
        ServerResource serverResource = findServerById(requestDto.getServerId());
        User user = findUserByName(requestDto.getUserName());
        userResourceRepository.save(UserResource.from(requestDto.getCpu(), requestDto.getMemory(), serverResource, user));
    }

    private ServerResource findServerById(long serverId) {
        return serverResourceRepository.findById(serverId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 서버정보를 요청했습니다."));
    }

    private User findUserByName(String userName) {
        return userRepository.findByName(userName)
                .orElse(createUser(userName));
    }

    private User createUser(String userName) {
        return userRepository.save(User.of(userName));
    }
}
