package com.cernestoc.controller;

import com.cernestoc.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("donloadPhoto/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<Object> donloadUserPhoto(@PathVariable("id") long id){
        try {
            byte[] imageBytes = userService.getUserImage(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageBytes,headers, HttpStatus.OK);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/userImage/{id}/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<String> addImage(@PathVariable("id") long id, @RequestParam("imageFile")MultipartFile imageFile){
        try {
            userService.addUserImage(id,imageFile);
            return ResponseEntity.ok("Image has been successfully loaded");
        }
        catch (IOException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
