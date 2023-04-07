import com.ACGN.dao.UserMapper;
import com.ACGN.entity.User;
import com.ACGN.util.MailClient;
import com.DemoApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = DemoApplication.class)

public class MailTest {
    @Autowired
    public MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private UserMapper userMapper;


    @Test
    public void testTextMail(){
        mailClient.sendMail("******@******.edu.cn","TEST","Welcome.");
    }
    @Test
    public void testselect(){
        mailClient.sendMail("******@******.edu.cn","TEST","Welcome.");
    }

        @Test
        public void testSelect() {
            System.out.println(("----- selectAll method test ------"));
            //参数是一个Wrapper，条件结构器，这里先不用 填null
            //查询所有的用户
            List<User> userList = userMapper.selectList(null);
            userList.forEach(System.out::println);

        }

    }


