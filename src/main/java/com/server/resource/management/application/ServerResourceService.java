package com.server.resource.management.application;

import com.server.resource.management.domain.ServerResource;
import com.server.resource.management.domain.User;
import com.server.resource.management.domain.UserResource;
import com.server.resource.management.domain.repository.ServerResourceRepository;
import com.server.resource.management.domain.repository.UserRepository;
import com.server.resource.management.domain.repository.UserResourceRepository;
import com.server.resource.management.ui.dto.DeleteResourceRequestDto;
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
        saveUserResource(UserResource.from(requestDto.getCpu(), requestDto.getMemory(), serverResource, user));

    }

    @Transactional
    public void modifyResource(ServerResourceRequestDto requestDto) {
        ServerResource serverResource = findServerById(requestDto.getServerId());
        User user = findUserByName(requestDto.getUserName());
        UserResource userResource = findUserResourceByUserAndServerResource(user, serverResource);
        userResource.modifyUsingResource(requestDto.getCpu(), requestDto.getMemory());
    }

    @Transactional
    public void deleteResource(DeleteResourceRequestDto requestDto) {
        ServerResource serverResource = findServerById(requestDto.getServerId());
        User user = findUserByName(requestDto.getUserName());
        serverResource.removeUsedResource(findUserResourceByUserAndServerResource(user, serverResource));
    }

    private void saveUserResource(UserResource userResource) {
        userResourceRepository.findByUserAndServerResource(userResource.getUser(), userResource.getServerResource())
                .orElseGet(() -> createUserResource(userResource));
    }

    private UserResource createUserResource(UserResource userResource) {
        return userResourceRepository.save(userResource);
    }

    private UserResource findUserResourceByUserAndServerResource(User user, ServerResource serverResource) {
        return userResourceRepository.findByUserAndServerResource(user, serverResource)
                .orElseThrow(() -> new IllegalArgumentException("수정할 사용자 혹은 서버정보가 존재하지 않습니다.."));
    }

    private ServerResource findServerById(long serverId) {
        return serverResourceRepository.findById(serverId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 서버정보를 요청했습니다."));
    }

    private User findUserByName(String userName) {
        return userRepository.findByName(userName)
                .orElseGet(() -> createUser(userName));
    }

    private User createUser(String userName) {
        return userRepository.save(User.of(userName));
    }
}
