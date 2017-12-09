package com.hengyi.japp.cargo.application.query;

import com.hengyi.japp.cargo.domain.meg.MegSendInfo;
import com.hengyi.japp.cargo.domain.repository.MegSendInfoRepository;
import org.jzb.search.JPageQuery;

import java.security.Principal;
import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * 描述：
 *
 * @author jzb 2017-12-01
 */
public class MegSendInfoQuery extends JPageQuery<Stream<MegSendInfo>> {
    public LocalDate ld;
    public LocalDate ldStart;
    public LocalDate ldEnd;

    public MegSendInfoQuery(Principal principal, int first, Integer pageSize) {
        super(principal, first, pageSize);
    }

    public MegSendInfoQuery(Principal principal) {
        super(principal, 0, null);
    }

    public MegSendInfoQuery exe(MegSendInfoRepository megSendInfoRepository) {
        megSendInfoRepository.query(this);
        return this;
    }
}
