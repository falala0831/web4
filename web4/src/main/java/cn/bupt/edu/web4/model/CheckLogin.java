package cn.bupt.edu.web4.model;

import cn.bupt.edu.web4.Data.LoginInfor;

public class CheckLogin {

    public static boolean Check(LoginInfor infor) {
        if ("lxl".equals(infor.getUsername()) && "123456".equals(infor.getPassword()))
            return true;
        else
            return false;
    }
}
