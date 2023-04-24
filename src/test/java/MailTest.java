import com.ACGN.Service.DiscussPostService;
import com.ACGN.dao.UserMapper;
import com.ACGN.entity.DiscussPost;
import com.ACGN.entity.User;
import com.DemoApplication;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = DemoApplication.class)

public class MailTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscussPostService discussPostService;

    @Test
    public void testTextMail(){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
        Page<DiscussPost> classifiedPage;
        /**
         * 置顶帖的数量
         */
        int sum=0;
        QueryWrapper queryWrapperOne=new QueryWrapper();
        queryWrapperOne.eq("type",1);
        queryWrapperOne.eq("status",1);
        queryWrapperOne.orderByDesc("create_time");
        HashMap map=new HashMap();
        List<DiscussPost>list=discussPostService.list(queryWrapperOne);
        sum=list.size();
        QueryWrapper queryWrapperTwo=new QueryWrapper();
        queryWrapperTwo.eq("type",1);
        queryWrapperTwo.eq("status",1);
        queryWrapperTwo.orderByDesc("status");
        Page<DiscussPost> pageTwo=new Page<>();
        pageTwo.setCurrent(1);
        pageTwo.setSize(10-sum);
        classifiedPage=discussPostService.page(pageTwo,queryWrapperTwo);
        map.put("normal",classifiedPage);
        map.put("top",list);
        System.out.println(map.toString());
        System.out.println(classifiedPage.toString());
//        mailClient.sendMail("******@******.edu.cn","TEST","Welcome.");
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


