package ru.bsuedu.cad.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import ru.bsuedu.cad.demo.repository.GroupRepository;
import ru.bsuedu.cad.demo.repository.StudentRepository;
import ru.bsuedu.cad.demo.entity.Group;

@Service
public class GroupService {
        final private StudentRepository studentRepository;
    final private GroupRepository groupRepository;

    public GroupService(StudentRepository studentRepository,  GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }


    public List<Group> searchGroupByPartName(String text) {
        return  groupRepository.searchByDescription(text);
    }

    public List<Group> getAllGroup() {
        return   ImmutableList.copyOf(groupRepository.findAll());
    }
}
