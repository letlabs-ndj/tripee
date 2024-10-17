package com.lameute.expedition_service.service;

import com.lameute.expedition_service.repo.ExpeditionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpeditionService {
    @Autowired
    private ExpeditionRepo expeditionRepo;


}
