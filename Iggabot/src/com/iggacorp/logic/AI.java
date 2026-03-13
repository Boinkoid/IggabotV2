package com.iggacorp.logic;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AI {

    private final HttpClient client;
    private final String styleExamples;
    private final String model;

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
            pb.redirectErrorStream(true);
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

Examples of how they text:
%s

Reply the way they would.

User: %s
""".formatted(styleExamples, userMessage);

            String json = """
{
  "model": "%s",
  "prompt": "%s",
  "stream": false
}
""".formatted(
                    model,
                    prompt.replace("\"", "\\\"").replace("\n", "\\n")
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11434/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();

            int start = body.indexOf("\"response\":\"") + 12;
            int end = body.indexOf("\"", start);

            return body.substring(start, end)
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"");

        } catch (Exception e) {
            return "AI Error: " + e.getMessage();
        }
    }
}