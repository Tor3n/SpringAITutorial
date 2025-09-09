package ru.raiffeisen.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RaiffeinsenSpringAiApplication {

	@Autowired
	private ChatRepo chatRepo;

	@Bean
	public ChatClient chatClient(ChatClient.Builder builder){
		return builder.defaultAdvisors().defaultOptions(
				OllamaOptions.builder().topP(0.7).topK(20).repeatPenalty(1.1).temperature(0.3).build())
				.defaultAdvisors(
						SimpleLoggerAdvisor.builder().order(1).build(),
						getHistoryAdviser(2),
						SimpleLoggerAdvisor.builder().order(3).build()
						)
				.build();
	}


	/*
		private MessageChatMemoryAdvisor getHistoryAdviser(){
		return MessageChatMemoryAdvisor.builder(
				PostgresChatMemoryRepository.builder()
						.chatMemoryRepository(chatRepo)
						.maxMessages(4)
						.build()
		).build();
	}

		private MessageChatMemoryAdvisor getHistoryAdviser(){
		return MessageChatMemoryAdvisor.builder(
				MessageWindowChatMemory.builder()
						.chatMemoryRepository(chatRepo)
						.maxMessages(4)
						.build()
		).build();
	}

	 */

	private MessageChatMemoryAdvisor getHistoryAdviser(int order){
		return PostgresChatMemory.builder(
				MessageWindowChatMemory.builder()
						.chatMemoryRepository(chatRepo)
						.maxMessages(4)
						.build()
		).order(order).build();
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RaiffeinsenSpringAiApplication.class, args);
		ChatClient chatClient = context.getBean(ChatClient.class);
		//String answer = chatClient.prompt("could you give me text of bohemian rapsody?").call().content();
		//System.out.println(answer);
	}

}


