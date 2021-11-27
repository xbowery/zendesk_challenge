package com.java.zendesk.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import com.java.zendesk.model.Ticket;
import com.java.zendesk.repository.TicketRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private static final String authPassword = "{email_address}:{password}";

    // private static final String authToken = "{email_address}/token:{api_token}";

    private static final String encodedAuthPassword = Base64.getEncoder().encodeToString(authPassword.getBytes());

    // private static final String encodedAuthToken =
    // Base64.getEncoder().encodeToString(authToken.getBytes());

    private static final int maxTicketPerPage = 25;

    private static int totalTicketCount;

    private static List<Ticket> ticketList = new ArrayList<>();

    private static TicketRepository tickets;

    @Autowired
    public TicketService(TicketRepository tickets) {
        this.tickets = tickets;
    }

    public static void addAllTickets(String urlPath) throws IOException {
        int index = 0;

        URL url = new URL(urlPath);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + encodedAuthPassword);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        JSONArray getTickets = jsonObject.getJSONArray("tickets");
        totalTicketCount = getTickets.length();

        while (index < totalTicketCount) {
            JSONObject getIndividualTicket = getTickets.getJSONObject(index);
            int id = (Integer) getIndividualTicket.get("id");
            String created_at = getIndividualTicket.get("created_at").toString();
            String updated_at = getIndividualTicket.get("updated_at").toString();
            String type = getIndividualTicket.get("type").toString();
            String subject = getIndividualTicket.get("subject").toString();
            String priority = getIndividualTicket.get("priority").toString();
            String status = getIndividualTicket.get("status").toString();

            Ticket newTicket = new Ticket(id, created_at, updated_at, type, subject, priority, status);
            tickets.save(newTicket);
            index++;
        }

        Object nextPage = jsonObject.get("next_page");
        if (!(nextPage.equals(null))) {
            String nextPageURL = (String) nextPage;
            addAllTickets(nextPageURL);
        } else {
            ticketList = tickets.findAll();
            return;
        }
    }

    public static List<Ticket> getAllTickets() {
        return ticketList;
    }

    public static void printAllTickets(int count) {
        Scanner sc = new Scanner(System.in);
        int pageNo = count;
        int totalPageNo = (int) Math.ceil(ticketList.size() * 1.0 / maxTicketPerPage);

        if (ticketList.size() == 0) {
            System.out.println("Sorry, there are no tickets for you to view in this account.");
            return;
        }

        if (count != totalPageNo) {
            System.out.printf("Viewing Ticket #%d to Ticket #%d\n", ((count - 1) * maxTicketPerPage + 1),
                    (count * maxTicketPerPage));
        } else {
            System.out.printf("Viewing Ticket #%d to Ticket #%d\n", ((count - 1) * maxTicketPerPage + 1),
                    ticketList.size());
        }

        System.out.println("");
        System.out.printf("%-5s%-50s%-15s%-15s%-25s%-25s%s\n", "ID", "Subject", "Type", "Priority", "Date Created", "Date Updated",
                "Status");
        for (int i = (count - 1) * maxTicketPerPage; i < count * maxTicketPerPage; i++) {
            if (i < ticketList.size())
                System.out.print(ticketList.get(i).toString());
            else
                break;
        }

        System.out.println("");
        System.out.printf("You are viewing Page %d of %d\n", pageNo, totalPageNo);
        System.out.println("");

        System.out.println("Do you want to view other pages? (Y/N)");
        System.out.print("Enter your choice: ");
        String choice = sc.nextLine();
        System.out.println("");

        while (!choice.equals("Y") && !choice.equals("N")) {
            System.out.println("Invalid input. Please enter only Y or N.");
            System.out.println("Do you want to view other pages? (Y/N)");
            System.out.print("Enter your choice: ");
            choice = sc.nextLine();
        }

        if (choice.equals("N")) {
            return;
        } else if (choice.equals("Y")) {
            System.out.printf("Which page would you like to view? You can choose from Page 1 to Page %d\n",
                    totalPageNo);
            System.out.print("Enter your choice: ");
            int pageChoice = sc.nextInt();
            sc.nextLine();
            System.out.println("");
            while (pageChoice < 1 || pageChoice > totalPageNo) {
                System.out.printf("Invalid input. Please enter only between 1 and %d.\n", totalPageNo);
                System.out.print("Enter your choice: ");

                pageChoice = sc.nextInt();
                sc.nextLine();
                System.out.println("");
            }
            printAllTickets(pageChoice);
        }
    }

    public static Ticket getIndividualTicket(int id) {
        Ticket targetTicket = null;
        for (Ticket ticket : ticketList) {
            if (ticket.getId() == id) {
                targetTicket = ticket;
            }
        }
        return targetTicket;
    }

    public static void printIndividualTicket(Ticket ticket) {
        System.out.printf("Viewing Ticket #%d\n", ticket.getId());
        System.out.println("");
        System.out.printf("%-5s%-50s%-15s%-15s%-25s%-25s%s\n", "ID", "Subject", "Type", "Priority", "Date Created",
                "Date Updated", "Status");
        System.out.println(ticket.toString());
    }
}