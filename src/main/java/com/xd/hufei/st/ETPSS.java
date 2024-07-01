package com.xd.hufei.st;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"CS1","CS2","CS3"})
public class ETPSS extends Structure {
    public Cli CS1;
    public Cli CS2;
    public Cli CS3;
    public Pointer ctx;
    public int is_multi_res;

    public ETPSS() {
        super();
    }

    public static class ByReference extends ETPSS implements Structure.ByReference {
    }
}