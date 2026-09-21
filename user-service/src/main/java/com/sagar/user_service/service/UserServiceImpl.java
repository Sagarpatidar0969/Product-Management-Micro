package com.sagar.user_service.service;


import com.sagar.user_service.entity.User;
import com.sagar.user_service.event.UserCreatedEvent;
import com.sagar.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String USER_KEY = "USER:";
    private final KafkaProducerService kafkaProducerService;


    // ================= SAVE USER =================

    @Override
    public User save(User user) {

        // Save user in MySQL
        User savedUser = repository.save(user);

        // Save user in Redis
        redisTemplate.opsForValue()
                .set(USER_KEY + savedUser.getId(), savedUser);
        UserCreatedEvent event = new UserCreatedEvent(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

        kafkaProducerService.sendUserCreatedEvent(event);

//        redisTemplate.opsForValue()
//                .set(USER_KEY + savedUser.getId(), savedUser,10, TimeUnit.MINUTES);

        return savedUser;
    }


    // ================= GET USER BY ID =================

    @Override
    public User getById(Long id) {
        //Ex : User + 1 = User : 1;
        String key = USER_KEY + id;

        // 1. Check Redis
        User cachedUser =
                (User) redisTemplate.opsForValue().get(key);

        if (cachedUser != null) {

            System.out.println("User fetched from Redis");

            return cachedUser;
        }

        // 2. If Redis does not contain data
        System.out.println("User fetched from MySQL");

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // 3. Store user in Redis
        redisTemplate.opsForValue()
                .set(key, user);

        return user;
    }


    // ================= GET ALL USERS =================

    @Override
    public List<User> getAll() {

        return repository.findAll();
    }


    // ================= DELETE USER =================

    @Override
    public void delete(Long id) {

        // Delete from MySQL
        repository.deleteById(id);

        // Delete from Redis
        redisTemplate.delete(USER_KEY + id);

        System.out.println("User deleted from MySQL and Redis");
    }




    // ================= SAVE USER + IMAGE =================

//    @Override
//    public User save(UserDto dto, MultipartFile image)
//            throws IOException {
//
//        User user = new User();
//
//        user.setName(dto.getName());
//        user.setEmail(dto.getEmail());
//        user.setMobile(dto.getMobile());
//
//        // Save in MySQL
//        User savedUser = repository.save(user);
//
//        // Save in Redis
//        redisTemplate.opsForValue()
//                .set(USER_KEY + savedUser.getId(), savedUser);
//
//        return savedUser;
//    }
}