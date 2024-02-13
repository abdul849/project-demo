package exmple.service;

import exmple.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostDto getPostByid(long id);

    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    void deleteById(long id);
}
