package com.hengyi.japp.cargo.application.query;

import com.hengyi.japp.cargo.domain.pta.PtaSendInfo;
import com.hengyi.japp.cargo.domain.repository.PtaSendInfoRepository;
import org.jzb.search.JPageQuery;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

/**
 * 描述：
 *
 * @author jzb 2017-12-01
 */
public class PtaSendInfoQuery extends JPageQuery<Stream<PtaSendInfo>> {
    public LocalDate ld;
    public LocalDate ldStart;
    public LocalDate ldEnd;
    public Set<String> packTypes;
    public Set<String> transTypes;

    public PtaSendInfoQuery(Principal principal, int first, Integer pageSize) {
        super(principal, first, pageSize);
    }

    public PtaSendInfoQuery(Principal principal) {
        super(principal, 0, null);
    }

    public PtaSendInfoQuery exe(PtaSendInfoRepository ptaSendInfoRepository) {
        ptaSendInfoRepository.query(this);
        return this;
    }
}
