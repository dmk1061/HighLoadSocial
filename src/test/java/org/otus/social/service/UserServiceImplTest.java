package org.otus.social.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.otus.social.dto.RegisterUserDto;
import org.otus.social.dto.UserDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.ArrayList;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest extends AbstractContainerBaseTest{
    @Autowired
    UserServiceImpl userService;
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl( POSTGRES_CONTAINER.getJdbcUrl() );
        config.setUsername( POSTGRES_CONTAINER.getUsername() );
        config.setPassword( POSTGRES_CONTAINER.getPassword());
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }



    @BeforeEach
    public void setupDatabase() {
        Flyway flyway = Flyway.configure().dataSource(POSTGRES_CONTAINER.getJdbcUrl(), POSTGRES_CONTAINER.getUsername(),
                POSTGRES_CONTAINER.getPassword()).load();
        flyway.migrate();
    }
    @Test
    public void registerUserAndGetById () throws SQLException {

      final String name = "test_user_name";
      final String surname = "test_user_sur`name";
      RegisterUserDto registerUserDto = new RegisterUserDto();
      registerUserDto.setLogin("testuser");
      registerUserDto.setPassword("123456");
      registerUserDto.setCity("London");
      registerUserDto.setAge(30L);
      registerUserDto.setInterests(new ArrayList<String>());
      registerUserDto.setName(name);
      registerUserDto.setSurname(surname);
      registerUserDto.setSex("m");

     Long userId =  userService.registerUser(registerUserDto);
     UserDataDto userDataDto = userService.getUserDataByUserId(userId);
     Assert.assertEquals(surname, userDataDto.getSurname());

    }

}
