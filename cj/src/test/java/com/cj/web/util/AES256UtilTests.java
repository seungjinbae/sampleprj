package com.cj.web.util;

import org.apache.commons.codec.net.URLCodec;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AES256UtilTests {
	private static Logger logger = LogManager.getLogger(AES256UtilTests.class);
	
	@Test
	public void conteextLoads() {
		String key = "aes256-test-key!!";
		String oriStr = "배승진";
		String encStr = "";
		String decStr = "";
		try {
			AES256Util aes = new AES256Util(key);
			URLCodec codec = new URLCodec();
			
			encStr = codec.encode(aes.aesEncode(oriStr));
			decStr = aes.aesDecode(codec.decode(encStr));
			
			assertThat(oriStr, is(decStr));
			
			logger.info("oriStr :" +oriStr);
			logger.info("encStr :" +encStr);
			logger.info("decStr :" +decStr);
			
		}catch(Exception e) {
			
		}
	}
}
