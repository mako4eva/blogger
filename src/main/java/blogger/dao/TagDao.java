package blogger.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import blogger.entity.Tag;


public interface TagDao extends JpaRepository<Tag,Long> {

}
