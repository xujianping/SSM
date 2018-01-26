import com.xujp.dao.ScoreDao;
import com.xujp.domain.Score;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Xujp on 2018/1/26.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 使用Springtest测试框架
@ContextConfiguration("/spring/spring-*.xml") // 加载配置
public class MyBatisTest {
    @Autowired
    private ScoreDao scoreDao;

    @Test
    public void testAddScore() {
        Score score = new Score();
        score.setChangeType("充钱钱");
        score.setScore(10);
        int insert = scoreDao.insertScore(score);
        System.out.print("insert :"+insert);
    }

}
