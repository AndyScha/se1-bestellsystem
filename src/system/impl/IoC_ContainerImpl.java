package system.impl;

import datamodel.Order;
import system.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Implementation class of an "Inversion-of-Control" (IoC) container, which creates
 * and holds system component objects.
 *
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 */

public final class IoC_ContainerImpl implements IoC {

    /**
     * Private static singleton IoC_ContainerImpl instance.
     */
    private static final IoC_ContainerImpl singleton = new IoC_ContainerImpl();

    /**
     * References to singleton objects that implement system component interfaces.
     */
    private final Calculator calculator;
    private final Formatter formatter;
    private final Printer printer;
    private final DatamodelFactory datamodelFactory;
    private final OrderBuilder orderBuilder;
    private final OrderBuilder orderBuilderOld;

    private final Properties props;


    /**
     * Private constructor to prevent instance creation outside this class.
     */
    private IoC_ContainerImpl() {
        this.calculator = new CalculatorImpl();
        this.formatter = new FormatterImpl();
        this.printer = new PrinterImpl(calculator, formatter);
        this.props = new Properties();
        this.datamodelFactory = new DatamodelFactoryImpl();
        this.orderBuilder = new OrderBuilderJSONImpl(datamodelFactory, props);
        this.orderBuilderOld = new OrderBuilderImpl(datamodelFactory);
    }


    /**
     * Getter of singleton instance that implements the {@link IoC} interface.
     *
     * @return reference to singleton IoC instance.
     */
    public static IoC getInstance() {
        return singleton;
    }


    /**
     * Getter of system singleton component that implements the {@link Calculator} interface.
     *
     * @return reference to singleton Calculator instance.
     */
    @Override
    public Calculator getCalculator() {
        return this.calculator;
    }


    /**
     * Getter of system singleton component that implements the {@link Formatter} interface.
     *
     * @return reference to singleton Formatter instance.
     */
    @Override
    public Formatter getFormatter() {
        return this.formatter;
    }


    /**
     * Getter of system singleton component that implements the {@link Printer} interface.
     *
     * @return reference to singleton Printer instance.
     */
    @Override
    public Printer getPrinter() {
        return this.printer;
    }

    public OrderBuilder getOrderBuilder(){
        return this.orderBuilder;
    }

    public DatamodelFactory getDatamodelFactory() {
        return datamodelFactory;
    }

    @Override
    public Properties getProperties() {
        return this.props;
    }

    @Override
    public int loadProperties(String propertyFilePath) {
        try {
            props.load(new FileInputStream(propertyFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.keySet().size();
    }
}
