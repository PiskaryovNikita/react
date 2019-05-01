package com.nixsolutions.controller.errorHandling;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConflictException extends RuntimeException {
	private static final long serialVersionUID = -3763927795584270858L;

	public ConflictException(String message) {
		super(message);
	}
}
