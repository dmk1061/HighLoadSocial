package org.otus.social.service;

import org.otus.social.dto.DialogMessageDto;


import java.util.List;

public interface DialogService {


    Boolean sent (DialogMessageDto dialogMessageDto) ;

    List<DialogMessageDto> getDialog(String username, Long id);
}
