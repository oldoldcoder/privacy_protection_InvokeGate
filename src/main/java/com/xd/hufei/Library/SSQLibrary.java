package com.xd.hufei.Library;


import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;

import java.util.List;

public class SSQLibrary {

    // 定义JNA接口，映射导出的函数
    public interface SSQInterface extends Library {
        SSQInterface INSTANCE = Native.load("security_similarity_query", SSQInterface.class);

        int init_algo(String dataFilePath, Structures.SSQ_data data, Structures.kd_tree tree, Structures.SSQ_data kArr);
        int query_algo(Structures.SSQ_data data, Structures.kd_tree tree, Structures.SSQ_data kArr, String queryFilePath, String resultFilePath);
        int free_algo(Structures.SSQ_data data, Structures.kd_tree tree, Structures.SSQ_data kArr);

    }

    // 定义结构体
    public static class Structures {
        public static class SSQ_data extends Structure {

            public int n;
            public int dim;
            public PointerByReference total_data;

            @Override
            protected List<String> getFieldOrder() {
                return List.of("n", "dim", "total_data");
            }

            public static class ByReference extends SkylineLibrary.Structures.skyline_data implements Structure.ByReference {
            }

            public static class ByValue extends SkylineLibrary.Structures.skyline_data implements Structure.ByValue {
            }
        }

        // Define the kd_tree structure
        public static class kd_tree extends Structure {
            public static class ByReference extends kd_tree implements Structure.ByReference {
            }

            public static class ByValue extends kd_tree implements Structure.ByValue {
            }

            public int dim;
            public tree_node.ByReference root;

            @Override
            protected List<String> getFieldOrder() {
                return List.of("dim", "root");
            }
        }

        // Define the tree_node structure
        public static class tree_node extends Structure {
            public static class ByReference extends tree_node implements Structure.ByReference {
            }

            public static class ByValue extends tree_node implements Structure.ByValue {
            }

            public int is_leaf_node;
            public int dir;
            public int dim;
            public union_data data;
            public ByReference left;
            public ByReference right;

            @Override
            protected List<String> getFieldOrder() {
                return List.of("is_leaf_node", "dir", "dim", "data", "left", "right");
            }

            public static class union_data extends Union {
                public divide_data divide;
                public leaf_data leaf;

                public static class divide_data extends Structure {
                    public int divide_dim;
                    public Pointer divide_val;
                    public Pointer en_divide_dim;
                    public Pointer en_divide_val;

                    @Override
                    protected List<String> getFieldOrder() {
                        return List.of("divide_dim", "divide_val", "en_divide_dim", "en_divide_val");
                    }
                }

                public static class leaf_data extends Structure {
                    public leaf_val.ByReference data_root;
                    public leaf_val.ByReference data_now;

                    @Override
                    protected List<String> getFieldOrder() {
                        return List.of("data_root", "data_now");
                    }
                }
            }
        }

        // Define the leaf_val structure
        public static class leaf_val extends Structure {
            public static class ByReference extends leaf_val implements Structure.ByReference {
            }

            public static class ByValue extends leaf_val implements Structure.ByValue {
            }

            public Pointer val;
            public ByReference next;

            @Override
            protected List<String> getFieldOrder() {
                return List.of("val", "next");
            }
        }

        // Define the dim_data structure
        public static class dim_data extends Structure {
            public static class ByReference extends dim_data implements Structure.ByReference {
            }

            public static class ByValue extends dim_data implements Structure.ByValue {
            }

            public int dim;
            public Pointer single_data;
            public Pointer en_data;

            @Override
            protected List<String> getFieldOrder() {
                return List.of("dim", "single_data", "en_data");
            }
        }
    }

}