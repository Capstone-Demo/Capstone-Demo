package com.recoder.capstone_admin.domain.service.user;

import com.recoder.capstone_admin.domain.model.User;
import com.recoder.capstone_admin.domain.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUser(String id) {
        User user;
        try {
            user = userRepository.findUserById(id);
        }catch(Exception e) {
            user = new User();
        }
        return user;
    }

    public User findUser(String id, String pw) {

        User user;
        try {
            user = userRepository.findUserByIdAndPw(id, pw);
        }catch(Exception e) {
            user = new User();
        }
        return user;
    }
}
