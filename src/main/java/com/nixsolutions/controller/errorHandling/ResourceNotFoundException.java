package com.nixsolutions.controller.errorHandling;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
