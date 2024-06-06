package org.otus.social.dialog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.lib.dto.DialogMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DialogServiceImpl implements DialogService {

    @Autowired
    @Qualifier("masterDataSource")
    private final DataSource masterDataSource;



    @Override
    public Boolean sent(final DialogMessageDto dialogMessageDto) {

        try (final Connection con = masterDataSource.getConnection()) {
            try (final PreparedStatement insertDialog = con.prepareStatement(
                    "INSERT INTO DIALOG_MESSAGE (from_user_id, to_user_id, body, created) VALUES (?,?,?, now())", Statement.RETURN_GENERATED_KEYS)) {
                insertDialog.setLong(1, dialogMessageDto.getFromUserId());
                insertDialog.setLong(2, dialogMessageDto.getToUserId());
                insertDialog.setString(3, dialogMessageDto.getBody());
                insertDialog.executeUpdate();
            }
        } catch (Exception e) {
            log.error("Error during message persistence");
        }

        return true;
    }

    @Override
    public List<DialogMessageDto> getDialog(final Long from, final Long to) {
        final List<DialogMessageDto> dialog = new ArrayList<>();
        try (final Connection con = masterDataSource.getConnection()) {
            try (final PreparedStatement selectDialogs = con.prepareStatement(
                    """
                            select from_user_id, to_user_id, body from dialog_message dml
                            where (from_user_id = ? and to_user_id =?) or (from_user_id=? and to_user_id=?) order by created;
                            """)) {
                selectDialogs.setLong(1, from);
                selectDialogs.setLong(2,  to);
                selectDialogs.setLong(3, from);
                selectDialogs.setLong(4, to);

                try (final ResultSet selectedMessages = selectDialogs.executeQuery()) {
                    while (selectedMessages.next()) {
                        DialogMessageDto dialogMessageDto = new DialogMessageDto();
                        dialogMessageDto.setFromUserId(selectedMessages.getLong(1));
                        dialogMessageDto.setToUserId(selectedMessages.getLong(2));
                        dialogMessageDto.setBody(selectedMessages.getString(3));
                        dialog.add(dialogMessageDto);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error during message persistence");
        }
        return dialog;
    }

}
