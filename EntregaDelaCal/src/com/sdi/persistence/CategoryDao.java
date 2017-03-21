package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Category;


public interface CategoryDao {

	List<Category> findByUserId(Long userId);
	int deleteAllFromUserId(Long userId);
	Category findByUserIdAndName(Long userId, String name);

}
