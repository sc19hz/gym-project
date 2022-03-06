package gym;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{
    private HelloWorld helloWorld;

    public HelloController(HelloWorld helloWorld) {
        this.helloWorld = helloWorld;
    }

    @GetMapping("/helloworld")
    public String hello() {
        return helloWorld.hello();
    }

    @GetMapping("/anotherHelloworld")
    public String anotherHello() {
        return helloWorld.anotherHello();
    }
}