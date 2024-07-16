package com.xd.hufei.Library;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class DRQLibrary {

    // 定义JNA接口，映射导出的函数
    public interface DRQInterface extends Library {
        DRQInterface INSTANCE = Native.load("drq", DRQInterface.class);

        // 定义原生方法
        int init_algo(Structures.DrqDataSet set, String filePath);
        int query_algo(Structures.DrqDataSet set, String queryPath, String resultFilePath);
        int free_algo(Structures.DrqDataSet set);
    }

    // 定义结构体
    public static class Structures {

        public static class DrqDataSet extends Structure {
            public static class ByReference extends DrqDataSet implements Structure.ByReference {}
            public static class ByValue extends DrqDataSet implements Structure.ByValue {}

            public int n;
            public int d;
            public Pointer owners;
            public Pointer ctx; // BN_CTX 需要特别处理

            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("n", "d", "owners", "ctx");
            }
        }
    }
}