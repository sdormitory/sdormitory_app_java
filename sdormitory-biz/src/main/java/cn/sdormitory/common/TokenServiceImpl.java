package cn.sdormitory.common;

import cn.sdormitory.sys.entity.SysUser;
import com.alibaba.fastjson.JSON;
import cz.mallat.uasparser.UserAgentInfo;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @创建人：zhouyang
 * @创建时间：2020/9/12 12:25
 * @version：V1.0
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {
    private Logger logger = Logger.getLogger(TokenServiceImpl.class);
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    /**
     * 调用RedisAPI
     */

    private int expire = SESSION_TIMEOUT;// 2h
    private String tokenPrefix = "token:";//统一加入 token前缀标识

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    /***
     * @param agent Http头中的user-agent信息
     * @return Token格式<br/>
     * 	PC：“前缀PC-USERCODE-USERID-CREATIONDATE-RONDEM[6位]”
     *  <br/>
     *  Android：“前缀ANDROID-USERCODE-USERID-CREATIONDATE-RONDEM[6位]”
     */
    @Override
    public String generateToken(String agent, SysUser user) {
        // TODO Auto-generated method stub
        try {
            UserAgentInfo userAgentInfo =
                    UserAgentUtil.getUasParser().parse(
                            agent);
            StringBuilder sb = new StringBuilder();
            sb.append(tokenPrefix);//统一前缀
            if (userAgentInfo.getDeviceType().equals(UserAgentInfo.UNKNOWN)) {
                if (UserAgentUtil.CheckAgent(agent)) {
                    sb.append("MOBILE-");
                } else {
                    sb.append("PC-");
                }
            } else if (userAgentInfo.getDeviceType()
                    .equals("Personal computer")) {
                sb.append("PC-");
            } else
                sb.append("MOBILE-");
//			sb.append(user.getUserCode() + "-");
            sb.append(MD5.getMd5(user.getUsername(),32) + "-");//加密用户名称
            sb.append(user.getId() + "-");
            sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                    + "-");
            sb.append(MD5.getMd5(agent, 6));// 识别客户端的简化实现——6位MD5码

            return sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(String token, SysUser user) {
        ValueOperations<String,Object> redis = redisTemplate.opsForValue();
        if (token.startsWith(tokenPrefix+"PC-"))
            redis.set(token, user.getUsername(),expire, TimeUnit.SECONDS);
        else
            redis.set(token, user.getUsername());// 手机认证信息永不失效
    }


    @Override
    public String load(String token) {
        ValueOperations<String,Object> redis = redisTemplate.opsForValue();
        return redis.get(token).toString();
    }



    @Override
    public void delete(String token) {
        if (redisTemplate.hasKey(token))
            redisTemplate.delete(token);
    }

    private boolean exists(String token) {

        return redisTemplate.hasKey(token);
    }

    @Override
    public String replaceToken(String agent, String token)
            throws Exception {
        ValueOperations<String,Object> redis = redisTemplate.opsForValue();
        // 验证旧token是否有效
        if (!exists(token)) {// token不存在
            throw new Exception("未知的token或 token已过期");// 终止置换
        }
        Date TokenGenTime;// token生成时间
        try {
            String[] tokenDetails = token.split("-");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            TokenGenTime = formatter.parse(tokenDetails[3]);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw new Exception("token格式错误:" + token);
        }

        long passed = Calendar.getInstance().getTimeInMillis()
                - TokenGenTime.getTime();// token已产生时间
        if (passed < REPLACEMENT_PROTECTION_TIMEOUT * 1000) {// 置换保护期内
            throw new Exception("token处于置换保护期内，剩余"
                    + (REPLACEMENT_PROTECTION_TIMEOUT * 1000 - passed) / 1000
                    + "(s),禁止置换");
        }
        // 置换token
        String newToken = "";
        SysUser user = new SysUser();
        user.setUsername(this.load(token));

        long ttl = redisTemplate.getExpire(token);// token有效期（剩余秒数 ）
        if (ttl > 0 || ttl == -1) {// 兼容手机与PC端的token在有效期
            newToken = this.generateToken(agent, user);
            this.save(newToken, user);// 缓存新token
            redis.set(token, JSON.toJSONString(user),2);// 2分钟后旧token过期，注意手机端由永久有效变为2分钟（REPLACEMENT_DELAY默认值）后失效
        } else {// 其它未考虑情况，不予置换
            throw new Exception("当前token的过期时间异常,禁止置换");
        }
        return newToken;
    }

    @Override
    public boolean validate(String agent, String token) {
        if (!exists(token)) {// token不存在
            return false;
        }
        try {
            Date TokenGenTime;// token生成时间
            String agentMD5;
            String[] tokenDetails = token.split("-");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            TokenGenTime = formatter.parse(tokenDetails[3]);
            long passed = Calendar.getInstance().getTimeInMillis()
                    - TokenGenTime.getTime();
            if(passed>this.SESSION_TIMEOUT*1000)
                return false;
            agentMD5 = tokenDetails[4];
            logger.error("0----------"+agentMD5);
            if(MD5.getMd5(agent, 6).equals(agentMD5))
                return true;
        } catch (ParseException e) {
            return false;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }
}
