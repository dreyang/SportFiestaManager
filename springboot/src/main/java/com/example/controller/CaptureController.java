package com.example.controller;
import com.example.common.CaptureConfig;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping
public class CaptureController {
    //通过captcha这个接口生成验证码
    @RequestMapping("/captcha")
    public void captcha(@RequestParam String key,HttpServletRequest request, HttpServletResponse response) throws Exception {

       SpecCaptcha captcha = new SpecCaptcha(135, 33, 5);
        captcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);
        CaptureConfig.CAPTURE_MAP.put(key, captcha.text().toLowerCase());
        CaptchaUtil.out(captcha, request, response);

                        //算数类型
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(135, 33);
//        captcha.setLen(4);  // 几位数运算，默认是两位
//        captcha.getArithmeticString();  // 获取运算的公式：3+2=?
//        captcha.text();  // 获取运算的结果：5
//        CaptureConfig.CAPTURE_MAP.put(key, captcha.text().toLowerCase());
//        CaptchaUtil.out(captcha, request, response);
    }
}
