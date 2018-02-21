package br.com.betogontijo;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import br.com.betogontijo.Beans.DistributedService;

public class DistributedServiceImpl implements DistributedService {

	@Override
	public double add(double a, double b) {
		Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
	
		MarketDataService marketDataService = bitstamp.getMarketDataService();

		Ticker ticker;
		try {
			ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
			System.out.println(ticker.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a + b;
	}

	@Override
	public double sub(double a, double b) {
		return a - b;
	}

	@Override
	public double mul(double a, double b) {
		return a * b;
	}

	@Override
	public double div(double a, double b) {
		return a / b;
	}

}
