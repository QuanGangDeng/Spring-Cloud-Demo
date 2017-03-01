package springCloud.eureka.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class App {
	
	@Autowired
	RestTemplate client;

	/**
	 * LoadBalanced 注解表明restTemplate使用LoadBalancerClient执行请求
	 *
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		RestTemplate template = new RestTemplate();
		SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) template
				.getRequestFactory();
		factory.setConnectTimeout(3000);
		factory.setReadTimeout(3000);
		return template;
	}

	/**
	 * 如果要访问不同服务url <br>
	 * 可以在 http://eureka.client/<br>
	 * 后添加具体路径 如：/queryOrder 默认路径为 /
	 * @return
	 */
	@RequestMapping("/loadbalance")
	public String helloWorld() {
		return client.getForObject("http://eureka.client/", String.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
