package sec.project.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String loadMessageboard(Model model, HttpServletRequest request, HttpServletResponse response) {
        // A completely stupid thing to do just to prove a point about the
        // importance of isHttpOnly for session cookies.
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("JSESSIONID")) {
                    cookie.setHttpOnly(false);
                    response.addCookie(cookie);
                }
            }
        }
        model.addAttribute("comments", messageRepository.findAll());
        return "messageboard";
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String submitForm(@RequestParam(value="name",required=true) String name, @RequestParam(value="msg",required=true) String msg) {
        messageRepository.save(new Message(name, msg));
        return "redirect:/";
    }
}
