package com.iamskrai.task_sphere.user.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
        private Long id;
        private String name;
        private String email;
}
