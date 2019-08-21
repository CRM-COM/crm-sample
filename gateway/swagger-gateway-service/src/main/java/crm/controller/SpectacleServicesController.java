package crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/sourcey/spectacle")
public class SpectacleServicesController {

    @RequestMapping(method = RequestMethod.GET, path = "/commerce")
    public String commerce() {
        return "commerce.html";
    }
}
