package com.sagar.user_service.service;


import com.sagar.user_service.dto.UserDto;
import com.sagar.user_service.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User save(User user);

    User getById(Long id);

    List<User> getAll();

    void delete(Long id);

    void sendSaleEmail();

   // User save(UserDto dto, MultipartFile image) throws IOException;

}
