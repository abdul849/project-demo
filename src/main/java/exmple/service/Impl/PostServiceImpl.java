package exmple.service.Impl;
import exmple.entity.Post;
import exmple.exception.ResourceNotFoundException;
import exmple.payload.PostDto;
import exmple.repository.PostRepository;
import exmple.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }


    @Override
    public PostDto createPost(PostDto postDto) {

        Post post=mapToEntity(postDto);
        Post  savePost = postRepository.save(post);

        PostDto dto=mapToDto(savePost);
        return dto;
    }

    @Override
    public PostDto getPostByid(long id) {
        Post post = postRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("post not found with id:" + id));

        PostDto dto=new PostDto();
        dto.setContent(post.getContent());
        dto.setDescription(post.getDescription());
        dto.setTitle(post.getTitle());

        return dto;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<Post> pagePost = postRepository.findAll(pageable);
        List<Post> posts = pagePost.getContent();

        List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public void deleteById(long id){
        postRepository.deleteById(id);
    }



    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }
    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }
}
