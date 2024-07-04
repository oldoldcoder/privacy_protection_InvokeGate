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

        int init_algo(String dataFilePath, Structures.RSQ_data data, Structures.mr_tree tree);
        int query_algo(Structures.RSQ_data data, Structures.mr_tree tree, String queryFilePath, String resultFilePath);
        int free_algo(Structures.RSQ_data data, Structures.mr_tree tree);
    }

    public static class Structures{

        // 定义set_y结构体
        public static class set_y extends Structure {
            public int dim;
            public PointerByReference single_data; // BIGNUM ** single_data

            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("dim", "single_data");
            }

            public static class ByReference extends set_y implements Structure.ByReference {}
            public static class ByValue extends set_y implements Structure.ByValue {}
        }

        // 定义set_x结构体
        public static class set_x extends Structure {
            public int dim;
            public PointerByReference en_data; // eTPSS ** en_data
            public PointerByReference de_data; // BIGNUM ** de_data

            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("dim", "en_data", "de_data");
            }

            public static class ByReference extends set_x implements Structure.ByReference {}
            public static class ByValue extends set_x implements Structure.ByValue {}
        }

        // 定义RSQ_data结构体
        public static class RSQ_data extends Structure {
            public int xn;
            public int yn;
            public int dim;
            public PointerByReference en_x; // set_x ** en_x
            public PointerByReference open_y; // set_y ** open_y

            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("xn", "yn", "dim", "en_x", "open_y");
            }

            public static class ByReference extends RSQ_data implements Structure.ByReference {}
            public static class ByValue extends RSQ_data implements Structure.ByValue {}
        }

        // 定义mr_node结构体
        public static class mr_node extends Structure {
            public set_x.ByReference data;
            public int is_left_node;
            public PointerByReference range; // eTPSS *** range
            public PointerByReference distance; // eTPSS ** distance
            public Pointer maxDistance; // eTPSS * maxDistance
            public int dim;
            public mr_node.ByReference left;
            public mr_node.ByReference right;

            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("data", "is_left_node", "range", "distance", "maxDistance", "dim", "left", "right");
            }

            public static class ByReference extends mr_node implements Structure.ByReference {}
            public static class ByValue extends mr_node implements Structure.ByValue {}
        }

        // 定义mr_tree结构体
        public static class mr_tree extends Structure {
            public mr_node.ByReference root;

            @Override
            protected List<String> getFieldOrder() {
                return List.of("root");
            }

            public static class ByReference extends mr_tree implements Structure.ByReference {}
            public static class ByValue extends mr_tree implements Structure.ByValue {}
        }
    }
}
