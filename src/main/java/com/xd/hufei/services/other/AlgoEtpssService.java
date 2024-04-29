package com.xd.hufei.services.other;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author heqi
 * @time 2024/04/29
 * @desc eTPSS算法的java本地实现，不确保吞吐量的情况下
 * */
public interface AlgoEtpssService {


    // 初始化变量
    BigInteger MOD = BigInteger.valueOf(2).pow(65);
    Random rand = new Random(System.currentTimeMillis());
    /**
     * 定义分隔符
     * */
    String SEPARATOR = "#";

    // 分享
    public Etpss Share(BigInteger val);
    public Etpss Share(String val);
    // 恢复
    public BigInteger Recover(Etpss e);

    // 转换为字符串
    public String Object2String(Etpss e);

    public static  class Etpss{
        public cli cs1 = null;
        public cli cs2 = null;
        public cli cs3 = null;
        boolean isMultiRes = false;

        public Etpss(){
            this.isMultiRes = false;
            this.cs1 = new cli();
            this.cs2 = new cli();
            this.cs3 = new cli();
        }
        public void share(int len,Random random,BigInteger val){
            // 范围内取随机值
            this.cs1.x = new BigInteger(len,random).mod(MOD);
            this.cs2.x = new BigInteger(len,random).mod(MOD);
            BigInteger bigInteger = new BigInteger(val.toByteArray());
            this.cs3.x = bigInteger.subtract(this.cs1.x).subtract(this.cs2.x).mod(MOD);

        }
        public static class cli{
            // 原值
            public BigInteger x = null;
            // 扰动1
            public BigInteger r1 = null;
            // 扰动2
            public BigInteger r2 = null;

        }
    }
}


