package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.ParameterDto;
import com.mitrais.cdcpos.entity.ParameterEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.ParameterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ParameterService {
    private final ParameterRepository parameterRepository;

//    public ParameterEntity add(ParameterDto req){
//        ParameterEntity parameter = new ParameterEntity();
//        //parameter.setName(req.getName().toLowerCase(Locale.ROOT));
//        parameter.setValue(req.getValue());
//        return parameterRepository.save(parameter);
//    }

    public List<ParameterEntity> get(){
        return parameterRepository.findAll();
    }

    public ParameterEntity getById(UUID id){
        return parameterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parameter", "id", id));
    }

    public ParameterEntity update(UUID id, ParameterDto req){
        var parameter = getById(id);
        parameter.setValue(req.getValue());
        return parameterRepository.save(parameter);
    }

    public ParameterEntity getByName(String name){
        return parameterRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Parameter", "name", name));
    }

    public boolean isParameterExist(String name){
        return parameterRepository.existsByName(name);
    }

}
