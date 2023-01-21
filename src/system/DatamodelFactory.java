package system;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;

import java.util.List;
import java.util.Optional;

public interface DatamodelFactory {
    Customer createCustomer();

    Customer createCustomer(String name);

    Article createArticle();

    Article createArticle(String description, long unitPrice);

    Order createOrder(Customer customer);

    List<Customer> getCustomers();

    List<Article> getArticles();

    List<Order> getOrders();

    int customersCount();

    int articlesCount();

    Optional<Customer> findCustomerById(long id);

    Optional<Article> findArticleById(String id);

    Optional<Order> findOrderById(String id);

    int ordersCount();
}
