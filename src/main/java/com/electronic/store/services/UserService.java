package com.electronic.store.services;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;

import java.io.IOException;
import java.util.List;

public interface UserService {

    //create user
    public UserDto createUser(UserDto userDto);

    //update user
    public UserDto updateUser(UserDto userDto, String userId);

    //delete single user
    public void deleteUser(String userId) throws IOException;

    //delete All User
    public void deleteAllUser();

    //getAll user
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get user by id
    public UserDto getUserById(String userId);

    //get user by email
    public UserDto getUserByEmail(String email);

    //search user
    public List<UserDto> searchUser(String keyword);

    //other user related feature
}
