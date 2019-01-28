package br.com.devcave.jira.vo;

import lombok.Data;

@Data
public class IssueResponse {
    private String id;
    private String key;
    private String self;
}
