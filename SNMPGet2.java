/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.JOptionPane;
import org.snmp4j.*;
import org.snmp4j.smi.*;
import org.snmp4j.mp.*;
import org.snmp4j.transport.*;
import org.snmp4j.event.*;

/**
 *
 * @author robsonsantos
 */
public class SNMPGet2 {

    public static void main(String[] args) {
        try {

            TransportMapping transporte = new DefaultUdpTransportMapping();
            Snmp snmp = new Snmp(transporte);
            snmp.listen();

            PDU pdu = new PDU();
            pdu.setType(PDU.GET);

            VariableBinding vb = new VariableBinding(SnmpConstants.sysDescr);
            pdu.add(vb);
            vb = new VariableBinding(SnmpConstants.sysName);
            pdu.add(vb);
            vb = new VariableBinding(SnmpConstants.sysLocation);
            pdu.add(vb);
            vb = new VariableBinding(SnmpConstants.sysUpTime);
            pdu.add(vb);
            
            

            String destino = JOptionPane.showInputDialog("Digite o endereço do elemento que será gerenciado.", null);
            Address endereco = GenericAddress.parse(destino + "/161");

            CommunityTarget alvo = new CommunityTarget();
            alvo.setCommunity(new OctetString("public"));
            alvo.setAddress(endereco);
            alvo.setVersion(SnmpConstants.version1);

            ResponseEvent resposta = snmp.send(pdu, alvo);
            PDU pduResposta = resposta.getResponse();
            System.out.println("Descrição: "+pduResposta.get(0).getVariable());
            System.out.println("Nome: "+pduResposta.get(1).getVariable());
            System.out.println("Localização: "+pduResposta.get(2).getVariable());
            System.out.println("Tempo de funcionamento do Sistema: "+pduResposta.get(3).getVariable());
            snmp.close();

        } catch (Exception exc) {
            System.out.println(exc.toString());
            System.out.println("Deu treta");

        }

    }
}
