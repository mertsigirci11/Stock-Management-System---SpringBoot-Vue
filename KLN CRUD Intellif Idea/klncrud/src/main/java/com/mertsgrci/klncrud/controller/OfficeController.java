package com.mertsgrci.klncrud.controller;

import com.mertsgrci.klncrud.dto.OfficeDto;
import com.mertsgrci.klncrud.model.Office;
import com.mertsgrci.klncrud.service.OfficeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/offices")
@CrossOrigin("http://127.0.0.1:5173/")
public class OfficeController
{
    @Autowired
    private ModelMapper modelMapper;

    private final OfficeService officeService;

    public OfficeController (OfficeService officeService)
    {
        this.officeService = officeService;
    }

    @GetMapping
    public List<OfficeDto> getAllOffices()
    {
        return officeService.getAllOffices().stream()
                .map(office -> modelMapper.map(office,OfficeDto.class)).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<OfficeDto> createOffice(@RequestBody OfficeDto officeDto)
    {
        //Converting Dto to Entity
        Office officeRequest = modelMapper.map(officeDto, Office.class);

        //Creating Operation
        officeService.createOffice(officeRequest);

        //Converting Entity to Dto
        OfficeDto officeDtoResponse = modelMapper.map(officeRequest,OfficeDto.class);

        return new ResponseEntity<OfficeDto>(officeDtoResponse,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeDto> updateOffice(@PathVariable("id") Integer id, @RequestBody OfficeDto officeDto)
    {
        //Converting Dto to Entity
        Office officeRequest = modelMapper.map(officeDto,Office.class);

        //Updating Operation
        officeService.updateOffice(officeRequest,id);

        //Converting Entity to Dto
        OfficeDto officeDtoResponse = modelMapper.map(officeRequest,OfficeDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(officeDtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffice(@PathVariable("id") Integer id)
    {
        try
        {
            officeService.deleteOffice(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
