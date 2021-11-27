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

		System.out.println("Welcome to Zendesk Ticket Viewer! Please enter the number corresponding to your choice:");
		System.out.println("1. View all tickets");
		System.out.println("2. View individual ticket");
		System.out.println("3. Exit");
		System.out.println("");
		System.out.print("Enter your choice: ");

		int choice = sc.nextInt();

		try {
			if (choice == 1) {
				System.out.println("You have selected Option 1. Please wait while we fetch the data for you.");
				System.out.println("");
				TicketService.addAllTickets(allTicketsListURL);
				TicketService.printAllTickets();
			}
		} catch (IOException e) {
			System.out.println(
					"Sorry, there was an error reading the data while connecting. Please check whether you have entered your credentials correctly and try again later.");
			ctx.close();
		}
		System.out.println("Thank you for using the app. Have a good day ahead!");
		ctx.close();
	}

}