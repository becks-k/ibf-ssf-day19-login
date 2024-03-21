package vttp2023.ssf.day19.day19.services;

import java.util.Random;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vttp2023.ssf.day19.day19.models.User;
import vttp2023.ssf.day19.day19.models.UserSession;


@Service
public class LoginService {
    
    public boolean loginAuth(User user) {
        if (user.getPassword().equals("%s%s".formatted(user.getUsername(), user.getUsername()))) {
            return true;
        }

        return false;
    }

    public boolean captchaAuth(int a, int b, int value) {
        int answer = a + b;
        if (value == answer) {
            return true;
        }
        return false;
    }

    public int generateRandNum() {
        Random rand = new Random();
        return rand.nextInt(10);
    }

    public UserSession getSession(HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");

        if (userSession == null) {
            userSession = new UserSession();
            session.setAttribute("userSession", userSession);
        } 
        return userSession;
    }
}
