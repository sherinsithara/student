package org.mentorbridge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "student")
@Builder
@AllArgsConstructor
@Data
public class StudentEntity {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;

}
