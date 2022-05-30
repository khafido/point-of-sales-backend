package com.mitrais.cdcpos.controller;

import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.ParameterDto;
import com.mitrais.cdcpos.entity.ParameterEntity;
import com.mitrais.cdcpos.service.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/parameter")
@CrossOrigin(origins="*", maxAge=3600)
@RequiredArgsConstructor
public class ParameterController {
    private final ParameterService parameterService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> add(@RequestBody ParameterDto req){
        ParameterEntity parameter;
        try{
            parameter = parameterService.add(req);
            return new ResponseEntity<>
                    (new GenericResponse(parameter, "Parameter created", GenericResponse.Status.CREATED),
                            HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            return new ResponseEntity<>
                    (new GenericResponse(req, "Paramaeter already exists", GenericResponse.Status.ERROR_INPUT),
                            HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<GenericResponse> getAll(){
        List<ParameterEntity> parameters = parameterService.get();
        return new ResponseEntity<>
                (new GenericResponse(parameters, "Get all parameter success", GenericResponse.Status.SUCCESS),
                        HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<GenericResponse> getByName(@PathVariable("name") String name){
        ParameterEntity parameter = parameterService.getByName(name);
        return new ResponseEntity<>
                (new GenericResponse(parameter, "Get parameter by name success", GenericResponse.Status.SUCCESS),
                        HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable("id") UUID id, @RequestBody ParameterDto req){
        ParameterEntity parameter;
        try{
            parameter = parameterService.update(id,req);
            return new ResponseEntity<>
                    (new GenericResponse(parameter, "Parameter updated", GenericResponse.Status.SUCCESS),
                            HttpStatus.OK);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>
                    (new GenericResponse(req, "Paramaeter already exists", GenericResponse.Status.ERROR_INPUT),
                            HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/check-parameter/{parameter}")
    public boolean checkParameter(@PathVariable("parameter") String parameter){
        return parameterService.isParameterExist(parameter);
    }


}
