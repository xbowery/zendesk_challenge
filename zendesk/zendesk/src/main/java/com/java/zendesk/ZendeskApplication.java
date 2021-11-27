package com.java.zendesk;

import java.io.IOException;
import java.util.Scanner;

import com.java.zendesk.model.Ticket;
import com.java.zendesk.service.TicketService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ZendeskApplication {

	private static final String allTicketsListURL = "https://{subdomain}.zendesk.com/api/v2/tickets.json";

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = SpringApplication.run(ZendeskApplication.class, args);

		Scanner sc = new Scanner(System.in);

		try {
			int choice = 0;
			while (choice != 3) {
				System.out.println(
						"Welcome to Zendesk Ticket Viewer! Please enter the number corresponding to your choice:");
				System.out.println("1. View all tickets");
				System.out.println("2. View individual ticket");
				System.out.println("3. Exit");
				System.out.println("");
				System.out.print("Enter your choice: ");
				choice = sc.nextInt();
				if (choice == 1) {
					System.out.println("You have selected Option 1. Please wait while we fetch the data for you.");
					System.out.println("");
					TicketService.addAllTickets(allTicketsListURL);
					TicketService.printAllTickets(1);

					if (toExit()) {
						break;
					}
				}
			}
		} catch (IOException e) {
			System.out.println(
					"Sorry, there was an error reading the data while connecting. Please check whether you have entered your credentials correctly and try again later.");
			ctx.close();
		}
		System.out.println("Thank you for using the app. Have a good day ahead!");
		ctx.close();
	}

	private static boolean toExit() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you wish to use the other functionalities of the application again? (Y/N)");
		System.out.print("Enter your choice: ");
		String input = sc.nextLine();
		System.out.println("");
		while (!(input.equals("Y") || input.equals("N") || input.equals("y") || input.equals("n"))) {
			System.out.println("Invalid input. Please enter only Y or N.");
			System.out.println("Do you wish to use the other functionalities of the application again? (Y/N)");
			System.out.print("Enter your choice: ");
			input = sc.nextLine();
			System.out.println("");
		}

		if (input.equals("N") || input.equals("n")) {
			return true;
		}
		return false;
	}
}