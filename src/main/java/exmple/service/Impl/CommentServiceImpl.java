package exmple.service.Impl;
import exmple.entity.Comment;
import exmple.entity.Post;
import exmple.exception.ResourceNotFoundException;
import exmple.payload.CommentDto;
import exmple.repository.CommentRepository;
import exmple.repository.PostRepository;
import exmple.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;
    public CommentServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }



    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id:" + postId));


        Comment comment=new Comment();

        comment.setEmail(commentDto.getEmail());
        comment.setText(commentDto.getText());
        comment.setPost(post);

        Comment save = commentRepository.save(comment);

        CommentDto dto=new CommentDto();
        dto.setEmail(save.getEmail());
        dto.setText(save.getText());
        dto.setId(save.getId());


        return dto;
    }

    @Override
    public void createComment(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto updateComment(long id, CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("post not found with this id:" + id)
        );

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found for id:" + id)
        );


        Comment c = mapToEntiry(commentDto);
        c.setId(comment.getId());
        c.setPost(post);
        Comment save = commentRepository.save(c);
        return mapToDto(save);
    }



    CommentDto mapToDto(Comment comment){
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        return dto;
    }
    Comment mapToEntiry(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
