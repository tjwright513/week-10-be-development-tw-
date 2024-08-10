package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	private Project curProject;
	
	
	private List<String> operations = List.of(
			"1) Add a project", 
			"2) List projects",
			"3) Select a Project");
	
	public static void main(String[] args) {
		new ProjectsApp().processUserSections();
	}

	private void processUserSections() {
		boolean done = false;
		while(!done) {
			try {
				int selection = getUserSelection();
				
				switch(selection) {
				case -1:
				done = exitMenu();
				break;
				
				case 1:
				createProject();
				break;
				
				case 2:
				listProjects();
				break;
				
				case 3:
			    selectProject();
			    break;
				
				default:
					System.out.println("/n" + selection + " is not a vald selection. Try again");
				}
			}
			catch(Exception e) {
				System.out.println("/nError: " + e + " Try again.");
			}
		}
		}
		private void selectProject() {
		listProjects();
		Integer projectId = getIntInput("Enter a project ID to select a project");
		curProject = null;
		curProject = projectService.fetchProjectById(projectId); 
	}

		private void listProjects() {
		   List<Project> projects = projectService.fetchAllProjects();
		   System.out.println("n/Projects:");
		   projects.forEach(project -> System.out.println("   " + project.getProjectId() + ": " + project.getProjectName()));
		
	}

		private void createProject(){
			String projectName = getStringInput("Enter the Project Name");
			BigDecimal estimatedHours = getDecimalInput("Enter the estimate hours");
			BigDecimal actualHours = getDecimalInput("Enter the actual hours");
			Integer diffuculty = getIntInput("Enter the priject difficulty (1-5)");
			String notes = getStringInput("Enter the project notes");
			
			Project project = new Project();
			
			project.setProjectName(projectName);
			project.setEstimatedHours(estimatedHours);
			project.setActualHours(actualHours);
			project.setDifficulty(diffuculty);
			project.setNotes(notes);
			
			Project dbProject = projectService.addProject(project);
			System.out.println("You have successfully created project: " + dbProject);
		}
		private BigDecimal getDecimalInput(String prompt) {
			String input = getStringInput(prompt);
			if(Objects.isNull(input)) {
				return null;
			}
			try {
				return new BigDecimal(input).setScale(2);
			}
			catch(NumberFormatException e) {
				throw new DbException(input + " is not a valad decimal number.");
			}
		}
		private boolean exitMenu() {
			System.out.println("Exit the Menu.");
			return true;
		}

		
		private Integer getIntInput(String prompt) {
			String input = getStringInput(prompt);
			if(Objects.isNull(input)) {
				return null;
			}
			
			try {
				return Integer.valueOf(input);
			}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valad number.");
		}
		}
		private String getStringInput(String prompt) {
			System.out.print(prompt + ": ");
			String input = scanner.nextLine();
			
			return input.isBlank() ? null : input.trim();
		}




	private int getUserSelection(){
		printOperations();
		
		Integer input = getIntInput("Enter a menu selection");
		
		return Objects.isNull(input) ? -1 : input;
	
	}



	private void printOperations() {
		System.out.println("/nThese are the avaiable selections. Press Eneter key to quite");
		operations.forEach(line -> System.out.println(" " + line));
		if(Objects.isNull(curProject)) {
			System.out.println("\nYou are working with project.");
		}
		else {
			System.out.println("\nYou are working with project: " + curProject);
			}
		}
	
}



