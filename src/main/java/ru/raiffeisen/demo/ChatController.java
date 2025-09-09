package ru.raiffeisen.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.raiffeisen.demo.Model.Chat;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/")
    public String mainPage(ModelMap model){
        model.addAttribute("chats", chatService.getAllChats());
        return "chat";
    }

    @GetMapping("/chat/{chatid}")
    public String showChat(ModelMap model, @PathVariable long chatid){
        model.addAttribute("chats", chatService.getAllChats());
        model.addAttribute("chats", chatService.findChat());

        return "chat";
    }

    @PostMapping("/chat/new")
    public String newChat(ModelMap model, @RequestParam String title){
        Chat chat = chatService.createNewChat(title);
        return "redirect:/chat/"+chat.getid();
    }

    @PostMapping("chat/{chatid}/delete")
    public String deleteChat(@PathVariable long chatid){
        chatRepo.deleteById();
        //todo delete chat by id
        return "redirect:/";
    }

    @PostMapping("/chat/{chatid}/entry")
    public String talktoModel(@PathVariable Long chatid, @RequestParam String promt){
        chatService.proceedInteraction(chatid, promt);
        return "redirect:/chat"+chatid;
    }

}
