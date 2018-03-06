package br.com.betogontijo;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinExchange;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

import br.com.betogontijo.Beans.DistributedService;

public class DistributedServiceImpl implements DistributedService {

	Exchange mercadoBitcoin = ExchangeFactory.INSTANCE.createExchange(MercadoBitcoinExchange.class.getName());
	Exchange poloniexExchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
	Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());

	@Override
	public Map<String, Set<String>> getMap() {
		Map<String, Set<String>> resp = new HashMap<String, Set<String>>();
		for (CurrencyPair currencyPair : mercadoBitcoin.getExchangeMetaData().getCurrencyPairs().keySet()) {
			String[] currencies = currencyPair.toString().split("/");
			Set<String> values = resp.get(currencies[0]);
			if (values == null) {
				values = new HashSet<String>();
			}
			values.add(currencies[1]);
			resp.put(currencies[0], values);
		}
		for (CurrencyPair currencyPair : poloniexExchange.getExchangeMetaData().getCurrencyPairs().keySet()) {
			String[] currencies = currencyPair.toString().split("/");
			Set<String> values = resp.get(currencies[0]);
			if (values == null) {
				values = new HashSet<String>();
			}
			values.add(currencies[1]);
			resp.put(currencies[0], values);
		}
		for (CurrencyPair currencyPair : bitstamp.getExchangeMetaData().getCurrencyPairs().keySet()) {
			String[] currencies = currencyPair.toString().split("/");
			Set<String> values = resp.get(currencies[0]);
			if (values == null) {
				values = new HashSet<String>();
			}
			values.add(currencies[1]);
			resp.put(currencies[0], values);
		}
		return resp;
	}

	@Override
	public Ticker convert(String from, String to) {
		Currency fromCurrency = Currency.getInstance(from);
		Currency toCurrency = Currency.getInstance(to);
		CurrencyPair currencyPair = new CurrencyPair(fromCurrency, toCurrency);
		CurrencyPairMetaData currencyPairMetaData = mercadoBitcoin.getExchangeMetaData().getCurrencyPairs()
				.get(currencyPair);
		Exchange exchange = null;
		if (currencyPairMetaData != null) {
			exchange = mercadoBitcoin;
		} else {
			currencyPairMetaData = poloniexExchange.getExchangeMetaData().getCurrencyPairs().get(currencyPair);
			if (currencyPairMetaData != null) {
				exchange = poloniexExchange;
			} else {
				currencyPairMetaData = bitstamp.getExchangeMetaData().getCurrencyPairs().get(currencyPair);
				if (currencyPairMetaData != null) {
					exchange = bitstamp;
				} else {
					return null;
				}
			}
		}
		MarketDataService marketDataService = exchange.getMarketDataService();

		Ticker ticker = null;
		try {
			ticker = marketDataService.getTicker(currencyPair);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ticker;
	}
}
