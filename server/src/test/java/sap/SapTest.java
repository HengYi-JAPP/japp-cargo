package sap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hengyi.japp.cargo.domain.sap.Likp;
import com.hengyi.japp.cargo.domain.sap.Lips;
import com.hengyi.japp.cargo.domain.sap.Ylips;
import com.hengyi.japp.sap.RfcExeCommand;
import com.hengyi.japp.sap.client.JSap;
import com.hengyi.japp.sap.client.RfcClient;
import com.hengyi.japp.sap.grpc.server.ExeRfcReply;

import static org.jzb.Constant.MAPPER;

/**
 * 描述：
 *
 * @author jzb 2017-11-20
 */
public class SapTest {
    public static void main(String[] args) throws Exception {
        final RfcClient rfcClient = JSap.grpcClient();
        RfcExeCommand command = new RfcExeCommand();
        final ObjectNode imports = MAPPER.createObjectNode().put("I_POUND_NO", "5201711220063");
        command.setImports(imports);
        final ExeRfcReply res = rfcClient.exeRfc("ZJAPP_CARGO_3", command);
        final JsonNode exports = MAPPER.readTree(res.getExports());
        final JsonNode E_LIKP = exports.get("E_LIKP");
        final Likp likp = MAPPER.convertValue(E_LIKP, Likp.class);
        final JsonNode E_LIPS = exports.get("E_LIPS");
        Lips lips = MAPPER.convertValue(E_LIPS, Lips.class);
        lips.setLikp(likp);
        Ylips ylips = MAPPER.convertValue(exports.get("E_YLIPS"), Ylips.class);
        ylips.setLips(lips);
        System.out.println(ylips);
    }
}
