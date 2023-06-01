package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;

@Repository
public class PendingOrdersRepository {
	@Autowired
	RedisTemplate<String, String> template;

	// TODO: Task 3
	// WARNING: Do not change the method's signature.
	public void add(PizzaOrder order) {

		Map<String, String> orderData = new HashMap<>();
		orderData.put("orderId", order.getOrderId());
		orderData.put("date", order.getDate().toString());
		orderData.put("total", order.getTotal().toString());
		orderData.put("name", order.getName());
		orderData.put("email", order.getEmail());
		
		template.opsForHash().putAll("orderId", orderData);
	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	public boolean delete(String orderId) {
		return false;
	}

}
