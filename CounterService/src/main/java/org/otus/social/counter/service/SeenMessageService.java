package org.otus.social.counter.service;

import java.util.List;

public interface SeenMessageService {


    Boolean seen(Long id, Long messageId);

    List<Long> getSeen ();

    Boolean rollBack(List<Long> ids);
}
