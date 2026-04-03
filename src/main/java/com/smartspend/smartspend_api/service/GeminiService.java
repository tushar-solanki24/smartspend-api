package com.smartspend.smartspend_api.service;

import com.smartspend.smartspend_api.entity.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    /**
     * Analyzes a list of expenses using the Gemini AI API and returns budget
     * advice.
     *
     * @param expenses List of user expenses
     * @return AI-generated financial advice
     */
    public String analyzeExpenses(List<Expense> expenses) {
        if (expenses == null || expenses.isEmpty()) {
            return "No expenses found to analyze. Start tracking your spending to get AI-powered advice!";
        }

        String expenseSummary = expenses.stream()
                .map(e -> String.format("- %s: ₹%.2f in category %s on %s",
                        e.getDescription(), e.getAmount(), e.getCategory(), e.getDate()))
                .collect(Collectors.joining("\n"));

        String prompt = "As a financial advisor, analyze the following expenses in Indian Rupees (₹) " +
                "and provide 3-4 concise, actionable tips to improve my budget and saving habits:\n\n"
                + expenseSummary;
        // Construct the request body for Gemini API
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)))));

        try {
            Map<?, ?> response = restTemplate.postForObject(
                    apiUrl + "?key=" + apiKey, requestBody, Map.class);

            if (response != null) {
                List<?> candidates = (List<?>) response.get("candidates");
                Map<?, ?> firstCandidate = (Map<?, ?>) candidates.get(0);
                Map<?, ?> content = (Map<?, ?>) firstCandidate.get("content");
                List<?> parts = (List<?>) content.get("parts");
                Map<?, ?> firstPart = (Map<?, ?>) parts.get(0);
                return (String) firstPart.get("text");
            }
            return "Error parsing AI response. Please try again later.";
        } catch (Exception e) {
            return "Failed to connect to AI service: " + e.getMessage();
        }
    }
}
