package com.ghstk.eduservice.entity.vo;

import lombok.Data;

@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLv1;
    private String subjectLv2;
    private String teacherName;
    private String price;

}
