package ru.raiffeisen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.raiffeisen.model.Chat;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;


    @GetMapping("/")
    public String mainPage(ModelMap model) {
        model.addAttribute("chats",chatService.getAllChats());
        return "chat";
    }

    @GetMapping("/chat/{chatId}")
    public String showChat(ModelMap model, @PathVariable long chatId) {
        model.addAttribute("chats", chatService.getAllChats());
        model.addAttribute("chat", chatService.findChat(chatId));
        return "chat";

    }

    @PostMapping("/chat/new")
    public String newChat(ModelMap model, @RequestParam String title) {
        Chat chat = chatService.createNewChat(title);
        return "redirect:/chat/"+chat.getId();
    }

    @PostMapping("chat/{chatId}/delete")
    public String deleteChat(@PathVariable long chatId) {
        chatService.delete(chatId);
        return "redirect:/";
    }


    @GetMapping("/chat/{chatId}/entry")
    public String talkToModel(@PathVariable long chatId, @RequestParam String userPrompt) {
        chatService.proceedInteraction(chatId, userPrompt);
        return "redirect:/chat/"+chatId;

    }
}
