package org.otus.social.main.service;

import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.mappers.factories.DefaultMessagePackMapperFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.main.dto.RegisterUserDto;
import org.otus.social.main.dto.SearchRequestDto;
import org.otus.social.main.dto.UserDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Statement;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("masterDataSource")
    private final DataSource masterDataSource;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final TarantoolClient client;

    private static final DefaultMessagePackMapperFactory mapperFactory = DefaultMessagePackMapperFactory.getInstance();

    public DataSource getDataSource(boolean master) {
        return masterDataSource;
    }

    @Transactional
    @Override
    public Long registerUser(final RegisterUserDto registerUserDto) throws SQLException {
        log.info("user registration started {}", registerUserDto.getUsername());
        Long userId = null;
        try (final Connection con = masterDataSource.getConnection()) {
            userId = insertUserAndGetId(con, registerUserDto, registerUserDto.getCity());
            if (registerUserDto.getInterests() != null && !registerUserDto.getInterests().isEmpty()) {
                insertUserInterests(con, userId, registerUserDto.getInterests());
            }
        }
        return userId;
    }

    public Long insertUserAndGetId(final Connection con, final RegisterUserDto registerUserDto, final String address) throws SQLException {
        Long userId = null;
        try (final PreparedStatement insertUser = con.prepareStatement(
                "INSERT INTO USERS (name, surname, age, sex, address, username, password) values (?,?,?,?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS)) {
            insertUser.setString(1, registerUserDto.getName());
            insertUser.setString(2, registerUserDto.getSurname());
            insertUser.setLong(3, registerUserDto.getAge());
            insertUser.setString(4, registerUserDto.getSex());
            insertUser.setString(5, address);
            insertUser.setString(6, registerUserDto.getUsername());
            insertUser.setString(7, passwordEncoder.encode(registerUserDto.getPassword()));
            insertUser.executeUpdate();

            try (final ResultSet generatedKeys = insertUser.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getLong(1);
                }
            }
        }
        return userId;
    }

    private void insertUserInterests(final Connection con, final Long userId, final List<String> interests) throws SQLException {
        for (final String interest : interests) {
            final PreparedStatement selectInterest = con.prepareStatement(
                    "SELECT ID FROM INTEREST WHERE INTEREST_NAME =?;"
            );
            selectInterest.setString(1, interest);
            Long interestId = null;
            try (final ResultSet selectedInterests = selectInterest.executeQuery();) {
                if (!selectedInterests.next()) {
                    final PreparedStatement insertInterest = con.prepareStatement(
                            "INSERT INTO INTEREST (INTEREST_NAME) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                    insertInterest.setString(1, interest);
                    insertInterest.executeUpdate();

                    try (final ResultSet generatedKeys = insertInterest.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            interestId = generatedKeys.getLong(1);
                        }
                    }
                } else {
                    interestId = selectedInterests.getLong("id");
                }
            }
            final PreparedStatement insertUserInterest = con.prepareStatement(
                    "INSERT INTO USER_INTEREST (USER_ID, INTEREST_ID) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            insertUserInterest.setLong(1, userId);
            insertUserInterest.setLong(2, interestId);
            insertUserInterest.executeUpdate();
        }
    }

    @Override
    public List<UserDataDto> search(final SearchRequestDto searchRequestDto) {
        log.info("search");
        final List<UserDataDto> list = new ArrayList<>();

        try (final Connection con = masterDataSource.getConnection()) {
            final PreparedStatement selectUsers = con.prepareStatement(
                    "SELECT * FROM USERS U WHERE U.NAME LIKE ? and U.SURNAME LIKE ? ORDER BY U.ID;"

            );
            selectUsers.setString(1, "%" + searchRequestDto.getFirstName() + "%");
            selectUsers.setString(2, "%" + searchRequestDto.getLastName() + "%");
            try (final ResultSet selectedUsers = selectUsers.executeQuery();) {
                while (selectedUsers.next()) {
                    final UserDataDto userDataDto = new UserDataDto();
                    userDataDto.setName(selectedUsers.getString(2));
                    userDataDto.setSurname(selectedUsers.getString(3));
                    userDataDto.setAge(selectedUsers.getLong(4));
                    userDataDto.setSex(selectedUsers.getString(5));
                    userDataDto.setCity(selectedUsers.getString(6));
                    userDataDto.setInterests(new ArrayList<String>());
                    list.add(userDataDto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public UserDataDto getUserDataByUserIdInMemory(final Long userId) {
        final UserDataDto userDataDto = new UserDataDto();
        try {
            final List result = client.call("get_user_data", Collections.singletonList(userId)).get();
            if (!result.isEmpty()) {
                Map<String, Object> userData = (Map<String, Object>) result.get(0);
                userDataDto.setName((String) userData.get("name"));
                userDataDto.setSurname((String) userData.get("surname"));
                userDataDto.setAge(((Integer) userData.get("age")).longValue());
                userDataDto.setSex((String) userData.get("sex"));
                userDataDto.setCity(userData.get("city").toString());
                userDataDto.setInterests(new ArrayList<>());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDataDto;
    }

    @Override
    public UserDataDto getUserDataByUserId(final Long userId) {
        final UserDataDto userDataDto = new UserDataDto();
        try (final Connection con = masterDataSource.getConnection()) {
            final PreparedStatement selectUser = con.prepareStatement(
                    "SELECT * FROM USERS U  WHERE U.ID = ?;"
            );
            selectUser.setLong(1, userId);
            try (final ResultSet selectedUsers = selectUser.executeQuery();) {
                selectedUsers.next();
                userDataDto.setName(selectedUsers.getString(2));
                userDataDto.setSurname(selectedUsers.getString(3));
                userDataDto.setAge(selectedUsers.getLong(4));
                userDataDto.setSex(selectedUsers.getString(5));
                userDataDto.setCity(selectedUsers.getString(6));
                userDataDto.setInterests(new ArrayList<String>());
            }
//            final PreparedStatement selectInterests = con.prepareStatement(
//                    " SELECT * FROM USER_INTEREST UI LEFT JOIN INTEREST I ON UI.INTEREST_ID = I.ID WHERE USER_ID = ?;"
//            );
//            selectInterests.setLong(1, userId);
//            try (ResultSet rs = selectInterests.executeQuery();) {
//                while (rs.next()) {
//                    userDataDto.getInterests().add(rs.getString(5));
//                    ;
//                }
//            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userDataDto;
    }

    @Override
    public RegisterUserDto getByUserName(final String username) {
        final RegisterUserDto registerUserDto = new RegisterUserDto();
        try (final Connection con = masterDataSource.getConnection()) {
            final PreparedStatement selectUser = con.prepareStatement(
                    "SELECT * FROM USERS U  WHERE U.USERNAME = ?;"
            );
            selectUser.setString(1, username);
            try (final ResultSet rs = selectUser.executeQuery();) {
                rs.next();
                registerUserDto.setId(rs.getLong(1));
                registerUserDto.setName(rs.getString(2));
                registerUserDto.setSurname(rs.getString(3));
                registerUserDto.setAge(rs.getLong(4));
                registerUserDto.setSex(rs.getString(5));
                registerUserDto.setUsername(rs.getString(7));
                registerUserDto.setPassword(rs.getString(8));
                registerUserDto.setCity(rs.getString(6));
                registerUserDto.setInterests(new ArrayList<>());
            }

            final PreparedStatement selectInterests = con.prepareStatement(
                    " SELECT * FROM USER_INTEREST UI LEFT JOIN INTEREST I ON UI.INTEREST_ID = I.ID WHERE USER_ID = ?;"
            );
            selectInterests.setLong(1, registerUserDto.getId());
            try (ResultSet rs2 = selectInterests.executeQuery();) {
                while (rs2.next()) {
                    registerUserDto.getInterests().add(rs2.getString(5));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return registerUserDto;
    }
}
