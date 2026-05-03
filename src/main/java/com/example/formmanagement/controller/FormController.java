package com.example.formmanagement.controller;

import com.example.formmanagement.domain.request.RequestFieldDTO;
import com.example.formmanagement.domain.request.RequestFormDTO;
import com.example.formmanagement.domain.request.RequestSubmitDTO;
import com.example.formmanagement.domain.response.*;
import com.example.formmanagement.service.FormService;
import com.example.formmanagement.service.SubmissionService;
import com.example.formmanagement.utils.exception.FieldValidationException;
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
@RequestMapping("api/v1/forms")
public class FormController {
    FormService formService;
    SubmissionService submissionService;

    @GetMapping("")
    public ResponseEntity<RestResponse<ResponsePaginationDTO>> getAllForms(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return ResponseEntity.ok(RestResponse.<ResponsePaginationDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Fetch all forms successfully")
                .data(formService.getAllForms(pageNumber, pageSize))
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<ResponseFormDTO>> getSingleForm(@PathVariable("id") Long id) {
        return ResponseEntity.ok(RestResponse.<ResponseFormDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Fetch form successfully")
                .data(formService.getFormById(id))
                .build());
    }

    @PostMapping("")
    public ResponseEntity<RestResponse<ResponseFormDTO>> createForm(@Valid @RequestBody RequestFormDTO requestFormDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.<ResponseFormDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .error(null)
                .message("Create form successfully")
                .data(formService.createForm(requestFormDTO))
                .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestResponse<ResponseFormDTO>> updateForm(
            @PathVariable("id") Long id,
            @Valid @RequestBody RequestFormDTO requestFormDTO) {
        return ResponseEntity.ok(RestResponse.<ResponseFormDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Update form successfully")
                .data(formService.updateForm(requestFormDTO, id))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Object>> deleteForm(@PathVariable("id") Long id) {
        formService.deleteForm(id);
        return ResponseEntity.ok(RestResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Delete form successfully")
                .data(null)
                .build());
    }

    @PostMapping("/{formId}/fields")
    public ResponseEntity<RestResponse<Object>> addFieldToForm(
            @PathVariable("formId") Long formId,
            @Valid @RequestBody List<RequestFieldDTO> requestFieldDTOs
            ) {
        formService.addFieldsToForm(formId, requestFieldDTOs);
        return ResponseEntity.ok(RestResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .error(null)
                .message("Add field to form successfully")
                .data(null)
                .build());
    }

    @PatchMapping("/{formId}/fields/{fieldId}")
    public ResponseEntity<RestResponse<ResponseFieldDTO>> updateFieldOfForm(
            @PathVariable("formId") Long formId,
            @PathVariable("fieldId") Long fieldId,
            @Valid @RequestBody RequestFieldDTO requestFieldDTO
    ){
        return ResponseEntity.ok(RestResponse.<ResponseFieldDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Update field of form successfully")
                .data(formService.updateFieldOfForm(formId, fieldId, requestFieldDTO))
                .build());
    }

    @DeleteMapping("/{formId}/fields/{fieldId}")
    public ResponseEntity<RestResponse<Object>> deleteFieldOfForm(
            @PathVariable("formId") Long formId,
            @PathVariable("fieldId") Long fieldId
    ){
        formService.deleteFieldOfForm(formId, fieldId);
        return ResponseEntity.ok(RestResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Delete field of form successfully")
                .data(null)
                .build());
    }

    @GetMapping("/active")
    public ResponseEntity<RestResponse<ResponsePaginationDTO>> getAllActiveForms(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return ResponseEntity.ok(RestResponse.<ResponsePaginationDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Fetch all active forms successfully")
                .data(formService.getActiveForms(pageNumber, pageSize))
                .build());
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<RestResponse<ResponseSubmitDTO>> submitForm(
            @PathVariable("id") Long id,
            @Valid @RequestBody RequestSubmitDTO dto
            ) throws FieldValidationException {
        return ResponseEntity.ok(RestResponse.<ResponseSubmitDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Fetch all forms successfully")
                .data(submissionService.handleSubmitForm(id, dto))
                .build());
    }

}