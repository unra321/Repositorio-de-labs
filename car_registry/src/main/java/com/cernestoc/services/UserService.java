package com.cernestoc.services;

import com.cernestoc.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    public UserEntity save(UserEntity entity);

    byte[] getUserImage(long id);

    void addUserImage(long id, MultipartFile imageFile) throws IOException;
}
