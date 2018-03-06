package br.com.betogontijo.Beans;

import java.util.Map;
import java.util.Set;

import org.knowm.xchange.dto.marketdata.Ticker;

public interface DistributedService {

	Ticker convert(String from, String todouble);

	Map<String, Set<String>> getMap();

}
