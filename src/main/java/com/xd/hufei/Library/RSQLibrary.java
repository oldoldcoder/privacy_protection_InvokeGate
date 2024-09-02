package com.xd.hufei.Library;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.PointerByReference;

import java.util.Arrays;
import java.util.List;

public class RSQLibrary {

    public interface RSQInterface extends Library {
        RSQLibrary.RSQInterface INSTANCE = Native.load("reverse_similarity_query", RSQLibrary.RSQInterface.class);

        //        int init_algo(String dataFilePath, Structures.RSQ_data data, Structures.mr_tree tree);
//        int query_algo(Structures.RSQ_data data, Structures.mr_tree tree, String queryFilePath, String resultFilePath);
//        int free_algo(Structures.RSQ_data data, Structures.mr_tree tree);
        int init_algo(String dataFilePath);
        int query_algo(String queryFilePath, String resultFilePath);
//        int free_algo(Structures.RSQ_data data, Structures.mr_tree tree);
    }

}
