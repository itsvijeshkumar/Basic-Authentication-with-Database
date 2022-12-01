package com.payment.gateway.app.config;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;

@ConditionalOnProperty(name="spring.profiles.active", havingValue="dev")
@Configuration
//@Profile({"dev", "test"})
public class AppConfig {
	
	@Autowired
	private Environment env;
	private static Map<String, String> properties = new HashMap<String, String>();
	
	static {
		
		properties.put("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
		properties.put("spring.datasource.url", "jdbc:mysql://127.0.0.1:3306/test");
		properties.put("spring.datasource.username", "root");
		properties.put("spring.datasource.password", "");
		//properties.put("spring.datasource.url", "jdbc:db2://172.16.1.99:50000/RAKDB");		
		//properties.put("spring.datasource.url", "jdbc:db2://172.16.1.159:50000/RAKDB2");
		//properties.put("spring.datasource.password", "Welcome@123");

	}
	


	@Bean(destroyMethod="")
	public DataSource dataSource() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:comp/env/"+ env.getProperty("spring.datasource.jndi-name"));
		//bean.setResourceRef(true); // this was previously uncommented
		bean.setProxyInterface(DataSource.class);
		//bean.setLookupOnStartup(false); // this was previously uncommented
		bean.afterPropertiesSet();
		return (DataSource)bean.getObject();
	}

	@Bean
	public TomcatServletWebServerFactory tomcatFactory() {
		return new TomcatServletWebServerFactory() {
			@Override
			protected TomcatWebServer getTomcatWebServer(org.apache.catalina.startup.Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatWebServer(tomcat);
			}

			@Override
			protected void postProcessContext(Context context) {
				ContextResource resource = new ContextResource();           
				resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
				resource.setName( env.getProperty("spring.datasource.jndi-name"));
				resource.setType(DataSource.class.getName());
				resource.setProperty("driverClassName",  properties.get("spring.datasource.driver-class-name"));
				resource.setProperty("url", properties.get("spring.datasource.url") );
				resource.setProperty("username", properties.get("spring.datasource.username"));
				resource.setProperty("password",  properties.get("spring.datasource.password"));
				context.getNamingResources().addResource(resource);
			}
		};
	}
}
