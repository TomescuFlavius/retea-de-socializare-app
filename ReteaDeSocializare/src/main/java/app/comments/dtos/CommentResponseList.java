package app.comments.dtos;

import java.util.List;

public record CommentResponseList(
        List<CommentResponse> commentResponseList
) {}