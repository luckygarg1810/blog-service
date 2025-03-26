package com.luckygarg.blog_service.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SummarizeService {

	@Value("${gemini.api.key}")
	private String geminiApiKey;

	@Value("${gemini.api.url}")
	private String geminiApiUrl;

	private final WebClient webClient;

	public SummarizeService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> generateSummary(String text) {
		Map<String, Object> requestBody = Map.of("contents", new Object[] {
				Map.of("parts", new Object[] { Map.of("text", "Summarize the following text:\n" + text) }) });

		Map<String, Object> response = webClient.post().uri(geminiApiUrl + geminiApiKey)
				.header("Content-Type", "application/json").bodyValue(requestBody).retrieve().bodyToMono(Map.class)
				.block();

		if (response != null && response.containsKey("candidates")) {
			List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
			if (!candidates.isEmpty() && candidates.get(0).containsKey("content")) {
				Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
				if (content.containsKey("parts")) {
					List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");
					if (!parts.isEmpty() && parts.get(0).containsKey("text")) {
						return Map.of("summary", parts.get(0).get("text"));
					}
				}
			}
		}

		return Map.of("summary", "Could not generate summary.");
	}
}
