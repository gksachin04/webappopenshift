package com.example.gremlin.gremlinthinkerpop;



import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

@EnableDiscoveryClient
@SpringBootApplication
public class GremlinThinkerpopApplication{

	public static void main(String[] args) {
		SpringApplication.run(GremlinThinkerpopApplication.class, args);
	}

	@Bean
	public GraphTraversalSource getG()  {
		GraphTraversalSource g = traversal().withRemote(DriverRemoteConnection.using("gremlin-server",8182,"g"));
		return g;
	}

}




