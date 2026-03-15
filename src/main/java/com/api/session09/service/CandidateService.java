package com.api.session09.service;

import com.api.session09.dto.ApiResponse;
import com.api.session09.dto.CandidateApplyDTO;
import com.api.session09.entity.Candidate;
import com.api.session09.exception.FileStorageException;

public interface CandidateService {
    ApiResponse<Candidate> createApplication(CandidateApplyDTO request) throws FileStorageException;
}
