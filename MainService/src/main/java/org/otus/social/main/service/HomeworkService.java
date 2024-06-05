package org.otus.social.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.main.SocialNetwork;
import org.otus.social.dto.RegisterUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeworkService {


    private   final UserServiceImpl userService;
    @Autowired
    @Qualifier("masterDataSource")
    private final DataSource masterDataSource;

   // @Scheduled(fixedRate = 100000000L)
    public void test() throws IOException {
        final List<List<String>> records = new ArrayList<>();
        final String fileName = "1.txt";
        final ClassLoader classLoader = SocialNetwork.class.getClassLoader();
        final InputStream inputStream = classLoader.getResourceAsStream(fileName);
        final List<RegisterUserDto> newUsers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)); BufferedWriter writer = new BufferedWriter(new FileWriter("V0_0_3__insert_test_index.sql"));) {
            int a = 1700000;
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] comasplit = line.split(",");
                final String[] namesplit = comasplit[0].split(" ");
                final String insertAddress = String.format("INSERT INTO ADDRESS (city) VALUES  ('%s'); \n", comasplit[2]);
                final String insertUser = String.format("INSERT INTO USERS (name, surname, age, sex, address, username, password) VALUES " +
                                "  ('%s', '%s', %d, 'M', 1, '%s', crypt('%s', gen_salt('bf', 10))); \n", namesplit[1], namesplit[0],
                        Long.valueOf(comasplit[1]), "username" + a, "pass" + a + "; ");
                a = a + 1;

                //        writer.write(insertUser);
                int ad = 0;
                final RegisterUserDto registerUserDto = new RegisterUserDto();
                registerUserDto.setSurname(namesplit[0]);
                registerUserDto.setName(namesplit[1]);
                registerUserDto.setAge(Long.valueOf(comasplit[1]));
                registerUserDto.setSex("m");
                registerUserDto.setCity(comasplit[2]);
                registerUserDto.setUsername("" + a);
                registerUserDto.setPassword("" + a++);
                registerUserDto.setInterests(new ArrayList<>());
                newUsers.add(registerUserDto);
                System.out.println(line);

            }
//            try (final Connection con = dataSource.getConnection()) {
//                insertUserAndGetId(con, newUsers, 1L);
//            }
 //           writeToSQLFile(newUsers);
            registerUsers(newUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void writeToSQLFile(List<RegisterUserDto> userList) {
        final HashMap<String, Integer > addressId = new HashMap<>();
        int id = 16;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("2.sql"))) {
            for (RegisterUserDto user : userList) {
                final String addressInsert = String.format("INSERT INTO ADDRESS (id, city) VALUES (%d, '%s');\n", id, user.getCity());
                final boolean writeAdress = addressId.get(user.getCity())== null;
                if(writeAdress) {
                    addressId.put(user.getCity(), id);
                }
                id = id+1;

                final String userInsert = String.format("INSERT INTO USERS (id, name, surname, age, sex, address, username, password) VALUES (%d, '%s', '%s', %d, '%s', %d, '%s', '%s');\n",
                        id,user.getName(), user.getSurname(), user.getAge(), user.getSex(), addressId.get(user.getCity()), user.getUsername(), user.getPassword());
                id = id+1;
                log.info(""+id);
                if(writeAdress){
                    writer.write(addressInsert);
                }

                writer.write(userInsert);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Transactional
    public Long registerUsers(final List<RegisterUserDto> list) throws SQLException {

        Long userId = null;
        try (final Connection con = masterDataSource.getConnection()) {
            for (RegisterUserDto registerUserDto : list) {

                userId = userService.insertUserAndGetId(con, registerUserDto, registerUserDto.getCity());
                log.info("user registration finished {}", registerUserDto.getUsername());
                //    insertUserInterests(con, userId, registerUserDto.getInterests());
            }
        }
        return userId;
    }


}
