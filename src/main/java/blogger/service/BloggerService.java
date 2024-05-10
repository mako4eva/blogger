package blogger.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import blogger.controller.model.PostData;
import blogger.controller.model.TagData;
import blogger.controller.model.UserData;
import blogger.dao.TagDao;
import blogger.dao.PostDao;
import blogger.dao.UserDao;
import blogger.entity.Tag;
import blogger.entity.User;
import blogger.entity.Post;

@Service
public class BloggerService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private TagDao tagDao;

	public List<PostData> retrieveAllPosts() {
		List<Post> posts = postDao.findAll();
		List<PostData> result = new LinkedList<>();
		for (Post post : posts) {
			PostData postData = new PostData(post);
			postData.getTags().clear();
			result.add(postData);
		}
		return result;
	}

	public PostData savePost(PostData postData) {
		Long postId = postData.getPostId();
		Post post = findOrCreatePost(postId);
		return new PostData(postDao.save(copyPostFields(post, postData)));
	}

	private Post copyPostFields(Post post, PostData postData) {
		post.setPostId(postData.getPostId());
		post.setPostTitle(postData.getPostTitle());
		post.setPostContent(postData.getPostContent());
		post.setUser(postData.getUser());

		return post;
	}

	public Post findOrCreatePost(Long postId) {
		if (postId == null) {
			return new Post();
		} else {
			return findPostById(postId);
		}
	}

	public Post findPostById(Long postId) {
		Post post = postDao.findById(postId)
				.orElseThrow(() -> new NoSuchElementException("Post with Id=" + postId + " does not exist"));
		return post;
	}

	public void deletePostById(Long postId) {
		Post post = findPostById(postId);
		postDao.delete(post);
	}

	private User findOrCreateUser(Long userId) {
		if (userId == null) {
			return new User();
		} else {
			return findUserById(userId);
		}

	}

	public User findUserById(Long userId) {

		return userDao.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User with Id=" + userId + " does not exist"));
	}

	private User copyUserFields(User user, UserData userData) {
		user.setUserName(userData.getUserName());
		user.setUserId(userData.getUserId());
		for (PostData postData : userData.getPosts()) {
			Post post = new Post();
			user.getPosts().add(copyPostFields(post, postData));
		}
		user.setUserEmail(userData.getUserEmail());
		return user;

	}

	public UserData saveUser(UserData userData) {
		Long userId = userData.getUserId();
		User user = findOrCreateUser(userId);
		return new UserData(userDao.save(copyUserFields(user, userData)));
	}


	private Tag findOrCreateTag(Long tagId) {
		if (tagId == null) {
			return new Tag();
		} else {
			return findTagById(tagId);
		}
	}

	public TagData saveTag(TagData tagData) {
		Long tagId = tagData.getTagId();
		Tag tag = findOrCreateTag(tagId);
		return new TagData(tagDao.save(copyTagFields(tag, tagData)));
	}

	public Tag findTagById(Long tagId) {
		return tagDao.findById(tagId)
				.orElseThrow(() -> new NoSuchElementException("Tag with Id=" + tagId + " does not exist"));

	}

	private Tag copyTagFields(Tag tag, TagData tagData) {
		tag.setTagName(tagData.getTagName());
		tag.setTagId(tagData.getTagId());
		return tag;

	}

	public PostData savePostTag(Long tagId,Long postId) {
		Post post = findPostById(postId);
		Tag tag = findTagById(tagId);
		post.getTags().add(tag);
		tagDao.save(tag);
		postDao.save(post);
		return new PostData(post);
	}


}
