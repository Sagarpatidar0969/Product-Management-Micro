package com.sagar.user_service.controller;

import com.sagar.user_service.entity.User;
import com.sagar.user_service.repository.UserRepository;
import com.sagar.user_service.service.OrderIntegrationService;
import com.sagar.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository repository;
    private final OrderIntegrationService orderIntegrationService;

    @PostMapping
    public User save(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
//    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<User> saveUser(
//            @RequestPart("user") String user,
//            @RequestPart("image") MultipartFile image) throws IOException {
//
//        ObjectMapper mapper = new ObjectMapper();
//        UserDto userDto = mapper.readValue(user, UserDto.class);
//
//        return ResponseEntity.ok(service.save(userDto, image));
//    }

    @PostMapping("/send-sale-email")
    public ResponseEntity<String> sendSaleEmail() {

        userService.sendSaleEmail();

        return ResponseEntity.ok(
                "Big Billion Sale email sent successfully"
        );
    }

}
