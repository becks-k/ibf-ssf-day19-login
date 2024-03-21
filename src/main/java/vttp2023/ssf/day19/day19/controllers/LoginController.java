package vttp2023.ssf.day19.day19.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import vttp2023.ssf.day19.day19.models.User;
import vttp2023.ssf.day19.day19.models.UserSession;
import vttp2023.ssf.day19.day19.services.LoginService;

@Controller
@RequestMapping
public class LoginController {

    @Autowired
    LoginService loginService;
    

    @PostMapping
    public ModelAndView createView(HttpSession session, @ModelAttribute User user, @RequestParam(defaultValue = "-1") String value) {
        
        ModelAndView mav = new ModelAndView();

        UserSession userSession = loginService.getSession(session);
        // New login
        if (userSession.getLoginCount() == 2) {
            userSession.setUser(user);
        }

        // Authenticate username and password
        boolean loginSuccess = loginService.loginAuth(user);

        // Captcha present
        if (userSession.hasCaptcha) {
            // Fail captcha
            System.out.println(">>>>>>>>>>>>>> a, b, value: " + userSession.getA() + userSession.getB() + value);
            boolean successCaptcha = loginService.captchaAuth(userSession.getA(), userSession.getB(), Integer.parseInt(value));
            if (!successCaptcha) {
                mav.setViewName("redirect:/suspended.html");
                session.invalidate();

                System.out.println(">>>>>>>>>>>>>> Condition 1");

                return mav;
            }
        }


        //Login fail
        if (!loginSuccess) {
            userSession.getUser().setUsername(user.getUsername());
            userSession.decrementLoginCount();
            if (userSession.getLoginCount() > 0) {
                userSession.setHasCaptcha(true);
                mav.setViewName("index");
                int a = loginService.generateRandNum();
                int b = loginService.generateRandNum();
                mav.addObject("hasCaptcha", userSession.hasCaptcha);
                mav.addObject("loginCount", userSession.getLoginCount());
                mav.addObject("a", a);
                mav.addObject("b", b);
                mav.setStatus(HttpStatusCode.valueOf(401));
                userSession.setA(a);
                userSession.setB(b);
                session.setAttribute("userSession", userSession);

                System.out.println(">>>>>>>>>>>>>> Condition 2");
            } else {
                mav.setViewName("redirect:/suspended.html");
                session.invalidate();

                System.out.println(">>>>>>>>>>>>>> Condition 3");
            }
        } else {
            mav.setViewName("redirect:/success.html");
        }

        return mav;
    }

    @PostMapping(path="/logout")
    public ModelAndView postLogout(HttpSession session) {
        ModelAndView mav = new ModelAndView("redirect:/"); 
        session.invalidate();
        return mav;
    }

    @GetMapping(path="/")
    public ModelAndView getLogin(HttpSession session) {
        
        UserSession userSession = loginService.getSession(session);
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("hasCaptcha", userSession.hasCaptcha);
        mav.addObject("loginCount", userSession.getLoginCount());
        mav.setStatus(HttpStatusCode.valueOf(200));
        
        System.out.println(">>>>>>>>>>>>>>loginCount:" + userSession.getLoginCount());
        return mav;
    }
    
    


}
