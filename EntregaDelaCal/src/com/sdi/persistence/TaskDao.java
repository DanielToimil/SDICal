package com.sdi.persistence;

import java.util.List;

import com.sdi.persistence.util.GenericDao;

import com.sdi.model.Task;

public interface TaskDao extends GenericDao<Task, Long> {

	List<Task> findByUserId(Long userId);
	int deleteAllFromUserId(Long userId);
	int deleteAllFromCategory(Long catId);
	List<Task> findInboxTasksByUserId(Long userId);
	List<Task> findTodayTasksByUserId(Long userId);
	List<Task> findWeekTasksByUserId(Long userId);
	List<Task> findTasksByCategoryId(Long catId);
	List<Task> findFinishedTasksByCategoryId(Long catId);
	List<Task> findFinishedTasksInboxByUserId(Long userId);

}