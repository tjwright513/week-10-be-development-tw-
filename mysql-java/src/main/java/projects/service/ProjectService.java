package projects.service;

import java.util.List;
import java.util.NoSuchElementException;

import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;



public class ProjectService {
   private ProjectDao projectDao = new ProjectDao();

public ProjectDao getProjectDao() {
	return projectDao;
}

public void setProjectDao(ProjectDao projectDao) {
	this.projectDao = projectDao;
}

public Project addProject(Project project) throws DbException {
	;
	try {
		return projectDao.insertProject(project);
	} catch (Throwable e) {
		e.printStackTrace();
	}
	return project;
}

public List<Project> fetchAllProjects() {
	return projectDao.fetchAllProjects();
}

public Project fetchProjectById(Integer projectId) {
	return projectDao.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException("Project with projectID=" + projectId + " des not exist."));
}
}
   
   
