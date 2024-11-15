package com.linqiumeng.mediavault.controller;

import com.linqiumeng.mediavault.exception.CustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping("/divide")
    public String divide(@RequestParam int a, @RequestParam int b) {
        try {
            int result = a / b;
            return "结果: " + result;
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("除数不能为零");
        }
    }

    @GetMapping("/check-age")
    public String checkAge(@RequestParam int age) {
        if (age < 18) {
            throw new IllegalArgumentException("年龄必须大于或等于18岁");
        }
        return "年龄符合要求";
    }

    @GetMapping("/validate-input")
    public String validateInput(@RequestParam int value) {
        if (value < 0) {
            throw new CustomException("输入值不能为负数");
        }
        return "输入值有效";
    }
}
