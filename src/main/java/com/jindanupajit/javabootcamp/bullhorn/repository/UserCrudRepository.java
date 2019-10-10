package com.jindanupajit.javabootcamp.bullhorn.repository;

import com.jindanupajit.javabootcamp.bullhorn.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserCrudRepository extends CrudRepository <User, Long> {
}
