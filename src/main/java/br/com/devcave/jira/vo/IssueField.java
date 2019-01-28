package br.com.devcave.jira.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueField {
    private IssueProject project;
    private IssueType issuetype;
    private String summary;
    private IssuePriority priority;
    private String description;
}
