package com.hengyi.japp.cargo.interfaces.sap;

import com.hengyi.japp.cargo.domain.sap.Ylips;

import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * 描述：
 *
 * @author jzb 2017-12-01
 */
public interface SapService {

    Ylips findYlips(String ylipsId) throws Exception;

    Stream<Ylips> queryYlips(LocalDate ld) throws Exception;
}
