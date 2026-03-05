package com.api.session09.controller;

import com.api.session09.dto.ApiResponse;
import com.api.session09.dto.CandidateApplyDTO;
import com.api.session09.entity.Candidate;
import com.api.session09.exception.FileStorageException;
import com.api.session09.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Candidate>> createApplication(@Valid @ModelAttribute CandidateApplyDTO request) throws FileStorageException {
        return new ResponseEntity<>(candidateService.createApplication(request), HttpStatus.CREATED);
    }
}
