package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Message;
import sec.project.repository.MessageRepository;

@Controller
public class MessageController {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/";
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadMessageboard(Model model) {
        model.addAttribute("comments", messageRepository.findAll());
        return "messageboard";
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String msg) {
        messageRepository.save(new Message(name, msg));
        return "redirect:/";
    }
}
