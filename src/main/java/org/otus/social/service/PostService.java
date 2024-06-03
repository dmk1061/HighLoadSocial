package org.otus.social.service;

import org.otus.social.dto.PostDto;
import java.util.List;

public interface PostService {


     Boolean publish (PostDto postDto) ;

     List<PostDto> getFeed(Long userId);
}
