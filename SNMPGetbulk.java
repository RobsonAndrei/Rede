/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.snmp4j.*;
import org.snmp4j.smi.*;
import org.snmp4j.mp.*;
import org.snmp4j.transport.*;
import org.snmp4j.event.*;


/**
 *
 * @author robsonsantos
 */
public class SNMPGetbulk {

    public static void main(String[] args) {
        try {
            TransportMapping transporte = new DefaultUdpTransportMapping();
            Snmp snmp = new Snmp(transporte);
            snmp.listen();

            PDU pdu = new PDU();
            pdu.setType(PDU.GETBULK);
            pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.4.22")));
            pdu.setMaxRepetitions(500);

            Address endereco = GenericAddress.parse("localhost/161");
            CommunityTarget alvo = new CommunityTarget();
            alvo.setCommunity(new OctetString("public"));
            alvo.setAddress(endereco);
            alvo.setVersion(SnmpConstants.version2c);
            
            ResponseEvent resposta = snmp.send(pdu, alvo);
            
            System.out.println("Informações da MIB-2");
            
            
            for (Object variavel : resposta.getResponse().getVariableBindings()) {
                System.out.println(variavel.toString());
            }

        } catch (Exception exc) {
            System.out.println(exc.toString() + " Deu treta mano!");
        }
    }
}
