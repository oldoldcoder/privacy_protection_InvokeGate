package com.xd.hufei.Library;


import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;

import java.util.List;

public class SSQLibrary {

    // 定义JNA接口，映射导出的函数
    public interface SSQInterface extends Library {
        SSQInterface INSTANCE = Native.load("security_similarity_query", SSQInterface.class);

//        int init_algo(String dataFilePath, Structures.SSQ_data data, Structures.kd_tree tree, Structures.SSQ_data kArr);
//        int query_algo(Structures.SSQ_data data, Structures.kd_tree tree, Structures.SSQ_data kArr, String queryFilePath, String resultFilePath);
//        int free_algo(Structures.SSQ_data data, Structures.kd_tree tree, Structures.SSQ_data kArr);

        int init_algo(String dataFilePath);
        int query_algo(String queryFilePath, String resultFilePath);

    }

}