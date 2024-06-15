package com.shivam.customAnnotations.Users;

import com.shivam.customAnnotations.SqlQueryToCustomClassMapper;
import org.springframework.stereotype.Service;

@Service
public class Services {

    @SqlQueryToCustomClassMapper(query = "SELECT USER_NAME, USER_EMAIL FROM USERS_MASTER", mapperClass = UserDao.class)
    public <T> T myMethod(int id) {
        // Method implementation
        return null;
    }
}
