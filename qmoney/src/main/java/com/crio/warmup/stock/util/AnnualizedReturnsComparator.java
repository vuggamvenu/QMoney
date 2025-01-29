package com.crio.warmup.stock.util;

import java.util.Comparator;
import com.crio.warmup.stock.dto.AnnualizedReturn;

public class AnnualizedReturnsComparator implements Comparator<AnnualizedReturn>{

    @Override
    public int compare(AnnualizedReturn bean1, AnnualizedReturn bean2) {
       return Double.compare(bean2.getAnnualizedReturn(), bean1.getAnnualizedReturn());
    }
    
}
