package com.sagar.user_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent {

    private Long userId;
    private String name;
    private String email;




}