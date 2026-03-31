package com.iggacorp.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AI {

	/*
	 * ts is black magic to me lowk
	 * i have no clue how it works
	 * */
    private final HttpClient client;
    private final String styleExamples;
    private final String model;
    private final ObjectMapper mapper = new ObjectMapper();

    public AI(String filePath, String model) throws Exception {
        startOllama();
        this.client = HttpClient.newHttpClient();
        this.model = model;

        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }

        reader.close();

        styleExamples = builder.toString();
    }

    private void startOllama() {
        try {
            ProcessBuilder pb = new ProcessBuilder("ollama", "serve");
            pb.start();
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Ollama may already be running.");
        }
    }

    public String chat(String userMessage) {
        try {
            String prompt = """
                    You are texting exactly like this person.
                    Your name is Iggagi.
                    You cannot swear, including in abbreviations like wth and lmao.
                    ONLY SAY THE RESPONSE, NOTHING TO HINT YOU ARE AI!
                    
                    Interactions they've had:
                    %s

                    Reply the way they would.

                    User: %s
                    """.formatted(styleExamples, userMessage);

            String safePrompt = prompt
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n");

            String json = """
                    {
                    "model": "%s",
                    "prompt": "%s",
                    "stream": false
                    }
                    """.formatted(model, safePrompt);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11434/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode node = mapper.readTree(response.body());
            String responseText = node.get("response").asText();

            return responseText;
        } catch (Exception e) {
            return "Error, contact delta asap\n" + e.getCause();
        }
    }
}
