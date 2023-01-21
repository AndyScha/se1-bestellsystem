package system.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import datamodel.Order;
import system.Calculator;
import system.Formatter;
import system.InventoryManager;
import system.IoC;
import system.Printer;
import system.Repository;
import system.DatamodelFactory;
import system.OrderBuilder;


/**
 * Implementation class of an "Inversion-of-Control" (IoC) container, which creates
 * and holds system component objects.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
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
	private final DatamodelFactory datamodelfactory;
	private final OrderBuilder orderbuilderJSON;
	private final OrderBuilder orderbuilder;
	private final java.util.Properties props = new Properties();
	private final InventoryManager inventorymanager;
	private final Repository repository;
	

	/**
	 * Private constructor to prevent instance creation outside this class.
	 */
	private IoC_ContainerImpl() {
		this.calculator = new CalculatorImpl();
		this.formatter = new FormatterImpl();
		this.printer = new PrinterImpl(calculator, formatter);
		this.datamodelfactory = new DatamodelFactoryImpl();
        this.inventorymanager = new InventoryManagerMOCK();
        this.repository = new RepositoryMOCK();
        this.orderbuilderJSON = new OrderBuilderJSONImpl(datamodelfactory, props, inventorymanager, repository);
        this.orderbuilder = new OrderBuilderImpl(datamodelfactory, inventorymanager, repository);
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
	
	/**
	 * Getter of system singleton component that implements the {@link DatamodelFactory} interface.
	 * 
	 * @return reference to singleton DatamodelFactory instance.
	 */
	@Override
	public DatamodelFactory getDatamodelFactory() {
		return this.datamodelfactory;
	}
	
	/**
	 * Getter of system singleton component that implements the {@link OrderBuilder} interface.
	 * 
	 * @return reference to singleton OrderBuilder instance.
	 */
	@Override
	public OrderBuilder getOrderBuilder() {
		return this.orderbuilderJSON;
	}
	
	/**
	 * Getter of system singleton component that contains system properties.
	 * @return reference to singleton Properties instance.
	 */
	@Override
	public Properties getProperties() {
		return this.props;
	}
	/**
	 * Load properties from file from propertyFilePath.
	 *
	 * @param propertyFilePath path to properties files.
	 * @return number of properties loaded.
	 * @throws IllegalArgumentException when propertyFilePath is null or empty "".
	 */
	@Override
	public int loadProperties(String propertyFilePath) {
        try {
            props.load(new FileInputStream(propertyFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.keySet().size();
	}
	
	/**
	 * Return singleton instance of InventoryManager.
	 *
	 * @return singleton instance of InventoryManager.
	 */
	public InventoryManager getInventoryManager() {
		return this.inventorymanager;
	}
	
	/**
	 * Return singleton instance of Order Repository.
	 *
	 * @return singleton instance of Order Repository.
	 */
	public Repository<Order, String> getOrderRepository() {
		return this.repository;
	}
}
