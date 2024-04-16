package com.xd.hufei.dto.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesensitizedData {

    BigInteger originalNumber;

    BigInteger cs1;
    BigInteger cs2;
    BigInteger cs3;
}
