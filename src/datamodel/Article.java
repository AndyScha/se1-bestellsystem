package datamodel;

import java.util.*;

/**
 * Class of entity type <i>Article</i>.
 * <p>
 * Articles can be referenced as ordered items in orders.
 * </p>
 *
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 */
public class Article {


    /**
     * Unique id, null or "" are invalid values, id can be set only once.
     */
    private String id = null;

    /**
     * Article description, never null, may be empty "".
     */
    private String description = "";

    /**
     * Price in cent per article, negative values are invalid.
     */
    private long unitPrice = 0;

    /**
     * Currency in which price is quoted, EUR is the default currency.
     */
    private Currency currency = Currency.EUR;

    /**
     * Tax rate applicable to article, German regular Value-Added Tax (VAT) is the default.
     */
    private TAX tax = TAX.GER_VAT;

    /**
     * Default constructor
     */
    public Article() {
    }


    /**
     * Constructor with description and price.
     *
     * @param description descriptive text for article.
     * @param unitPrice   price (in cent) for one unit of the article.
     * @throws IllegalArgumentException when description is null or empty "" or price negative {@code < 0}.
     */
    public Article(String description, long unitPrice) {
        // TODO implement here
        setDescription(description);
        setUnitPrice(unitPrice);
    }

    /**
     * Id getter.
     *
     * @return article order id, returns {@code null}, if id is unassigned.
     */
    public String getId() {
        // TODO implement here
        return id;
    }

    /**
     * Id setter. Id can only be set once with valid id, id is immutable after assignment.
     *
     * @param id only valid id (not null or "") updates id attribute on first invocation.
     * @return chainable self-reference
     * @throws IllegalArgumentException if id argument is invalid ({@code id==null} or {@code id==""}).
     */
    public Article setId(String id) {
        // TODO implement here
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException("invalid id.");
        }
        this.id = this.id == null ? id : this.id;
        return this;
    }

    /**
     * Description getter.
     *
     * @return descriptive text for article
     */
    public String getDescription() {
        // TODO implement here
        return description;
    }

    /**
     * Description setter.
     *
     * @param description descriptive text for article, only valid description (not null or "") updates attribute.
     * @return chainable self-reference.
     * @throws IllegalArgumentException when description is null or empty "".
     */
    public Article setDescription(String description) {
        // TODO implement here
        if (description == null || description.equals("")) {
            throw new IllegalArgumentException("description is invalid.");
        }
        this.description = description;
        return this;
    }

    /**
     * UnitPrice getter.
     *
     * @return price in cent for one article unit.
     */
    public long getUnitPrice() {
        return unitPrice;
    }

    /**
     * UnitPrice setter.
     *
     * @param unitPrice price in cent for one article, only valid price ( {@code >= 0} ) updates attribute.
     * @return chainable self-reference.
     */
    public Article setUnitPrice(long unitPrice) {
        // TODO implement here
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("unit price is negative.");
        }
        this.unitPrice = unitPrice;
        return this;
    }

    /**
     * Currency getter.
     *
     * @return currency in which unitPrice is quoted.
     */
    public Currency getCurrency() {
        // TODO implement here
        return currency;
    }

    /**
     * Currency setter.
     *
     * @param currency in which unitPrice is quoted.
     * @return chainable self-reference.
     * @throws IllegalArgumentException if currency is null.
     */
    public Article setCurrency(Currency currency) {
        // TODO implement here
        if (currency == null) {
            throw new IllegalArgumentException("invalid currency.");
        }
        this.currency = currency;
        return this;
    }

    /**
     * TAX getter.
     *
     * @return tax rate applicable for article.
     */
    public TAX getTax() {
        // TODO implement here
        return tax;
    }

    /**
     * TAX setter.
     *
     * @param tax rate that applies to article.
     * @return chainable self-reference.
     * @throws IllegalArgumentException if tax is null.
     */
    public Article setTax(TAX tax) {
        // TODO implement here
        if (tax == null) {
            throw new IllegalArgumentException("invalid tax.");
        }
        this.tax = tax;
        return this;
    }

}