package com.xd.hufei.Library;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;

public class DD_SSQLibrary {

    public interface DD_SSQInterface extends Library {
        DD_SSQLibrary.DD_SSQInterface INSTANCE = Native.load("dd_rsq", DD_SSQLibrary.DD_SSQInterface.class);

        // 定义原生方法
        int init_algo(DD_SSQLibrary.Structures.DD_SSQ_DataSet set, String filePath);

        int query_algo(DD_SSQLibrary.Structures.DD_SSQ_DataSet set, String queryPath, String pointYPath, String resultFilePath);

        int free_algo(DD_SSQLibrary.Structures.DD_SSQ_DataSet set);
    }
    public static class Structures {
        public static class DD_SSQ_DataSet extends Structure {

        }
    }
}
