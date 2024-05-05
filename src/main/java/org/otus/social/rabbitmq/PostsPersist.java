package org.otus.social.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostsPersist {

    private final ObjectMapper objectMapper;
    @Autowired
    @Qualifier("masterDataSource")
    private final DataSource masterDataSource;

  //  private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(byte[] byteArray) throws JsonProcessingException {
        String message = new String(byteArray, StandardCharsets.UTF_8); try {
        final PostDto postDto = objectMapper.readValue(message, PostDto.class);
        persistPost(postDto);
        log.info("Received <" + message + ">");
 //       latch.countDown();
        }catch (Exception e){
            log.error("PostReceiver error " + e.getMessage());
        }
    }
    public void persistPost (final PostDto postDto){
        try(final Connection con = masterDataSource.getConnection()) {
            try (final PreparedStatement insertSubscription = con.prepareStatement(
                    "INSERT INTO POSTS (USERNAME, BODY, CREATED) VALUES (?,?,NOW())", Statement.RETURN_GENERATED_KEYS)) {
                insertSubscription.setString(1, postDto.getUsername());
                insertSubscription.setString(2, postDto.getBody());
                insertSubscription.executeUpdate();
            }
        }catch (Exception e) {
            log.error("Error during subscription persistence");
        }
    }
//    public CountDownLatch getLatch() {
//        return latch;
//    }
}