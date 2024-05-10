package blogger.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import blogger.entity.Post;


public interface PostDao extends JpaRepository<Post,Long> {

}
