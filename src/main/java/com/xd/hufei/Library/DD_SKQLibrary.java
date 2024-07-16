package com.xd.hufei.Library;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class DD_SKQLibrary {
    // 定义JNA接口，映射导出的函数
    public interface DD_SKQInterface extends Library {
        DD_SKQLibrary.DD_SKQInterface INSTANCE = Native.load("dd_skq", DD_SKQLibrary.DD_SKQInterface.class);

        // 定义原生方法
        int init_algo(DD_SKQLibrary.Structures.DD_SKQDataSet set, String filePath);
        int query_algo(DD_SKQLibrary.Structures.DD_SKQDataSet set, String queryPath, String resultFilePath);
        int free_algo(DD_SKQLibrary.Structures.DD_SKQDataSet set);
    }

    // 定义结构体
    public static class Structures {

        public static class DD_SKQDataSet extends Structure {
            public static class ByReference extends DRQLibrary.Structures.DrqDataSet implements Structure.ByReference {}
            public static class ByValue extends DRQLibrary.Structures.DrqDataSet implements Structure.ByValue {}

            public int n;
            public Pointer ownerList;

            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("n", "ownerList");
            }
        }
    }
}
