package blogger.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import blogger.controller.model.PostData;
import blogger.controller.model.TagData;
import blogger.controller.model.UserData;
import blogger.entity.Post;
import blogger.service.BloggerService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/blogger")
@Slf4j
public class BloggerController {
	@Autowired
	private BloggerService bloggerService;

	@PostMapping("/post/{userId}")
	public ResponseEntity<String> createPost(@RequestBody PostData postData, @PathVariable Long userId) {
		postData.setUser(bloggerService.findUserById(userId));
		log.info("Received POST request for creating a post: " + postData);
		
		postData = bloggerService.savePost(postData);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("User \""+postData.getUser().getUserName()+"\" added post \""+postData.getPostTitle() + "\".");
	}

	@GetMapping("/post")
    public List<PostData> retrieveAllPosts() {
		List<PostData> posts = bloggerService.retrieveAllPosts();
		for (PostData postData : posts) {
			postData.getUser().getPosts().clear();
		}
		return posts;
    }
    
	@Transactional
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable Long postId) {
    	log.info("Received DELETE request for post ID={}", postId);
    	String title = bloggerService.findPostById(postId).getPostTitle();
    	bloggerService.deletePostById(postId);
    	return ResponseEntity.status(HttpStatus.CREATED).body("Post \"" + title + "\" has been deleted.");
    	
    }
	
    @GetMapping("/post/{postId}")
    public PostData retrievePostById(@PathVariable Long postId) {
    	Post post = bloggerService.findPostById(postId);
    	post.getUser().getPosts().clear();
        return new PostData(post);
    }
    
	@PutMapping("/post/{postId}")
	public ResponseEntity<String> updatePost(@PathVariable Long postId, @RequestBody PostData postData) {
		postData.setPostId(postId);
		log.info("Received PUT request for updating post ID={} to {}", postId, postData);
		bloggerService.savePost(postData);
		return ResponseEntity.status(HttpStatus.CREATED).body("Post \"" + postData.getPostTitle() + "\" has been updated.");

	}

	@PostMapping("/user")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> createUser(@RequestBody UserData userData) {
		
		log.info("Received POST request for creating a user: " + userData);
		bloggerService.saveUser(userData);
		return ResponseEntity.status(HttpStatus.CREATED).body("Created user \"" + userData.getUserName() + "\".");

	}

	
	@PostMapping("/tag")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> createTag(@RequestBody TagData tagData) {
		log.info("Received POST request for creating a tag: " + tagData);
		bloggerService.saveTag(tagData);
		return ResponseEntity.status(HttpStatus.CREATED).body("Tag \"" + tagData.getTagName() + "\" has been created.");
	}
	
	
	@PutMapping("/tag/{tagId}/post/{postId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> addTagToPost(@PathVariable Long tagId,@PathVariable Long postId) {
		bloggerService.savePostTag(tagId,postId);
		return ResponseEntity.status(HttpStatus.CREATED).body("Tag \""+bloggerService.findTagById(tagId).getTagName()+"\" was added to post \"" + bloggerService.findPostById(postId).getPostTitle()+"\".");
	}
	

}
