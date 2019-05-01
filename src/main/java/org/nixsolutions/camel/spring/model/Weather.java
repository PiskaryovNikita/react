package org.nixsolutions.camel.spring.model;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement
public class Weather {
	private String cityName;
	private int zipCode;
	private int temperature;
	private String date;
}
