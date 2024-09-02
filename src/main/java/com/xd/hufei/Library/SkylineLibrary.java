package com.xd.hufei.Library;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.PointerByReference;

import java.util.List;

public class SkylineLibrary {

    // 定义JNA接口，映射导出的函数
    public interface SkylineInterface extends Library {
        SkylineInterface INSTANCE = Native.load("SKYLINE", SkylineInterface.class);

//        int init_algo(String dataFilePath, Structures.skyline_data data, Structures.rtree tree);
//
//        // 定义query_algo方法，参数为skyline_data结构体指针、rtree结构体指针、查询文件路径
//        int query_algo(Structures.skyline_data data, Structures.rtree tree, String queryFilePath,String resultFilePath);
//
//        // 定义free_algo方法，参数为skyline_data结构体指针、rtree结构体指针
//        int free_algo(Structures.skyline_data data, Structures.rtree tree);

        int init_algo(String dataFilePath);

        // 定义query_algo方法，参数为skyline_data结构体指针、rtree结构体指针、查询文件路径
        int query_algo(String queryFilePath,String resultFilePath);

        // 定义free_algo方法，参数为skyline_data结构体指针、rtree结构体指针
        int free_algo();

    }

}
