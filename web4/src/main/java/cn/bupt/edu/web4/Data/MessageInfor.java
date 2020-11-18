package cn.bupt.edu.web4.Data;

import lombok.Data;

import java.io.Serializable;

@Data
// 对于添加联系人 前端会先检查数据格式（JS） 然后服务器只需要检查是不是重名就行了
public class MessageInfor implements Serializable {

    private String contactname; // 联系人姓名，主键

    private String tel;         // 联系人电话

    private String email;       // 邮箱

    private String addr;        // 住址

    private String qq;          // qq号

    private String message;     // 提示消息

    public MessageInfor(String cont, String tel, String email, String addr, String qq, String message) {
        this.contactname = cont;
        this.tel = tel;
        this.email = email;
        this.qq = qq;
        this.addr = addr;
        this.message = message;
    }

}
