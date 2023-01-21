package datamodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Class of entity type <i>Order</i>.
 * <p>
 * Order represents a contractual relationship with a Customer for purchased (ordered) items.
 * </p>
 *
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 */
public class Order {


    /**
     * Unique id, null or "" are invalid values, id can be set only once.
     */
    private String id;

    /**
     * Reference to owning Customer, final, never null.
     */
    private final Customer customer;

    /**
     * Date/time the order was created.
     */
    private final Date creationDate;

    /**
     * Items that are ordered as part of this order.
     */
    private final List<OrderItem> items;

    /**
     * Constructor with customer owning the order.
     *
     * @param customer customer as owner of order, customer who placed that order.
     * @throws IllegalArgumentException when customer argument is null or has invalid id.
     */
    public Order(Customer customer) {

        // TODO implement here
        this.customer = customer;
        this.creationDate = new Date();
        this.items = new ArrayList<>();
    }

    /**
     * Id getter.
     *
     * @return order id, returns {@code null}, if id is unassigned.
     */
    public String getId() {
        // TODO implement here
        return id;
    }

    /**
     * Id setter. Id can only be set once with valid id, id is immutable after assignment.
     *
     * @param id only valid id (not null or "") updates id attribute on first invocation.
     * @return chainable self-reference.
     * @throws IllegalArgumentException if id argument is invalid ({@code id==null} or {@code id==""}).
     */
    public Order setId(String id) {
        // TODO implement here
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException("invalid id (negative).");
        }
        this.id = this.id == null ? id : this.id;
        return this;
    }

    /**
     * Customer getter.
     *
     * @return owning customer, cannot be null.
     */
    public Customer getCustomer() {
        // TODO implement here
        return customer;
    }

    /**
     * CreationDate getter, returns the time/date when the order was created.
     *
     * @return time/date when order was created as long in ms since 01/01/1970.
     */
    public long getCreationDate() {
        // TODO implement here
        return creationDate.getTime();
    }

    /**
     * CreationDate setter for date/time, which is valid for {@code 01/01/2020 <= datetime <= now() + 1day}.
     * Orders cannot be older than the lower bound and younger than the current datetime (+1day).
     *
     * @param datetime time/date when order was created (in milliseconds since 01/01/1970).
     * @return chainable self-reference.
     * @throws IllegalArgumentException if datetime is outside valid range {@code 01/01/2020 <= datetime <= now() + 1day}.
     */
    public Order setCreationDate(long datetime) {
        // TODO implement here
//        Date creationDate = new Date(datetime);
        Date lowerBoundDate = new Date(2020, 01, 01);
//        Date date = new Date();
//        date.setDate(date.getDate() + 1);
        Date currentTimePlusOneDay = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
        if (creationDate.before(lowerBoundDate) && creationDate.after(currentTimePlusOneDay)) {
            throw new IllegalArgumentException("date outside valid range");
        }
        this.creationDate.setTime(datetime);

        return this;

    }

    /**
     * Number of items that are part of the order.
     *
     * @return number of ordered items.
     */
    public int itemsCount() {
        // TODO implement here
        return this.items.size();
    }

    /**
     * Ordered items getter.
     *
     * @return ordered items.
     */
    public Iterable<OrderItem> getItems() {
        // TODO implement here
        return items;
    }

    /**
     * Create new item and add to order.
     *
     * @param article article ordered from catalog.
     * @param units   units ordered.
     * @return chainable self-reference.
     * @throws IllegalArgumentException if article is null or units not a positive {@code units > 0} number.
     */
    public Order addItem(Article article, int units) {
        // TODO implement here
        OrderItem item = new OrderItem(article, units);
        items.add(item);
        return this;
    }

    /**
     * Delete i-th item from order, {@code i >= 0 && i < items.size()}, otherwise method has no effect.
     *
     * @param i index of item to delete, only a valid index deletes item.
     */
    public void deleteItem(int i) {
        // TODO implement here
        if (i >= 0 && i < itemsCount()) {
            items.remove(i);
        }
    }

    /**
     * Delete all ordered items.
     */
    public void deleteAllItems() {
        // TODO implement here
        items.clear();
    }

    public long getTotalOrderValue() {
        long total = 0;
        for (OrderItem item : items) {
            total += item.getUnitsOrdered() * item.getArticle().getUnitPrice();
        }
        return total;
    }
}