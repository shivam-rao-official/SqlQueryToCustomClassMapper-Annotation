package com.shivam.customAnnotations.Users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Repository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


//    @MyCustomQuery(query = "select e from users_master e")
    public Map<String, Object> getUserData() {

//        List<Map<String, Object>> queried =
//                jdbcTemplate.queryForList("select * from users_master");
//
//        if(queried.isEmpty()) return new HashMap<>();
//        else  {
//            HashMap<String, Object> result = new HashMap<>();
//            queried.forEach(user -> {
//                result.put("USER_NAME", user.get("USER_NAME"));
//                result.put("USER_EMAIL", user.get("USER_EMAIL"));
//            });
//
//            return result;
//        }

        return null;

    }
}
