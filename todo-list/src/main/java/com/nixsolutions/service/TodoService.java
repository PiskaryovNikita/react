package com.nixsolutions.service;

import java.util.UUID;

import com.nixsolutions.model.Todo;

public interface TodoService {

	<S extends Todo> S save(S entity);

	Todo update(Todo entity);

	Todo findByName(String name);

	Iterable<Todo> findAll();

	void deleteById(UUID id);

	void delete(Todo entity);
}