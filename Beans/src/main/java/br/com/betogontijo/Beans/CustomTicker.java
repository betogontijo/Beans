package br.com.betogontijo.Beans;

import java.io.Serializable;

import org.knowm.xchange.dto.marketdata.Ticker;

public class CustomTicker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4860168332530697192L;
	private Ticker ticker;
	private String market;

	public Ticker getTicker() {
		return ticker;
	}

	public void setTicker(Ticker ticker) {
		this.ticker = ticker;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

}
