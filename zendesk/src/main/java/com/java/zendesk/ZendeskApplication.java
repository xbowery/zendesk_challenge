package com.java.zendesk;

import java.io.IOException;
import java.net.UnknownHostException;
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
			System.out.println("Good day to you!");
			System.out.println("");
			System.out.println("The application is loading...");
			System.out.println("");

			TicketService.addAllTickets(allTicketsListURL);

			printWelcomeScreen();

			int choice = 0;
			while (choice != 3) {
				printOptionScreen();
				choice = sc.nextInt();
				System.out.println("");

				if (choice == 1) {
					System.out.println("You have selected Option 1. Please wait while we fetch the data for you.");
					System.out.println("");

					TicketService.printAllTickets(1);

					if (toExit()) {
						break;
					}
				} else if (choice == 2) {
					System.out.println("You have selected Option 2.");
					System.out.print("Please enter the ticket ID which you wish to view: ");
					int ticketId = sc.nextInt();

					System.out.println("");

					Ticket ticket = TicketService.getIndividualTicket(ticketId);

					if (ticket != null) {
						TicketService.printIndividualTicket(ticket);
					} else {
						System.out.println("Sorry, there is no ticket found with ID: " + ticketId + ".");
					}

					if (toExit()) {
						break;
					}
				} else if (choice == 3) {
					break;
				} else {
					System.out.println(
							"Sorry, you have entered an invalid option. Please enter only 1, 2 or 3. Please try again.");
					System.out.println("");
				}
			}
		} catch (UnknownHostException e) {
			System.out.println(
					"Sorry, there was an error connecting to the Zendesk server. Please ensure that you are connected to the Internet.");
		} catch (IOException e) {
			System.out.println(
					"Sorry, there was an error reading the data while connecting. Please check whether you have entered your credentials correctly and try again later.");
			ctx.close();
		}
		System.out.println("Thank you for using the app. Have a good day ahead!");
		sc.close();
		ctx.close();
	}

	public static void printWelcomeScreen() {
		System.out.println("__________                 .___             __    ");
		System.out.println("\\____    /____   ____    __| _/____   _____|  | __");
		System.out.println("  /     // __ \\ /    \\  / __ |/ __ \\ /  ___/  |/ /");
		System.out.println(" /     /\\  ___/|   |  \\/ /_/ \\  ___/ \\___ \\|    < ");
		System.out.println("/_______ \\___  >___|  /\\____ |\\___  >____  >__|_ \\");
		System.out.println("        \\/   \\/     \\/      \\/    \\/     \\/     \\/");
	}

	private static void printOptionScreen() {
		System.out.println("Welcome to Zendesk Ticket Viewer! Please enter the number corresponding to your choice:");
		System.out.println("1. View all tickets");
		System.out.println("2. View individual ticket");
		System.out.println("3. Exit");
		System.out.println("");
		System.out.print("Enter your choice: ");
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