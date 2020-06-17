package com.gitlab.projects.dto;

import lombok.Data;

@Data
public class CodeReport {
    private int bugs;
    private int vulnerabilities;
    private float duplicated_lines_density;
    private int code_smells;
}
