package com.xd.hufei.Library;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

// 与其他四个不同的地方就是每次删除情况global_map之后需要自己手动调用一次init_constant去重建global_map
public class SKQLibrary {

    // 定义JNA接口，映射导出的函数
    public interface SKQInterface extends Library {
        SKQInterface INSTANCE =  Native.load("security_keyword_query", SKQInterface.class);

        // 定义原生方法
        int init_algo(String dataFilePath, Structures.DataOwner data);
        int query_algo(Structures.DataOwner data, String queryFilePath, String resultFilePath);
        int free_algo(Structures.DataOwner data);
        void init_constant();
    }

    // 定义结构体
    public static class Structures {
        // 定义结构体
        public static class  DataOwner extends Structure {
            public static class ByReference extends DataOwner implements Structure.ByReference {}
            public static class ByValue extends DataOwner implements Structure.ByValue {}

            public Pointer hashmap_forward;
            public Pointer hashmap_backward;
            public Pointer fileCnt;

            public int i;
            public int is_back;

            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("hashmap_forward", "hashmap_backward", "fileCnt", "i", "is_back");
            }
        }
    }
}
