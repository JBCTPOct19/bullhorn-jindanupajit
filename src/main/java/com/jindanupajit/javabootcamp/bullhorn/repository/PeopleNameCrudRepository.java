package com.jindanupajit.javabootcamp.bullhorn.repository;

import com.jindanupajit.javabootcamp.bullhorn.entity.PeopleName;
import org.springframework.data.repository.CrudRepository;

public interface PeopleNameCrudRepository extends CrudRepository<PeopleName, Long> {
}
