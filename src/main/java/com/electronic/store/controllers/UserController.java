package com.electronic.store.controllers;

import com.electronic.store.dtos.ApiResponseMessage;
import com.electronic.store.dtos.ImageResponseMessage;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.services.FileService;
import com.electronic.store.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") String userId) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) throws IOException {
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("user deleted successfully!!").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/allUser")
    public ResponseEntity<ApiResponseMessage> deleteAllUser() {
        userService.deleteAllUser();
        ApiResponseMessage message = ApiResponseMessage.builder().message("All user deleted successfully!!").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUserList(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<UserDto> allUserDtoList = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allUserDtoList, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId) {
        UserDto filteredUser = userService.getUserById(userId);
        return new ResponseEntity<>(filteredUser, HttpStatus.OK);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<UserDto> getUserByEmailId(@PathVariable("email") String email) {
        UserDto filteredUserByEmail = userService.getUserByEmail(email);
        return new ResponseEntity<>(filteredUserByEmail, HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<PageableResponse<UserDto>> searchUser(
            @PathVariable("keywords") String keywords,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<UserDto> searchUserDto = userService.searchUser(pageNumber,pageSize,sortBy,sortDir,keywords);
        return new ResponseEntity<>(searchUserDto, HttpStatus.OK);
    }

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponseMessage> uploadUserImage(@RequestParam("userImage") MultipartFile userImage, @PathVariable("userId") String userId) throws IOException {
        String imageName = fileService.uploadFile(userImage, imageUploadPath);
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto updatedUser = userService.updateUser(user, userId);
        ImageResponseMessage imageResponseMessage = ImageResponseMessage.builder().imageName(imageName).imagePath(imageUploadPath).message("Image Uploaded").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(imageResponseMessage, HttpStatus.OK);
    }

    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable("userId") String userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserById(userId);
        logger.info("File name {}", user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
