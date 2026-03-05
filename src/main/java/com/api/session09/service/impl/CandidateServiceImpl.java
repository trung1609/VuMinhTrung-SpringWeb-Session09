package com.api.session09.service.impl;

import com.api.session09.dto.ApiResponse;
import com.api.session09.dto.CandidateApplyDTO;
import com.api.session09.entity.Candidate;
import com.api.session09.exception.FileStorageException;
import com.api.session09.repository.CandidateRepository;
import com.api.session09.service.CandidateService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private Cloudinary cloudinary;


    @Override
    @Transactional
    public ApiResponse<Candidate> createApplication(CandidateApplyDTO request) throws FileStorageException {
       String cvFile = null;

        MultipartFile file = request.getCvFile();

        if (file == null || file.isEmpty()){
            throw new FileStorageException("CV file is required");
        }

        String contentType = file.getContentType();
        if(contentType == null || !contentType.equals("application/pdf")){
            throw new FileStorageException("Only PDF files are allowed");
        }

        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            cvFile = (String) result.get("url");
        }catch (IOException e){
            throw new FileStorageException("Failed to upload CV file");
        }

        Candidate candidate = Candidate.builder()
                .name(request.getName())
                .email(request.getEmail())
                .cvUrl(cvFile)
                .build();

        candidateRepository.save(candidate);

        return new ApiResponse<>(
                "Success",
                "Create application successfully",
                candidate
        );
    }
}
