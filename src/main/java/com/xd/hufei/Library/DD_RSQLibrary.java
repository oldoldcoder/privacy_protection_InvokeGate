package com.xd.hufei.Library;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;

/**
 * @author heqi
 * @date 16/07/2024
 * */

public class DD_RSQLibrary {

    public interface DD_RSQInterface extends Library {
        DD_RSQLibrary.DD_RSQInterface INSTANCE = Native.load("dd_rsq", DD_RSQLibrary.DD_RSQInterface.class);

        // 定义原生方法
        int init_algo(DD_RSQLibrary.Structures.DD_RSQ_DataSet set, String filePath);

        int query_algo(DD_RSQLibrary.Structures.DD_RSQ_DataSet set, String queryPath, String pointYPath, String resultFilePath);

        int free_algo(DD_RSQLibrary.Structures.DD_RSQ_DataSet set);
    }

    public static class Structures {
        public static class DD_RSQ_DataSet extends Structure {

        }
    }

}
