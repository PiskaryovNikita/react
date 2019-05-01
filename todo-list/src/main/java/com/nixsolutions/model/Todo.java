package com.nixsolutions.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.utils.UUIDs;

import lombok.Data;

@Table(value = "todo")
public @Data class Todo {
	@PrimaryKey
	private UUID id;
	@Indexed
	private String name;
	
	public Todo() {
		this.id = UUIDs.timeBased();
	}
}
