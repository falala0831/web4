package cn.bupt.edu.web4.Data;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

@Data
public class Table implements Serializable {

    private Vector<MessageInfor> tableinfo;

    public Table() {
        tableinfo = new Vector<MessageInfor>();
        tableinfo.add(new MessageInfor("张三", "11111111111", "233333@qq.com", "北京市海淀区", "12345678", ""));
        tableinfo.add(new MessageInfor("李四", "11111111112", "wtf@126.com", "北京市昌平区", "87654321", ""));
        tableinfo.add(new MessageInfor("王五", "11111111113", "123123@163.com", "北京市朝阳区", "13579246", ""));
    }
}
