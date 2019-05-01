package org.nixsolutions.camel.spring.route;

import org.apache.camel.builder.RouteBuilder;
import org.nixsolutions.camel.spring.model.ServiceConstants;
import org.nixsolutions.camel.spring.processor.CustomProcessor;
import org.springframework.stereotype.Component;

@Component
public class Route extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from(ServiceConstants.MY_SERVICE_ENDPOINT)
		.id(ServiceConstants.MY_ROUTE_ID)
		.log("routing..." + "${in.body}")
		.process(new CustomProcessor());
		
		from(ServiceConstants.MAIL_SERVICE_ENDPOINT)
			.id(ServiceConstants.MAIL_ROUTE_ID)
			.log("routing... " + "${in.body.cityName}")
			.process(new CustomProcessor())
			.setHeader("subject", simple("Weather"))
			.to("smtps://smtp.gmail.com:465?username=testsmtpprotocol@gmail.com&password=qwe@@123");
	}
}
