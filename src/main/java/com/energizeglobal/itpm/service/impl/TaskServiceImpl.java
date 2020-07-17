package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {


}
