package com.xd.hufei.services.other.impl;

import com.xd.hufei.services.other.AlgoEtpssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Slf4j
@Service
public class AlgoEtpssServiceImpl implements AlgoEtpssService {

    @Override
    public Etpss Share(BigInteger val) {

        if(val.compareTo(MOD) >= 0){
            log.error("The value of num exceeds 2 ^ 64");
            return null;
        }
        BigInteger negate = MOD.negate();
        if(val.compareTo(negate) <= 0){
            log.error("The value of num exceeds -（ 2 ^ 64）\n");
            return null;
        }

        Etpss etpss = new Etpss();
        etpss.share(MOD.bitLength(),rand,val);
        return etpss;
    }

    @Override
    public Etpss Share(String val) {
        BigInteger v = new BigInteger(val);
        return Share(v);
    }

    @Override
    public BigInteger Recover(Etpss e) {
        BigInteger x = new BigInteger(e.cs1.x.toByteArray()).add(e.cs2.x).add(e.cs3.x).mod(MOD);

        if(x.compareTo(MOD.divide(BigInteger.TWO)) < 0){
            return x;
        }else{
            return x.subtract(MOD);
        }

    }
    @Override
    public String Object2String(Etpss e) {
        return e.cs1.x.toString() + SEPARATOR + e.cs2.x.toString() + SEPARATOR + e.cs3.x.toString();
    }
}
