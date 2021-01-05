package cn.sdormitory.service;

import cn.sdormitory.basedata.entity.BStudent;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/28 11:07
 * @version：V1.0
 */
public interface AppStudentService {
    BStudent getStudentById(int id);

    BStudent getStudentByNo(String studentNo);

    boolean updateStuPassword(int id , String oldPassword , String newPassword);

    boolean updateParPassword(int id , String oldPassword , String newPassword);
}
