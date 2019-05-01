package org.nixsolutions.camel.spring.processor;

import java.util.Date;
import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.httpclient.HttpStatus;
import org.nixsolutions.camel.spring.model.Weather;

public class CustomProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		final Weather body = exchange.getIn().getBody(Weather.class);
		modifyBody(body);
		exchange.getOut().setBody(body);
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.SC_ACCEPTED);
	}

	private void modifyBody(final Weather body) {
		String s = new StringBuilder(body.getCityName()).reverse().toString();
		body.setCityName(s);
		body.setTemperature(body.getZipCode() * 13);
		body.setZipCode(body.getTemperature() * new Random().nextInt(1000));
		body.setDate(new Date(System.currentTimeMillis()).toString());
	}
}
