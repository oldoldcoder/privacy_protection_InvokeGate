package com.xd.hufei.Library;

import com.sun.jna.Library;
import com.sun.jna.Native;




public class ScComputingLibrary {
    public interface ScComputingInterface extends Library {
        ScComputingInterface INSTANCE =  Native.load("dd_cComputing", ScComputingInterface.class);
        /**
         * @param algoName:代表调用具体算法的名称
         * @param fileString:具体文件数据的路径
         * @param resultFilePath:具体结果文件的路径
         * @return SUCCESS:1 ERROR:0
         * */
        int deal(String algoName, String fileString, String resultFilePath) ;
    }
    public static class Structures {

    }
}

