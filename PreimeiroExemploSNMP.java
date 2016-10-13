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
public class PrimeiroExemploSNMP {
    public static void main(String[] args)  {
        try{
            String remoto = "192.168.25.7/161";
            
            TransportMapping transporte = new DefaultUdpTransportMapping();
            Snmp snmp =  new Snmp(transporte);
            snmp.listen();
            
            PDU pdu = new PDU();
            pdu.setType(PDU.GETNEXT);
            pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.5")));
            
            Address endereco = GenericAddress.parse(remoto);
            CommunityTarget alvo = new CommunityTarget();
            alvo.setCommunity(new OctetString("public"));
            alvo.setAddress(endereco);
            alvo.setVersion(SnmpConstants.version1);
            
            
            ResponseEvent resposta = snmp.send(pdu, alvo);
            
            
            PDU PDUResposta = resposta.getResponse();
            System.out.println("Resposta: "+PDUResposta);
        
        }catch(Exception exc){
            System.out.println(exc.toString());
            System.out.println("Deu Merda");
           
        }
    }
}
