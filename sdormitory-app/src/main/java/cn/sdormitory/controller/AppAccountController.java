package cn.sdormitory.controller;

import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.common.annotation.IgnoreAuth;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.common.dto.ResultMsg;
import cn.sdormitory.service.AppStudentService;
import cn.sdormitory.service.AppUserService;
import cn.sdormitory.sys.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/28 11:02
 * @version：V1.0
 */
@RestController
@RequestMapping("/app/Account")
@Api("APP端个人中心接口")
public class AppAccountController {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppStudentService appStudentService;

    @ApiOperation(value = "个人信息")
    @GetMapping("/getAccountById")
    public ResultMsg getAccountById(int id,String tokenPrefix) {
        if(!"".equals(tokenPrefix) && tokenPrefix != null){
            if(tokenPrefix.equals("appuser")){
                SysUser sysUser = appUserService.getUserById(id);
                return  ResultMsg.BY_SUCCESS("获取成功",sysUser);
            }else{
                BStudent bStudent=appStudentService.getStudentById(id);
                return  ResultMsg.BY_SUCCESS("获取成功",bStudent);
            }
        }else{
            return  ResultMsg.BY_ERROR("token不能为空!!!");
        }

    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePassord")
    public ResultMsg updatePassword(int id , String oldPassword , String newPassword,String tokenPrefix){
        if(!"".equals(tokenPrefix) && tokenPrefix != null){
            if(tokenPrefix.equals(CommonConstant.TOKEN_USER)){
                if(appUserService.updatePassword(id,oldPassword,newPassword)){
                    return ResultMsg.BY_SUCCESS("修改成功","");
                }else{
                    return ResultMsg.BY_FAIL("修改失败，请检查密码!!!");
                }
            }else if(tokenPrefix.equals(CommonConstant.TOKEN_STUDENT)){
                if(appStudentService.updateStuPassword(id,oldPassword,newPassword)){
                    return ResultMsg.BY_SUCCESS("修改成功","");
                }else{
                    return ResultMsg.BY_FAIL("修改失败，请检查密码!!!");
                }
            }else{
                if(appStudentService.updateParPassword(id,oldPassword,newPassword)){
                    return ResultMsg.BY_SUCCESS("修改成功","");
                }else{
                    return ResultMsg.BY_FAIL("修改失败，请检查密码!!!");
                }
            }

        }else{
            return ResultMsg.BY_FAIL("失败!!!");
        }

    }


    /**
     * 获取图片
     *
     * @param id
     * @param width
     * @param height
     * @param response
     * @throws IOException
     */
    @IgnoreAuth
    @ApiOperation("获取照片")
    @GetMapping("/getPhoto")
    public void getPhotoById(Long id, int width, int height, final HttpServletResponse response) throws IOException {
        BStudent student = appStudentService.getStudentById(id.intValue());
        byte[] data = student.getPhoto();
        if (width != 0 && height != 0) {
            data = scaleImage(data, width, height);
        }
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        InputStream in = new ByteArrayInputStream(data);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf, 0, 1024)) != -1) {
            outputSream.write(buf, 0, len);
        }
        outputSream.close();
    }

    /**
     * 显示图片
     *
     * @param data   byet流
     * @param width  图片的宽度
     * @param height 图片的高度
     * @return 2020.11.19
     * @throws IOException
     */
    public static byte[] scaleImage(byte[] data, int width, int height) throws IOException {
        BufferedImage buffered_oldImage = ImageIO.read(new ByteArrayInputStream(data));
        int imageOldWidth = buffered_oldImage.getWidth();
        int imageOldHeight = buffered_oldImage.getHeight();
        double scale_x = (double) width / imageOldWidth;
        double scale_y = (double) height / imageOldHeight;
        double scale_xy = Math.min(scale_x, scale_y);
        int imageNewWidth = (int) (imageOldWidth * scale_xy);
        int imageNewHeight = (int) (imageOldHeight * scale_xy);
        BufferedImage buffered_newImage = new BufferedImage(imageNewWidth, imageNewHeight, BufferedImage.TYPE_INT_RGB);
        buffered_newImage.getGraphics().drawImage(buffered_oldImage.getScaledInstance(imageNewWidth, imageNewHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        buffered_newImage.getGraphics().dispose();
        ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
        ImageIO.write(buffered_newImage, "jpeg", outPutStream);
        return outPutStream.toByteArray();
    }
}
