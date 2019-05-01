package org.nixsolutions.camel.spring.webservice;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.nixsolutions.camel.spring.model.ServiceConstants;
import org.nixsolutions.camel.spring.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service")
public class Service {
	@Autowired
	private ProducerTemplate producer;
	@Autowired
	private CamelContext camelContext;

	@PostMapping(value = "/camel", consumes = { "application/json", "*/*" }, produces = "application/json")
	public Weather post(@RequestBody Weather weather) {
		final Exchange exchange = ExchangeBuilder.anExchange(camelContext).withBody(weather).build();
		final Exchange responseExchange = producer.send(ServiceConstants.MY_SERVICE_ENDPOINT, exchange);
		return responseExchange.getOut().getBody(Weather.class);
	}
	
	@PostMapping(value = "/camelMail", consumes = { "application/json", "*/*" }, produces = "application/json")
	public Weather postMail(@RequestBody Weather weather) {
		final Exchange exchange = ExchangeBuilder.anExchange(camelContext).withBody(weather).build();
		final Exchange responseExchange = producer.send(ServiceConstants.MAIL_SERVICE_ENDPOINT, exchange);
		return responseExchange.getIn().getBody(Weather.class);
	}
}
