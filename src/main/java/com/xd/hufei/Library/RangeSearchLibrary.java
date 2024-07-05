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
        int init_algo(String dataFilePath, Structures.PtreeB_data data, Structures.kd_tree tree);
        int query_algo(Structures.PtreeB_data data, Structures.kd_tree tree, String queryFilePath, String resultFilePath);
        int free_algo(Structures.PtreeB_data data, Structures.kd_tree tree);
    }


    public static class Structures{

        // 定义结构体PtreeB_data的映射
        public static class  PtreeB_data extends Structure {
            public static class ByReference extends PtreeB_data implements Structure.ByReference {}

            public int n;
            public int dim;
            public Pointer total_data; // 这里是一个指针，具体如何使用要看实际情况
            public Pointer en_total_data; // 这里同样是一个指针

            // 使用JNA提供的内存释放函数释放内存
            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("n", "dim", "total_data", "en_total_data");
            }
        }

        // 定义结构体kd_tree的映射
        public static class kd_tree extends Structure {
            public static class ByReference extends kd_tree implements Structure.ByReference {}

            public int dim;
            public Pointer root; // 这里是一个指针

            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("dim", "root");
            }
        }
    }
}

