package org.mentorbridge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StudentDTO {
    private String id;
     private String firstName;
    private String lastName;
   private String email;
}
