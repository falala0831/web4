package cn.bupt.edu.web4.Data;

import lombok.Data;

import java.io.Serializable;

@Data // 该直接会为该类的每一个数据成员添加get 和 set 函数
public class LoginInfor implements Serializable {

    private String username;

    private String password;

    private String message;


}
