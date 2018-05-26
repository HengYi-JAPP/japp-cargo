import com.google.common.collect.Sets;
import com.hengyi.japp.cargo.application.command.DailyDetailExportTokenCommand;
import com.hengyi.japp.cargo.application.command.EntityDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jzb.J;

import java.util.Date;
import java.util.HashSet;

import static com.hengyi.japp.cargo.Constant.JAPP_CARGO_PERMANENT_KEY;
import static org.jzb.Constant.MAPPER;

/**
 * 描述：
 *
 * @author jzb 2018-01-02
 */
public class Tst {
    public static void main(String[] args) {
        DailyDetailExportTokenCommand command = new DailyDetailExportTokenCommand();
        command.setStartDate(new Date());
        command.setEndDate(new Date());
        command.setAllT001s(true);
        final HashSet<EntityDTO> t001s = Sets.newHashSet();
        EntityDTO dto = new EntityDTO();
        dto.setId("1");
        t001s.add(dto);
        dto = new EntityDTO();
        dto.setId("2");
        t001s.add(dto);
        command.setT001s(t001s);
        final String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, JAPP_CARGO_PERMANENT_KEY)
                .setIssuedAt(new Date())
                .claim("command", command)
                .compact();
        Claims claims = Jwts.parser().setSigningKey(JAPP_CARGO_PERMANENT_KEY).parseClaimsJws(token).getBody();
        final Object o = claims.get("command");
        final DailyDetailExportTokenCommand com = MAPPER.convertValue(o, DailyDetailExportTokenCommand.class);
        System.out.println(com);
    }
}
