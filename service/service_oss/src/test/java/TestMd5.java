import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class TestMd5 {

    @Test
    public void EncodeByMd5() {
        long l = System.currentTimeMillis();
        System.out.println(l);
        String password = "xdfj33" + l;
        System.out.println(password);
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(md5Password);
    }

    @Test
    public void testUUID() {
        for (int i = 0; i < 5; i++) {
            String s = UUID.randomUUID().toString();
            System.out.println(s);
        }

        String date = new DateTime().toString("YYYY/MM/");
        System.out.println(date);
    }

}
