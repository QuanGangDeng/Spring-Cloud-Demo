package springCloud.eureka.ribbon.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@EnableDiscoveryClient
@RibbonClient("11")
@RestController
@EnableFeignClients
public class App {
	
	@Autowired 
	HelloClient client;
	
	@RequestMapping("/loadbalance")
	public String helloWorld() {
		return client.hello();
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	
	/**
	 * 如果要访问不同服务url <br>
	 * 在 valu中直接指定 requestMapping 路径<br>
	 * @FeignClient("eureka.client") 中eureka.client<br>
	 * 是eureka client项目中配置的spring.application.name名称
	 * @author Administrator
	 *
	 */
	@FeignClient("eureka.client")
	interface HelloClient {
		@RequestMapping(value = "/",method=RequestMethod.GET)
		String hello();
	}
}
