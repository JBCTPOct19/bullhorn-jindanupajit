package com.jindanupajit.javabootcamp.bullhorn.repository;

import com.jindanupajit.javabootcamp.bullhorn.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageCrudRepository extends CrudRepository<Message, Long> {
}
