package system.impl;

import datamodel.Order;
import datamodel.TAX;
import system.Calculator;

import java.util.Map;
import java.util.Optional;


class CalculatorImpl implements Calculator {

	@Override
	public double getTaxRate(TAX taxRate) {
		// TODO Auto-generated method stub
		return taxRate != null ? taxRateMapper.get(taxRate) : 0.0;
	}

	@Override
	public long calculateIncludedVAT(long grossValue, TAX tax) {
		// TODO Auto-generated method stub
		double taxRate = getTaxRate(tax);
		double netValue = grossValue / (1 + (taxRate / 100));

		double includedVat = grossValue - netValue;

		double vat = grossValue / (getTaxRate(tax) / 100 + 1) * getTaxRate(tax);
//        return Math.round(vat/100);
		return  Math.round(includedVat);
	}

	@Override
	public long[] calculateValueAndTax(Order order) {
		// TODO Auto-generated method stub
		long[] totals = {0L, 0L};
		Optional.ofNullable(order).ifPresent(o -> o.getItems().forEach(item -> {
			int units = item.getUnitsOrdered();
			long unitPrice = item.getArticle().getUnitPrice();
			long itemPrice = unitPrice * units;
			long vat = calculateIncludedVAT(itemPrice, item.getArticle().getTax());
			totals[0] += itemPrice;    // compound item price
			totals[1] += vat;        // compound item tax
		}));
		return totals;    // return tuple with compounded {value, vat}
	}

	private final Map<TAX, Double> taxRateMapper = Map.of(
			TAX.TAXFREE, 0.0,    // tax free rate
			TAX.GER_VAT, 19.0,    // German VAT tax (MwSt) 19.0%
			TAX.GER_VAT_REDUCED, 7.0    // German reduced VAT tax (MwSt) 7.0%
	);
}
