package cn.sdormitory.basedata.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.sdormitory.basedata.dao.BStudentDao;
import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.basedata.service.BStudentService;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.common.utils.PropertiesUtils;
import cn.sdormitory.request.HttpRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @创建人：zhouyang
 * @创建时间：2020/11/10 16:30
 * @version：V1.0
 */
@Slf4j
@Service("bStudentService")
public class BStudentServiceImpl extends ServiceImpl<BStudentDao, BStudent> implements BStudentService{

    @Override
    public IPage<BStudent> getPage(Map<String, Object> params) {
        int pageSize = Integer.parseInt(String.valueOf(params.get("pageSize")));
        int pageNum = Integer.parseInt(String.valueOf(params.get("pageNum")));

        String studentName = (String) params.get("studentName");
        String studentNo = (String) params.get("studentNo");
        String className = (String) params.get("className");
        String buildingNo = (String) params.get("buildingNo");
        String storey = (String) params.get("storey");
        String dormitoryNo = (String) params.get("dormitoryNo");
        String status = (String) params.get("status");

        LambdaQueryWrapper<BStudent> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(studentName)) {
            wrapper.eq(BStudent::getStudentName, studentName);
        }
        if (StrUtil.isNotEmpty(studentNo)) {
            wrapper.eq(BStudent::getStudentNo, studentNo);
        }
        if (StrUtil.isNotEmpty(className)) {
            wrapper.eq(BStudent::getClassName, className);
        }
        if (StrUtil.isNotEmpty(buildingNo)) {
            wrapper.eq(BStudent::getBuildingNo, buildingNo);
        }
        if (StrUtil.isNotEmpty(storey)) {
            wrapper.eq(BStudent::getStorey, storey);
        }
        if (StrUtil.isNotEmpty(dormitoryNo)) {
            wrapper.eq(BStudent::getDormitoryNo, dormitoryNo);
        }
        if (StrUtil.isNotEmpty(status)) {
            wrapper.eq(BStudent::getStatus, status);
        }

        wrapper.apply(params.get(CommonConstant.SQL_FILTER) != null, (String) params.get(CommonConstant.SQL_FILTER));
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public BStudent getBStudentById(Long id) {
        return getById(id);
    }

    @Override
    public int create(BStudent bStudent) {
        int count=0;
        count = this.baseMapper.insert(bStudent);
        if(count>0){
            try {
                this.setPerson(bStudent);
            }catch (Exception e){
                e.printStackTrace();
                count=0;
            }
        }
        return count;
    }

    @Override
    public int delete(Long id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int update(Long id, BStudent bStudent) {
        bStudent.setId(id);
        return this.baseMapper.updateById(bStudent);
    }

    @Override
    public int deleteByIds(String[] ids) {
        String[] studentNos=new String[ids.length];
        for(int i=0;i<ids.length;i++){
            BStudent bStudent=this.getBStudentById(Long.parseLong(ids[i]));
            String studentNo=bStudent.getStudentNo();
            studentNos[i]=studentNo;
        }
        this.removePerson(studentNos);
        return this.baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public int updateStatus(Long id, String status) {
        BStudent bStudent = new BStudent();
        bStudent.setId(id);
        bStudent.setStatus(status);
        return this.baseMapper.updateById(bStudent);
    }

    @Override
    public BStudent getByStudentNo(String studentNo) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<BStudent>().eq(BStudent::getStudentNo, studentNo));
    }

    @Override
    public String setPerson(BStudent bStudent) {
        String key = PropertiesUtils.get("device.properties", "sdormitory.device1.key");
        String ip = PropertiesUtils.get("device.properties","sdormitory.device1.ip");
        List pairs = new ArrayList();
        String base64;
        base64 = Base64.encodeBase64String(bStudent.getPhoto());
        pairs.add(new BasicNameValuePair("key",key));
        pairs.add(new BasicNameValuePair("id",bStudent.getStudentNo()));
        pairs.add(new BasicNameValuePair("name",bStudent.getStudentName()));
        pairs.add(new BasicNameValuePair("IC_NO",bStudent.getStudentNo()));
        pairs.add(new BasicNameValuePair("ID_NO",bStudent.getIdcard()));
        pairs.add(new BasicNameValuePair("photo",base64));
        pairs.add(new BasicNameValuePair("passCount","10000"));
        pairs.add(new BasicNameValuePair("startTs","-1"));
        pairs.add(new BasicNameValuePair("endTs","-1"));
        pairs.add(new BasicNameValuePair("visitor","false"));
        String object = HttpRequest.sendPost(ip+"/setPerson",pairs);
        return object;
    }

    @Override
    public String getPerson( String id) {
        String key = PropertiesUtils.get("device.properties", "sdormitory.device1.key");
        String ip = PropertiesUtils.get("device.properties","sdormitory.device1.ip");
        String object = HttpRequest.sendGet(ip+"/getPerson?key="+key+"&id="+id,null);
        return object;
    }

    @Override
    public String listPersonByNumber(int number, int offset) {
        String key = PropertiesUtils.get("device.properties", "sdormitory.device1.key");
        String ip = PropertiesUtils.get("device.properties","sdormitory.device1.ip");
        String object = HttpRequest.sendGet(ip+"/listPersonByNumber?key="+key+"&number="+number+"&offset="+offset,null);
        return object;
    }

    @Override
    public String removePerson(String [] studentNos) {
        StringBuffer sbstr=new StringBuffer("[");
        for (String str:studentNos){
            sbstr.append(str+",");
        }
        String astr=sbstr.toString();
        if(astr.length()>1){
            astr=astr.substring(0,astr.length()-1);
        }
        astr+="]";
        String key = PropertiesUtils.get("device.properties", "sdormitory.device1.key");
        String ip = PropertiesUtils.get("device.properties","sdormitory.device1.ip");
        //修复pairs直接传空报空指针问题
        List pairs = new ArrayList();
        pairs.add(new BasicNameValuePair("key",key));
        pairs.add(new BasicNameValuePair("id",astr));
        String object = HttpRequest.sendPost(ip+"/removePerson",pairs);
        return object;
    }

    @Override
    public JSONObject listRecordByNumber(Integer number, Integer offset, Integer dbtype) {
        String key = PropertiesUtils.get("device.properties", "sdormitory.device1.key");
        String ip = PropertiesUtils.get("device.properties","sdormitory.device1.ip");
        HttpClient client= new DefaultHttpClient();
        HttpPost request = new HttpPost(ip+"/listRecordByNumber?key="+key+"&dbtype="+dbtype+"&number="+number+"&offset="+offset);

        JSONObject object = null;
        try {
            HttpResponse resp = client.execute(request);
            HttpEntity entity = resp.getEntity();
            if(entity!=null){
                String result = EntityUtils.toString(entity,"UTF-8");//解析返回数据
                object = JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public JSON removeRecord(double ts) {
        String key = PropertiesUtils.get("device.properties", "sdormitory.device1.key");
        String ip = PropertiesUtils.get("device.properties","sdormitory.device1.ip");
        HttpClient client= new DefaultHttpClient();
        HttpPost request = new HttpPost(ip+"/removeRecord?key="+key+"&ts="+ts);

        JSONObject object = null;
        try {
            HttpResponse resp = client.execute(request);
            HttpEntity entity = resp.getEntity();
            if(entity!=null){
                String result = EntityUtils.toString(entity,"UTF-8");//解析返回数据
                object = JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }


    @Override
    public String removePersonById(String id) {
        String key = PropertiesUtils.get("device.properties", "sdormitory.device1.key");
        String ip = PropertiesUtils.get("device.properties","sdormitory.device1.ip");
        //修复pairs直接传空报空指针问题
        List pairs = new ArrayList();
        pairs.add(new BasicNameValuePair("key",key));
        pairs.add(new BasicNameValuePair("id",id));
        String object = HttpRequest.sendPost(ip+"/removePerson",pairs);
        return object;
    }


    @Override
    public BStudent getByStuNoAndNameAndClassId(String studentNo, String studentName, Long classId) {
        LambdaQueryWrapper<BStudent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BStudent::getStudentNo, studentNo);
        if(!"".equals(studentName) && studentName!=null){
            wrapper.eq(BStudent::getStudentName, studentName);
        }
        if(!"".equals(classId) && classId!=null){
            wrapper.eq(BStudent::getClassId, classId);
        }
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public BStudent getByStuNoAndNameAndClassName(String studentNo, String studentName, String className) {
        LambdaQueryWrapper<BStudent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BStudent::getStudentNo, studentNo);
        wrapper.eq(BStudent::getStudentName, studentName);
        wrapper.eq(BStudent::getClassName, className);
        return this.baseMapper.selectOne(wrapper);
    }
}
