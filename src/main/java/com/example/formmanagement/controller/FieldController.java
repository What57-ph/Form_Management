package com.example.formmanagement.controller;

import com.example.formmanagement.domain.request.RequestFieldDTO;
import com.example.formmanagement.domain.response.ResponseFieldDTO;
import com.example.formmanagement.domain.response.RestResponse;
import com.example.formmanagement.service.FieldService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/fields")
public class FieldController {
    FieldService fieldService;

    @GetMapping("")
    public ResponseEntity<RestResponse<List<ResponseFieldDTO>>> getAllFields() {
        return ResponseEntity.ok(RestResponse.<List<ResponseFieldDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Fetch successfully")
                .data(fieldService.getAllFields())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<ResponseFieldDTO>> getSingleField(@PathVariable("id") Long id) {
        return ResponseEntity.ok(RestResponse.<ResponseFieldDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Fetch successfully")
                .data(fieldService.getSingleField(id))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Object>> deleteField(@PathVariable("id") Long id) {
        fieldService.deleteField(id);
        return ResponseEntity.ok(RestResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Delete field successfully")
                .data(null)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<Object>> updateField(
            @PathVariable("id") Long id,
            @Valid @RequestBody RequestFieldDTO requestFieldDTO){
        fieldService.updateField(requestFieldDTO, id);
        return ResponseEntity.ok(RestResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Update field successfully")
                .data(null)
                .build());
    }

    @PostMapping("")
    public ResponseEntity<RestResponse<ResponseFieldDTO>> createField(@Valid @RequestBody RequestFieldDTO requestFieldDTO){
        fieldService.addField(requestFieldDTO);
        return ResponseEntity.ok(RestResponse.<ResponseFieldDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .error(null)
                .message("Create field successfully")
                .data(null)
                .build());
    }

}
