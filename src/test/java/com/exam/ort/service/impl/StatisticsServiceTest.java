package com.exam.ort.service.impl;

import com.exam.ort.entity.Progress;
import com.exam.ort.repository.ProgressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @Mock
    private ProgressRepository progressRepository;

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Test
    void shouldReturnStatisticsForUser() {
        long userId = 1L;

        Progress progress = new Progress();
        progress.setScorePercentage(80.0);

        when(progressRepository.findByUserId(userId)).thenReturn(progress);

        double actualScore = statisticsService.getUserScorePercentage(userId);

        assertEquals(80.0, actualScore);
        verify(progressRepository, times(1)).findByUserId(userId);
    }
}
