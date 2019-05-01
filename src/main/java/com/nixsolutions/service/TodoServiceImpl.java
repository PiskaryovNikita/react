package com.nixsolutions.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nixsolutions.controller.errorHandling.ConflictException;
import com.nixsolutions.controller.errorHandling.ResourceNotFoundException;
import com.nixsolutions.dao.TodoDao;
import com.nixsolutions.model.Todo;

@Service
public class TodoServiceImpl implements TodoService {
	@Autowired
	private TodoDao todoDao;

	@Override
	public <S extends Todo> S save(S entity) {
		if (todoDao.findByName(entity.getName()) != null) {
			throw new ConflictException("todo with name = " + entity.getName() + " already exist");
		}
		return todoDao.save(entity);
	}
	
	@Override
	public Todo update(Todo entity) {
		if (todoDao.findById(entity.getId()) == null) {
			throw new ResourceNotFoundException("no topic wiht id = " + entity.getId());
		}
		return todoDao.save(entity);
	}

	@Override
	public Todo findByName(String name) {
		Todo todo = todoDao.findByName(name);
		if (todo == null) {
			throw new ResourceNotFoundException("no todo with name " + name);
		}
		return todo;
	}

	@Override
	public Iterable<Todo> findAll() {
		Iterable<Todo> iterable = todoDao.findAll();
		if (!iterable.iterator().hasNext()) {
			throw new ResourceNotFoundException("no todos");
		}
		return iterable;
	}

	@Override
	public void deleteById(UUID id) {
		if (todoDao.findById(id) == null) {
			throw new ResourceNotFoundException("no todo with id " + id);
		}
		todoDao.deleteById(id);
	}

	@Override
	public void delete(Todo entity) {
		if (todoDao.findByName(entity.getName()) == null) {
			throw new ResourceNotFoundException("no todo with name " + entity.getName());
		}
		todoDao.delete(entity);
	}
}
