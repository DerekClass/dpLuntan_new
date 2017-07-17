package com.Derek.dpLuntan;

import com.Derek.dpLuntan.dao.QuestionDAO;
import com.Derek.dpLuntan.dao.UserDAO;
import com.Derek.dpLuntan.model.EntityType;
import com.Derek.dpLuntan.model.Question;
import com.Derek.dpLuntan.model.User;
import com.Derek.dpLuntan.service.FollowService;
import com.Derek.dpLuntan.util.JedisAdapter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DpLuntanApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;
    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    FollowService followService;

    @Autowired
    JedisAdapter jedisAdapter;

    @Test
    public void contextLoads() {
        Random random = new Random();
       // jedisAdapter.getJedis().flushDB();
        for (int i = 0; i < 11; i++) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            //互相关注
            for (int j = 0; j <i ; j++) {
                followService.follow(j, EntityType.ENTITY_USER,i);
            }
            user.setPassword("xx");
            userDAO.updatePassword(user);

            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * i);
            question.setCreatedDate(date);
            question.setUserId(i + 1);
            question.setTitle(String.format("TITLE{%d}", i));
            question.setContent(String.format("hello dupeng Content %d", i));
            questionDAO.addQuestion(question);
        }
/*        Assert.assertEquals("xx", userDAO.selectById(1).getPassword());
          userDAO.deleteById(1);
          Assert.assertNull(userDAO.selectById(1));

          List<Question> list = questionDAO.selectLatestQuestions(0,0,10);
          for (int i = 0; i <list.size() ; i++) {
            System.out.println(list.get(i));
        }*/


    }
}
