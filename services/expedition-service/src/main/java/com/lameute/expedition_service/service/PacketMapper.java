package com.lameute.expedition_service.service;

import com.lameute.expedition_service.dto.ExpeditionRequest;
import com.lameute.expedition_service.dto.PacketRequest;
import com.lameute.expedition_service.model.Expedition;
import com.lameute.expedition_service.model.Packet;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class PacketMapper {
    /*Converts Packet request to Packet object */
    public Packet toPacket(PacketRequest request){
        Packet packet = new Packet();
        packet.setDescription(request.description());
        packet.setWeight(request.weight());
        packet.setLength(request.length());
        packet.setWidth(request.width());
        packet.setHeight(request.height());

        return packet;
    }

    /*Update packet attributes with PacketRequest data */
    public void mergePacket(Packet packet, PacketRequest request){
        if(StringUtils.isNotBlank(request.description())){
            packet.setDescription(request.description());
        }
        if(request.height() != null){
            packet.setHeight(request.height());
        }
        if(request.length() != null){
            packet.setLength(request.length());
        }
        if(request.width() != null){
            packet.setWidth(request.width());
        }
        if(request.weight() != null){
            packet.setWeight(request.weight());
        }

    }

}
