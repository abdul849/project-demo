package exmple.controller;
import exmple.payload.PostDto;
import exmple.repository.PostRepository;
import exmple.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
@Autowired
private PostRepository postRepository;
    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> creatPost(@RequestBody PostDto postDto){
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<PostDto> getPostByid(@RequestParam long id){
        PostDto dto = postService.getPostByid(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping
    public List<PostDto> getAllPosts(@RequestParam(name="PageNo",required=false,defaultValue="0") int pageNo,
                                     @RequestParam(name="Pagesize",required=false,defaultValue="3") int pageSize,
                                     @RequestParam(name="sortBy",required=false,defaultValue="id") String SortBy,
                                     @RequestParam(name="SortDir",required=false,defaultValue="id") String sortDir




    ){
        List<PostDto> postDtos = postService.getAllPosts(pageNo,pageSize,SortBy,sortDir);
        return postDtos;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        postService.deleteById(id);
        return new  ResponseEntity<>("Record is deleted",HttpStatus.OK);
    }

}
