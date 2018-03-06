package br.com.betogontijo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import br.com.betogontijo.Beans.DistributedService;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		Window window = new Window();
		window.execute();
	}

	@Bean
	RmiProxyFactoryBean service() {
		RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
		rmiProxyFactory.setServiceUrl("rmi://localhost:1099/DistributedService");
		rmiProxyFactory.setServiceInterface(DistributedService.class);
		return rmiProxyFactory;
	}
}
