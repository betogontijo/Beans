package br.com.betogontijo.Beans;

import java.util.Map;
import java.util.Set;

public interface DistributedService {

	CustomTicker convert(String from, String todouble);

	Map<String, Set<String>> getMap();

}
