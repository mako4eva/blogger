package blogger.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.*;
import blogger.entity.Post;
import blogger.entity.User;

@Data
@NoArgsConstructor
public class UserData {
	private Long userId;
	private String userName;
	private String userEmail;
	private Set<PostData> posts = new HashSet<>();

	public UserData(User user) {
		userId = user.getUserId();
		userName = user.getUserName();
		userEmail = user.getUserEmail();
		if (user.getPosts() != null)
			for (Post post : user.getPosts()) {
				posts.add(new PostData(post));

			}

	}

}
