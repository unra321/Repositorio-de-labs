package com.cernestoc.services.impl;

import com.cernestoc.entity.UserEntity;
import com.cernestoc.repository.UserRepository;
import com.cernestoc.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByMail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public UserEntity save(UserEntity entity) {
        return userRepository.save(entity);
    }

    @Override
    public byte[] getUserImage(long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(RuntimeException::new);

        return Base64.getDecoder().decode(userEntity.getImage());
    }

    @Override
    public void addUserImage(long id, MultipartFile imageFile) throws IOException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(RuntimeException::new);

        log.info("Saving image file");
        userEntity.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes()));
        userRepository.save(userEntity);
    }
}
