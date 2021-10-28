package com.example.be.service;

import com.example.be.dto.StudentMarkDTO;
import com.example.be.mapper.StudentMarkMapper;
import com.example.be.model.StudentMark;
import com.example.be.model.TestQuizz;
import com.example.be.repository.StudentMarkRepository;
import com.example.be.repository.TestQuizzRepository;
import com.example.be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentMarkService {

    private final StudentMarkRepository repo;
    private final TestQuizzRepository testQuizzRepo;
    private final UserRepository userRepo;
    private final AuthService authService;
    private final StudentMarkMapper studentMarkMapper;

    @Autowired
    public StudentMarkService(StudentMarkRepository repo,
                              TestQuizzRepository testQuizzRepo,
                              UserRepository userRepo,
                              AuthService authService,
                              StudentMarkMapper studentMarkMapper) {
        this.repo = repo;
        this.testQuizzRepo = testQuizzRepo;
        this.userRepo = userRepo;
        this.authService = authService;
        this.studentMarkMapper = studentMarkMapper;
    }

    public StudentMark saveStudentMark(StudentMarkDTO studentMarkDTO) {
        TestQuizz testQuizz = testQuizzRepo.findTestQuizzById(studentMarkDTO.getTestQuizzId());

        StudentMark studentMark = studentMarkMapper.map(studentMarkDTO, testQuizz, authService.getCurrentUser());
        studentMark.setMark(studentMarkDTO.getMark());
        return repo.save(studentMark);
    }

    public List<StudentMarkDTO> getAllStudentMarkByUserId(Long userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(studentMarkMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<StudentMarkDTO> getStudentMarkByTestId(Long testId) {
        return repo.findByTestQuizzId(testId)
                .stream()
                .map(studentMarkMapper::mapToDTO)
                .collect(Collectors.toList());
    }

}
