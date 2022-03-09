package com.doapp.doApp;

import com.doapp.doApp.service.ListService;
import com.doapp.doApp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DoAppApplication implements ApplicationRunner {

	@Autowired
	ListService ls;
	@Autowired
	TaskService ts;

	public static void main(String[] args) {
		SpringApplication.run(DoAppApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		int choice = 99;
		System.out.println("Hello TODO task");
		Scanner scanner = new Scanner(System.in);
		String user = "";
		String currentList = "";
		do {
			System.out.println("Menu. Current user: " + user + "\n" +
					"0. Exit\n" +
					"1. User login\n" +
					"2. Add task\n" +
					"3. Complete task\n" +
					"4. List tasks\n" +
					"5. Delete task\n" +
					"6. List all lists\n" +
					"7. Change current list\n" +
					"8. Create new list\n" +
					"9. Delete list\n" +
					"10. Add user to list\n" +
					"11. Remove user from list\n" +
					"12. Change current list\n" +
					"13. Change current list\n"
			);
			choice = scanner.nextInt();
			switch (choice) {
				case 0:
					System.exit(0);
					break;
				case 1:
					user = scanner.next();
					break;
				case 2:
					doAddTask(user, 1);
					break;
				case 3:
					doCompleteTask(user, 1, 1);
					break;
				case 4:
					doListTasks(user);
					break;
			}

		} while (choice != 0);

	}
//	@Autowired
//	RestTemplate rt;

	private void doListTasks(String user) {
		System.out.println("Will list tasks for user '" + user + "'");
//		rt.getForObject("/api/")
		ls.getTaskLists(user);
	}

	private void doCompleteTask(String user, Integer listId, Integer taskId) {
		System.out.println("Will complete task in currentList '" + listId + "' for user '" + user + "'");
	}

	private void doAddTask(String user, Integer listId) {
		System.out.println("Will add task into currentList '" + listId + "' for user '" + user + "'");
		// ask user for task content...
		ts.addTask(user, listId, "task content here");
	}
}
