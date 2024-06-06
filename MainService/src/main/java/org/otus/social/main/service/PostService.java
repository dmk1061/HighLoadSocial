package org.otus.social.main.service;

import java.util.List;
import org.otus.social.main.dto.PostDto;

public interface PostService {


     Boolean publish (PostDto postDto) ;

     List<PostDto> getFeed(Long userId);
}
