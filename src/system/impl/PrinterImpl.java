package system.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import application.Application_E1;
import datamodel.*;
import system.Calculator;
import system.Formatter;
import system.Printer;
import system.TablePrinter;
import system.TablePrinter.Builder;


class PrinterImpl implements Printer {
    //
    private final Calculator calculator;
    private final Formatter formatter;

    PrinterImpl(Calculator calculator, Formatter formatter) {
        this.calculator = calculator;
        this.formatter = formatter;
    }

    @Override
    public TablePrinter createTablePrinter(StringBuffer sb, Consumer<Builder> builder) {
        // TODO Auto-generated method stub
        return new TablePrinterImpl(sb, builder);
    }

    @Override
    public StringBuffer printCustomer(StringBuffer sb, Customer c) {
        // TODO Auto-generated method stub
        if (c == null)
            return sb;
        //
        final StringBuffer contacts = new StringBuffer();
        IntStream.range(0, c.contactsCount()).forEach(i ->
                contacts.append(i == 0 ? "" : ", ").append(c.getContacts()[i])
        );
        //
        int nameStyle = 0;
        return (sb == null ? new StringBuffer() : sb)
                .append(String.format("| %6d ", c.getId()))
                .append(String.format("| %-31s", formatter.fmtName(c.getFirstName(), c.getLastName(), nameStyle)))
                .append(String.format("| %-44s ", contacts))
                .append("|\n");
    }

    @Override
    public StringBuffer printCustomers(StringBuffer sb, Collection<Customer> customers) {
        // TODO Auto-generated method stub
        if (customers == null)
            return sb;
        //
        final StringBuffer sb_ = sb == null ? new StringBuffer() : sb;
//		var cit = customers.iterator();
//		while(cit.hasNext()) {		// iterate through Customer collection
//			Customer c = cit.next();
//			printCustomer(sb, c);		// format each Customer object as line into StringBuffer
//		}
//		return sb;
        return process(sb_, customers, s -> s, c -> printCustomer(sb_, c));    // calling generic print method
    }

    @Override
    public StringBuffer printArticle(StringBuffer sb, Article a) {
        // TODO Auto-generated method stub
        if (a == null)
            return sb;
        //
        return (sb == null ? new StringBuffer() : sb)
                .append(String.format("| %10s ", a.getId()))
                .append(String.format("| %-27s", a.getDescription()))
                .append(String.format("| %6d ", a.getUnitPrice())).append("\u20ac")    // Unicode for Euro
                .append(String.format("| %4s MwSt", a.getTax() == TAX.GER_VAT_REDUCED ? "7%" : "19%"))
                .append("|\n");
    }

    @Override
    public StringBuffer printArticles(StringBuffer sb, Collection<Article> articles) {
        // TODO Auto-generated method stub
        if (articles == null)
            return sb;
        //
        final StringBuffer sb_ = sb == null ? new StringBuffer() : sb;
        return process(sb_, articles, a -> printArticle(sb_, a));
    }

    @Override
    public StringBuffer printOrder(StringBuffer sb, Order order) {
        // TODO Auto-generated method stub
        if (order == null)
            return sb;
        //
        final String creationDate = formatter.fmtDate(order.getCreationDate(), 0, "");
        final Customer c = order.getCustomer();
        return (sb == null ? new StringBuffer() : sb)
                .append(String.format("| %10s ", order.getId()))
                .append(String.format("| %-27s", formatter.fmtName(c.getFirstName(), c.getLastName(), 0)))
                .append(String.format("| %1d items ", order.itemsCount()))
                .append(String.format("| created: %s ", creationDate))
                .append("|\n");
    }

    @Override
    public StringBuffer printOrders(StringBuffer sb, Collection<Order> orders) {
        // TODO Auto-generated method stub
        if (orders == null)
            return sb;
        //
        final StringBuffer sb_ = sb == null ? new StringBuffer() : sb;
        return process(sb_, orders, a -> printOrder(sb_, a));
    }

    @Override
    public TablePrinter printOrder(TablePrinter orderTable, Order order) {
        // TODO Auto-generated method stub
        //
        if (orderTable != null && order != null) {
            /*
             * TODO: E1(3) implement/change logic to extract items from order, calculate values
             * and fill them into table rows. Code should produce correct table for any order.
             *
             * Current code produces (should produce what is shown in javadoc):
             * +----------+---------------------------------------------+--------------------+
             * |Bestell-ID|Bestellungen                  MwSt      Preis|     MwSt     Gesamt|
             * +----------+---------------------------------------------+--------------------+
             * |8592356245|Eric's Bestellung:                           |                    |
             * |          | - 4 Teller, 4x 6.49          4.14*    25.96 |                    |
             * |          | - 4 Tasse, 4x 2.99           1.91*    11.96 |   13.18     129.79 |
             * +----------+---------------------------------------------+--------------------+
             */
            String id = order.getId();    // retrieve order attributes
            String name = order.getCustomer().getFirstName();
            orderTable.row(id, name + "'s Bestellung: ");
            String reducedTaxMarker = "*";
            var valueAndTax = calculator.calculateValueAndTax(order);
            String compoundMwst = formatter.fmtPrice(valueAndTax[1], 1);
            String compoundValue = formatter.fmtPrice(valueAndTax[0], 1);

            Iterator<OrderItem> items = order.getItems().iterator();
            while (items.hasNext()) {
                OrderItem item = items.next();
                int itemAmount = item.getUnitsOrdered();
                String itemAmountStr = String.valueOf(item.getUnitsOrdered());

                String itemDescription = item.getArticle().getDescription();

                long itemPrice = item.getArticle().getUnitPrice();
                String itemPriceStr = formatter.fmtPrice(itemPrice);

                long sumItemPrice = itemPrice * itemAmount;
                String sumItemPriceStr = formatter.fmtPrice(sumItemPrice, 1);

                TAX tax = item.getArticle().getTax();

                long includedVAT = calculator.calculateIncludedVAT(itemPrice, tax);
                String includedVATStr = formatter.fmtPrice(includedVAT);

                long sumIncludedVAT = calculator.calculateIncludedVAT(sumItemPrice, tax);
//                long sumIncludedVAT = includedVAT * itemAmount;
                String sumIncludedVATStr = formatter.fmtPrice(sumIncludedVAT);

                String formattedOrder = " - " + itemAmountStr + " " + itemDescription + ", " + itemAmountStr + "x " + itemPriceStr;


                if (tax == TAX.GER_VAT) {
                    if (!items.hasNext()) {
                        orderTable.row("", formattedOrder, sumIncludedVATStr, "", sumItemPriceStr, compoundMwst, compoundValue);
                    } else {
                        orderTable.row("", formattedOrder, sumIncludedVATStr, "", sumItemPriceStr, "", "");
                    }
                }
                if (tax == TAX.GER_VAT_REDUCED) {
                    if (!items.hasNext()) {
                        orderTable.row("", formattedOrder, sumIncludedVATStr, reducedTaxMarker, sumItemPriceStr, compoundMwst, compoundValue);
                    } else {
                        orderTable.row("", formattedOrder, sumIncludedVATStr, reducedTaxMarker, sumItemPriceStr, "", "");
                    }
                }
            }
        }
        return orderTable;
    }

    @Override
    public TablePrinter printOrders(TablePrinter orderTable, Collection<Order> orders) {
        // TODO Auto-generated method stub
        long[] totals = {    // tuple with compounded price and VAT tax values over all orders.
                0L,        // compounded order value stored in first element
                0L        // compounded order VAT tax stored in second element
        };
        //
        /*
         * TODO: E1(5) implement/change logic.
         */
        Function<Stream<Order>, Stream<Order>> function = s -> s.sorted((a, b) -> (int) (b.getTotalOrderValue()) - (int) (a.getTotalOrderValue()));
        Consumer<Order> consumer = s -> printOrder(orderTable, s).line();
        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                totals[0] += item.getArticle().getUnitPrice() * item.getUnitsOrdered();
                totals[1] += calculator.calculateIncludedVAT(item.getArticle().getUnitPrice() * item.getUnitsOrdered(), item.getArticle().getTax());
            }
        }


        //
        String totalPrice = formatter.fmtPrice(totals[0], 1);
        String totalVAT = formatter.fmtPrice(totals[1], 1);
        //
        return process(orderTable, orders, function, consumer)
                .row("@ >        |   |", "", "", "", "", "Gesamt:", totalVAT, totalPrice)
                .line("@          +=+=+");
    }

    /**
     * Generic method that converts a {@code Collection<T>} to {@code Stream<T>}
     * and applies a function @{code applyEach} to each element.
     *
     * @param <T>        generic {@code Collection} and {@code Stream} element type.
     * @param <R>        generic return type of a collector.
     * @param collector  collector with result.
     * @param collection elements to be processed.
     * @param applyEach  functional interface to process each element {@code t}.
     * @return collector with result.
     */
    public <T, R> R process(final R collector, final Collection<T> collection,
                            final Consumer<T> applyEach
    ) {
        return process(collector, collection, null, applyEach);
    }

    /**
     * Generic method that converts a {@code Collection<T>} to {@code Stream<T>},
     * allows a callout to be applied to the stream before a function @{code applyEach}
     * is applied to each remaining element.
     *
     * @param <T>        generic {@code Collection} and {@code Stream} element type.
     * @param <R>        generic return type of a collector.
     * @param collector  collector with result.
     * @param collection elements to be processed.
     * @param callout    functional interface to process stream of elements before processing function is applied.
     * @param applyEach  functional interface to process each element {@code t}.
     * @return collector with result.
     */
    public <T, R> R process(final R collector, final Collection<T> collection,
                            final Function<Stream<T>, Stream<T>> callout,
                            final Consumer<T> applyEach
    ) {
        if (collection != null) {
            (callout != null ? callout.apply(collection.stream()) : collection.stream())
                    .forEach(t -> applyEach.accept(t));
        }
        return collector;
    }
}
