package org.nixsolutions.camel.spring.route;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.nixsolutions.camel.spring.model.ServiceConstants;
import org.nixsolutions.camel.spring.model.Weather;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"test"})
public class RouteTest extends CamelSpringTestSupport {
	private static final Weather TEST_MESSAGE_PAYLOAD = new Weather();

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan("org.nixsolutions.camel");
		ctx.refresh();
		return ctx;
	}
	
	@Test
	public void testPresent() throws Exception {
		assertTrue(context().getRouteStatus(ServiceConstants.MY_ROUTE_ID).isStarted());
	}
	
	@Test
	public void testProcess() throws Exception {
		final Endpoint endpoint = getMandatoryEndpoint(ServiceConstants.MY_SERVICE_ENDPOINT);
		final Exchange requestExchange = ExchangeBuilder.anExchange(context()).withBody(TEST_MESSAGE_PAYLOAD).build();
		final Exchange resultExchange = context().createProducerTemplate().send(endpoint, requestExchange);
		final Weather resultBody = resultExchange.getIn().getBody(Weather.class);
		assertTrue(resultBody.equals(TEST_MESSAGE_PAYLOAD));
	}

}
