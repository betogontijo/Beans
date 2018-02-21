package br.com.betogontijo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;

import br.com.betogontijo.Beans.DistributedService;

@SpringBootApplication
public class DistributedApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedApplication.class, args);
	}

	@Bean
	DistributedService distributedService() {
		return new DistributedServiceImpl();
	}
	
	@Bean
	RmiServiceExporter exporter(DistributedService implementation) {
	    Class<DistributedService> serviceInterface = DistributedService.class;
	    RmiServiceExporter exporter = new RmiServiceExporter();
	    exporter.setServiceInterface(serviceInterface);
	    exporter.setService(implementation);
	    exporter.setServiceName(serviceInterface.getSimpleName());
	    exporter.setRegistryPort(1099); 
	    return exporter;
	}
}
