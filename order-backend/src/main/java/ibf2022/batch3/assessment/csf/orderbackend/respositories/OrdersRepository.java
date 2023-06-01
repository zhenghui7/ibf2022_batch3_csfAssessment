package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import jakarta.json.Json;

@Repository
public class OrdersRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	// TODO: Task 3
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	// Native MongoDB query here for add()
	/*
	 * db.orders.insertOne({
	 * "_id": "orderid131",
	 * "date": ISODate("2023-05-31T00:00:00Z"),
	 * "total": 11.11,
	 * "name": "fred",
	 * "email": "fred@gmail.com",
	 * "sauce": "testsauce",
	 * "size": "1",
	 * "comments": "Extra crispy",
	 * "toppings": ["chicken", "cheese"],
	 * "crust": "thick"
	 * })
	 * 
	 */
	public void add(PizzaOrder order) {

		mongoTemplate.insert(Document.parse(
				Json.createObjectBuilder()
						.add("_id", order.getOrderId())
						.add("date", order.getDate().getTime())
						.add("total", order.getTotal())
						.add("name", order.getName())
						.add("email", order.getEmail())
						.add("sauce", order.getSauce())
						.add("size", order.getSize())
						.add("comments", order.getComments())
						.add("toppings", order.getTopplings().toString())
						.add("crust", order.getThickCrust() ? "thick" : "thin")
						.build().toString()),
				"orders");

	}

	// TODO: Task 6
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	// Native MongoDB query here for getPendingOrdersByEmail()
	public List<PizzaOrder> getPendingOrdersByEmail(String email) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		.with(Sort.by(Direction.ASC, "title"));

		return template.find(query, PizzaOrder.class, "book")
		.stream()
		.map(doc -> new PizzaOrder(doc.getInteger("bookID"), doc.getString("title")))
		.toList();

	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	// Native MongoDB query here for markOrderDelivered()
	public boolean markOrderDelivered(String orderId) {
		Query query = new Query();
		
		return false;
	}

}
