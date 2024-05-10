package blogger.controller.model;

import java.util.HashSet;
import java.util.Set;

import blogger.entity.Post;
import blogger.entity.Tag;
import blogger.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class PostData {

	private Long postId;
	private String postTitle, postContent;
	private Set<TagData> tags = new HashSet<>();
	private User user;

	public PostData(Post post) {
		postId = post.getPostId();
		postTitle = post.getPostTitle();
		postContent = post.getPostContent();
		if (post.getTags() != null)
			for (Tag tag : post.getTags()) {
				tags.add(new TagData(tag));
			}
		user = post.getUser();

	}

}