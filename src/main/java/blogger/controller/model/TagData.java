package blogger.controller.model;

import blogger.entity.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagData {
	private Long tagId;
	private String tagName;	
	public TagData(Tag tag) {
		tagId = tag.getTagId();
		tagName = tag.getTagName();

	}
}