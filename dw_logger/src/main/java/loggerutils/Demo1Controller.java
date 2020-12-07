package loggerutils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class Demo1Controller {
    @ResponseBody
    @RequestMapping("testDemo")
    public String testDemo(){
        return "hello demo";
    }

}
