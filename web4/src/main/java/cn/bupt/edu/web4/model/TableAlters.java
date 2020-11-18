package cn.bupt.edu.web4.model;

import cn.bupt.edu.web4.Data.MessageInfor;
import cn.bupt.edu.web4.Data.Table;

import java.util.Vector;

public class TableAlters {
    public static boolean checkValidAdd(Table table, MessageInfor infor) {
        boolean isvalid = true;
        Vector<MessageInfor> list = table.getTableinfo();
        for (int i = 0; i < list.size() && isvalid; i++) {
            if (list.elementAt(i).getContactname().equals(infor.getContactname()))
                isvalid = false;
        }
        return isvalid;
    }

    // 更新指定元素
    public static boolean alterElem(Table table, MessageInfor infor) {
        int index = -1;
        Vector<MessageInfor> list = table.getTableinfo();
        for (int i = 0; i < list.size() && -1 == index; i++) {
            if (list.elementAt(i).getContactname().equals(infor.getContactname()))
                index = i;
        }

        if (index != -1) { //如果找到了 就替换该元素
            list.set(index, infor);
            return true;
        } else {
            return false;
        }
    }

    public static void deleteElem(Table table, int index) {
        table.getTableinfo().remove(index);
    }
}

