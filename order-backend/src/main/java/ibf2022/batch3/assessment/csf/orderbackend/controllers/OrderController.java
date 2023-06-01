package ibf2022.batch3.assessment.csf.orderbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.services.OrderException;
import ibf2022.batch3.assessment.csf.orderbackend.services.OrderingService;
import jakarta.json.Json;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class OrderController {

	@Autowired
	OrderingService orderingService;

	// TODO: Task 3 - POST /api/order
	@PostMapping(path = "/api/order", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> placeOrder(@RequestBody PizzaOrder pizzaOrder) {
		System.out.println(pizzaOrder); // RequestBody did not parse out everything

		try {
			PizzaOrder returnOrder = orderingService.placeOrder(pizzaOrder);	//may not need to return
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(Json.createObjectBuilder()
							.add("orderId", "test") // depends on returning what
							.add("date", "testdate")
							.add("name", "testname")
							.add("email", "test email")
							.add("total", "returnOrder.getTotal()")
							.build().toString());

		} catch (OrderException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Json.createObjectBuilder()
							.add("error", e.getMessage())
							.build().toString());
		}
	}

	// TODO: Task 6 - GET /api/orders/<email>
	@GetMapping(path = "/orders/{email}")
	@ResponseBody
	public ResponseEntity<String> retrieveByemail(@PathVariable String email) {
		orderingService.getPendingOrdersByEmail(email);
		return null;
	}


	// TODO: Task 7 - DELETE /api/order/<orderId>
	@DeleteMapping(path = "/api/order/{email}")
	public ResponseEntity<String> markOrderDelivered(@RequestParam String orderId) {
		orderingService.markOrderDelivered(orderId);
		return null;
	}
}
