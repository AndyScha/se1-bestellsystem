package system;

import datamodel.Order;

/**
 * Interface to build orders.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public interface OrderBuilder {
    OrderBuilder buildOrders();
    OrderBuilder buildMoreOrders();
    
    /**
     * Accept an order only if sufficient inventory is in stock that can meet
     * all ordered items. An accepted order is saved to the OrderRepository.
     *
     * @param order order to accept.
     * @return true if order accepted.
     */
    public boolean accept(Order order);
}
