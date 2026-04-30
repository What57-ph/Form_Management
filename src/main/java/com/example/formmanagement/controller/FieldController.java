package com.example.formmanagement.controller;

import com.example.formmanagement.domain.request.RequestField;
import com.example.formmanagement.domain.response.ResponseField;
import com.example.formmanagement.domain.response.RestResponse;
import com.example.formmanagement.service.FieldService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/field")
public class FieldController {
    FieldService fieldService;

    @GetMapping("")
    public ResponseEntity<RestResponse<List<ResponseField>>> getAllFields() {
        return ResponseEntity.ok(RestResponse.<List<ResponseField>>builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Fetch successfully")
                .data(fieldService.getAllResponse())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<ResponseField>> getSingleField(@PathVariable("id") Long id) {
        return ResponseEntity.ok(RestResponse.<ResponseField>builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Fetch successfully")
                .data(fieldService.getSingleResponse(id))
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

    @PutMapping("")
    public ResponseEntity<RestResponse<Object>> updateField(@RequestBody RequestField requestField){
        fieldService.updateField(requestField);
        return ResponseEntity.ok(RestResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Update field successfully")
                .data(null)
                .build());
    }

    @PostMapping("")
    public ResponseEntity<RestResponse<Object>> createField(@RequestBody RequestField requestField){
        fieldService.addField(requestField);
        return ResponseEntity.ok(RestResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .error(null)
                .message("Create field successfully")
                .data(null)
                .build());
    }

}
