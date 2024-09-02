package com.xd.hufei.Library;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

// 使用JNA技术
public class RangeSearchLibrary {

    // 定义JNA接口，映射导出的函数
    public interface RangeSearchInterface extends Library {
        RangeSearchInterface INSTANCE = Native.load("range_search", RangeSearchInterface.class);

        // 定义JNA方法映射
//        int init_algo(String dataFilePath, Structures.PtreeB_data data, Structures.kd_tree tree);
//        int query_algo(Structures.PtreeB_data data, Structures.kd_tree tree, String queryFilePath, String resultFilePath);
//        int free_algo(Structures.PtreeB_data data, Structures.kd_tree tree);

        int init_algo(String dataFilePath);
        int query_algo(String queryFilePath, String resultFilePath);
        int free_algo();
    }

}

