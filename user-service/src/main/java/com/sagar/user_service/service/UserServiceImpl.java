package com.sagar.user_service.service;

import com.sagar.user_service.dto.UserDto;
import com.sagar.user_service.entity.User;
import com.sagar.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public User save(User user){
        return repository.save(user);
    }

    @Override
    public User getById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAll(){
        return repository.findAll();
    }

    @Override
    public void delete(Long id){
        repository.deleteById(id);
    }
    @Override
    public User save(UserDto dto, MultipartFile image) throws IOException {


        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setMobile(dto.getMobile());

        return repository.save(user);
    }
}