package com.example.sample1app;

import java.io.IOException;
import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.samskivert.mustache.Mustache.Lambda;
import com.samskivert.mustache.Template.Fragment;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelloController {
    private boolean flag = false;

    @GetMapping("/")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("index");
        mav.addObject("title", "ラムダ式のサンプル");
        mav.addObject("msg", "これはラムダ式を利用してメッセージを表示したものです。");

        Lambda fn = new Lambda() {
            public void execute (Fragment frag, Writer out)
                throws IOException {
                    out.write("<div class=\"alert alert-primary\">");
                    frag.execute(out);
                    out.write("</div>");
                }
        };
        mav.addObject("fn", fn);
        return mav;
    }

    @GetMapping("/other")
    public String other() {
        return "redirect:/";
    }

    @GetMapping("/home")
    public String home() {
        return "forward:/";
    }
    
}

class MyData {
    public String name;
    public int age;

    public MyData(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return String.format("Name:  %s, age: %s", name, age);
    }
}
