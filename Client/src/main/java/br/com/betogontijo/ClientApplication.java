package br.com.betogontijo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import br.com.betogontijo.Beans.DistributedService;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(ClientApplication.class, args);
		DistributedService service = applicationContext.getBean(DistributedService.class);
		double bookingOutcome = service.add(3, 2);
		System.out.println(bookingOutcome);
		applicationContext.close();
	}

	@Bean
	RmiProxyFactoryBean service() {
		RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
		rmiProxyFactory.setServiceUrl("rmi://localhost:1099/DistributedService");
		rmiProxyFactory.setServiceInterface(DistributedService.class);
		return rmiProxyFactory;
	}
}
