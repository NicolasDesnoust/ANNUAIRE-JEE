package dev.business;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import dev.dao.Dao;
import dev.dao.IDao;

@Profile("DirectoryManager-test")
@Configuration
public class DaoTestConfiguration {
	
	    @Bean
	    @Primary
	    public IDao dao() {
	        return Mockito.mock(IDao.class);
	    }
}
