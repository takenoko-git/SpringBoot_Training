package com.example.sample1app;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.ModelAndView;

import com.example.sample1app.repositories.PersonRepository;
import com.samskivert.mustache.Mustache.Lambda;
import com.samskivert.mustache.Template.Fragment;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class HelloController {
    private boolean flag = false;

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonDAOPersonImpl dao;

    @Autowired
    Post post;

    @Autowired
    SampleComponent sampleComponent;

    @GetMapping("/")
    public ModelAndView index(
        @ModelAttribute("formModel") Person person,
        ModelAndView mav
    ) {
        mav.setViewName("index");
        mav.addObject("title", "Hello page");
        mav.addObject("msg", "this is JPA sample data.");
        List<Person> list = dao.getAll();
        mav.addObject("data", list);
        return mav;
    }

    @GetMapping("/find")
    public ModelAndView find(ModelAndView mav) {
        mav.setViewName("find");
        mav.addObject("msg", "Personのサンプルです。");
        List<Person> list = dao.getAll();
        mav.addObject("data", list);
        return mav;
    }

    @PostMapping("/find")
    public ModelAndView search(HttpServletRequest request,
        ModelAndView mav) {
        mav.setViewName("find");
        String param = request.getParameter("find_str");
        if (param == "") {
            mav = new ModelAndView("redirect:/find");
        } else {
            mav.addObject("title", "Find result");
            mav.addObject("msg", "「" + param + "」の検索結果");
            mav.addObject("value", param);
            List<Person> list = dao.find(param);
            mav.addObject("data", list);
        }
        return mav;
    }
    

    @PostMapping("/")
    @Transactional
    public ModelAndView form(
        @ModelAttribute("formModel") @Validated Person person,
        BindingResult result,
        ModelAndView mav) {
            ModelAndView res = null;
            System.out.println(result.getFieldErrors());
            if (!result.hasErrors()) {
                repository.saveAndFlush(person);
                res = new ModelAndView("redirect:/");
            } else {
                mav.setViewName("index");
                mav.addObject("title", "Hello page");
                mav.addObject("msg", "sorry, error is occurred...");
                Iterable<Person> list = repository.findAll();
                mav.addObject("dataList", list);
                res = mav;
            }
            return res;
    }

    @GetMapping("/page/{page}")
    public ModelAndView page(ModelAndView mav, @PathVariable int page) {
        mav.setViewName("find");
        mav.addObject("msg", "Personのサンプルです。");
        int num = 2;
        List<Person> list = dao.getPage(page, num);
        mav.addObject("data", list);
        return mav;
    }
    
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@ModelAttribute Person Person,
        @PathVariable int id, ModelAndView mav) {
        mav.setViewName("edit");
        mav.addObject("title", "edit Person");
        Optional<Person> data =  repository.findById((long)id);
        mav.addObject("formModel", data.get());   
        return mav;
    }

    @PostMapping("/edit")
    @Transactional
    public ModelAndView update(@ModelAttribute Person Person,
        ModelAndView  mav) {
        repository.saveAndFlush(Person);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@ModelAttribute Person Person,
        @PathVariable int id, ModelAndView mav) {
        mav.setViewName("delete");
        mav.addObject("title", "Delete Person.");
        mav.addObject("msg", "Can I delete this record?");
        Optional<Person> data = repository.findById((long)id);
        mav.addObject("formModel", data.get());
        return mav;
    }

    @PostMapping("/delete")
    @Transactional
    public ModelAndView remove(@RequestParam long id,
        ModelAndView mav) {
            repository.deleteById(id);
            return new ModelAndView("redirect:/");
        }

    @GetMapping("/other")
    public String other() {
        return "redirect:/";
    }

    @GetMapping("/home")
    public String home() {
        return "forward:/";
    }
    
    @PostConstruct
    public void init() {
        Person p1 = new Person();
        p1.setName("taro");
        p1.setMail("taro@yamada");
        p1.setAge(39);
        repository.saveAndFlush(p1);

        Person p2 = new Person();
        p2.setName("hanako");
        p2.setMail("hanako@flower");
        p2.setAge(28);
        repository.saveAndFlush(p2);

        Person p3 = new Person();
        p3.setName("sachiko");
        p3.setMail("sachiko@happy");
        p3.setAge(17);
        repository.saveAndFlush(p3);
    }

    @GetMapping("/bean")
    public ModelAndView bean(ModelAndView mav) {
        mav.setViewName("bean");
        mav.addObject("title", "Bean sample");
        mav.addObject("msg", sampleComponent.message());
        return mav;
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
