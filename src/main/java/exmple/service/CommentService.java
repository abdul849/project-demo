package exmple.service;

import exmple.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, long postId);

    void createComment(long id);

    CommentDto updateComment(long id, CommentDto commentDto, long postId);
}
