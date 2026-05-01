package com.example.formmanagement.controller;

import com.example.formmanagement.domain.response.ResponseFormDTO;
import com.example.formmanagement.domain.response.ResponseSubmitDTO;
import com.example.formmanagement.domain.response.RestResponse;
import com.example.formmanagement.service.SubmissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/submissions")
public class SubmissionController {
    SubmissionService submissionService;

    @GetMapping("")
    public ResponseEntity<RestResponse<List<ResponseSubmitDTO>>> getAllForms() {
        return ResponseEntity.ok(RestResponse.<List<ResponseSubmitDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .error(null)
                .message("Fetch all forms successfully")
                .data(submissionService.getSubmittedForm())
                .build());
    }

}
