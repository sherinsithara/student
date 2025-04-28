package org.mentorbridge.repository;

import org.mentorbridge.entity.StudentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<StudentEntity,String> {
}
