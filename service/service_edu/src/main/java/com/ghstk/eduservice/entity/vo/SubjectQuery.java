package com.ghstk.eduservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.ConstructorArgs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class SubjectQuery {


    private final String id;
    private final String label;
    private List<SubjectQuery> children = new ArrayList<>();

}
