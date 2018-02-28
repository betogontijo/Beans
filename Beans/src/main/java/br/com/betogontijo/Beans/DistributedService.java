package br.com.betogontijo.Beans;

import java.util.Set;

import org.knowm.xchange.dto.marketdata.Ticker;

public interface DistributedService {

	Ticker convert(String from, String todouble);

	Set<String> getList();

}
