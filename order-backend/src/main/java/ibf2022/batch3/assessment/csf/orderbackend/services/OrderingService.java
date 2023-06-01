package ibf2022.batch3.assessment.csf.orderbackend.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.OrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.PendingOrdersRepository;

@Service
public class OrderingService {

	@Autowired
	private OrdersRepository ordersRepo;

	@Autowired
	private PendingOrdersRepository pendingOrdersRepo;

	// TODO: Task 5
	// WARNING: DO NOT CHANGE THE METHOD'S SIGNATURE
	public PizzaOrder placeOrder(PizzaOrder order) throws OrderException {
		//instruction and typo led me to write the codes for resttemplate instead
		String orderUrl = UriComponentsBuilder
				.fromUriString("https://pizza-pricing-production.up.railway.app/order")
				.toUriString();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("name", order.getName()); // testname
		body.add("email", order.getEmail()); // testemail@gmail.com
		body.add("sauce", order.getSauce()); // classic
		body.add("size", order.getSize().toString()); // 1
		body.add("thickCrust", order.getThickCrust().toString()); // true
		body.add("toppings", order.getTopplings().toString()); // chicken, seafood, beef, vegetables
		body.add("comments", order.getComments()); // no comment

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> resp = restTemplate.exchange(orderUrl, HttpMethod.POST, requestEntity,
				String.class);

		String responseString = resp.getBody();

		// Split the response string by comma
		String[] responseParts = responseString.split(",");

		// Extract the individual values
		String orderId = responseParts[0].trim();
		Long orderDate = Long.parseLong(responseParts[1].trim());
		Float orderPrice = Float.parseFloat(responseParts[2].trim());

		Date orderDateObj = new Date(orderDate);

		order.setOrderId(orderId);
		order.setDate(orderDateObj);
		order.setTotal(orderPrice);

		ordersRepo.add(order);
		return order;
	}

	// For Task 6
	// WARNING: Do not change the method's signature or its implemenation
	public List<PizzaOrder> getPendingOrdersByEmail(String email) {
		return ordersRepo.getPendingOrdersByEmail(email);
	}

	// For Task 7
	// WARNING: Do not change the method's signature or its implemenation
	public boolean markOrderDelivered(String orderId) {
		return ordersRepo.markOrderDelivered(orderId) && pendingOrdersRepo.delete(orderId);
	}

}
