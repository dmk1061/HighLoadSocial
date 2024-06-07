package org.otus.social.dialog.service;


import org.otus.social.lib.dto.DialogMessageDto;

import java.util.List;

public interface DialogService {

    Boolean sent (DialogMessageDto dialogMessageDto) ;

    List<DialogMessageDto> getDialog(Long from, Long to);

    Boolean updateSeen (List<Long> messages);


    Boolean sentV2 (DialogMessageDto dialogMessageDto) ;

    List<DialogMessageDto> getDialogV2(Long from, Long to);

    Boolean updateSeenV2 (List<Long> messages);
}

