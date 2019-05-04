package com.nixsolutions.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.nixsolutions.model.Todo;
import com.nixsolutions.service.TodoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/todos", consumes = {"application/json", "*/*"}, produces = "application/json")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @ApiOperation(value = "create todo", response = Todo.class)
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Todo create(@RequestBody Todo entity) {
        return todoService.save(entity);
    }

    @CrossOrigin
    @ApiOperation(value = "retrieve list of todos", response = List.class)
    @GetMapping
    public List<Todo> getAll() {
        List<Todo> list = new ArrayList<>();
        Iterable<Todo> iterable = todoService.findAll();
        for (Todo todo : iterable) {
            list.add(todo);
        }
        return list;
    }

    @ApiOperation(value = "retrieve one todo", response = Iterable.class)
    @GetMapping(path = "/{name}")
    public Todo get(@PathVariable("name") String name) {
        return todoService.findByName(name);
    }

    @ApiOperation(value = "update todo", response = Todo.class)
    @PutMapping(path = "/{name}")
    public Todo update(@PathVariable("name") String name, @RequestBody Todo entity) {
        Todo todo = todoService.findByName(name);
        entity.setId(todo.getId());
        return todoService.update(entity);
    }

    @ApiOperation(value = "delete todo", response = Todo.class)
    @DeleteMapping(path = "/{name}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("name") String name) {
        todoService.delete(todoService.findByName(name));
    }
}
