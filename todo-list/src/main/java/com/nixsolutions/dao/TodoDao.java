package com.nixsolutions.dao;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nixsolutions.model.Todo;

@Repository
public interface TodoDao extends CrudRepository<Todo, UUID>{
	public Todo findByName(String name);
}
