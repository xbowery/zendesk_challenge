package com.java.zendesk.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.java.zendesk.model.Ticket;

import org.json.JSONArray;
import org.json.JSONObject;

public class TicketService {
    private static final String authPassword = "{email_address}:{password}";

    // private static final String authToken = "{email_address}/token:{api_token}";

    private static final String encodedAuthPassword = Base64.getEncoder().encodeToString(authPassword.getBytes());

    // private static final String encodedAuthToken =
    // Base64.getEncoder().encodeToString(authToken.getBytes());

    private static final int maxTicketPerPage = 25;

    private static int totalTicketCount;

    private static List<Ticket> ticketList = new ArrayList<>();

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
            ticketList.add(newTicket);
            index++;
        }

        Object nextPage = jsonObject.get("next_page");
        if (!(nextPage.equals(null))) {
            String nextPageURL = (String) nextPage;
            addAllTickets(nextPageURL);
        } else {
            return;
        }
    }

    public static List<Ticket> getAllTickets() {
        return ticketList;
    }

    public static void printAllTickets() {
        System.out.printf("%-5s%-50s%-15s%-15s%-15s%s\n", "ID", "Subject", "Type", "Priority", "Date Created",
                "Status");
        for (Ticket ticket : ticketList) {
            System.out.printf("%-5d%-50s%-15s%-15s%-15s%s\n", ticket.getId(), ticket.getSubject(), ticket.getType(),
                    ticket.getPriority(), ticket.getCreatedDate(), ticket.getStatus());
        }
    }
}