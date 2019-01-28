package br.com.devcave.jira.controller;

import br.com.devcave.jira.client.JiraClient;
import br.com.devcave.jira.vo.IssueField;
import br.com.devcave.jira.vo.IssuePriority;
import br.com.devcave.jira.vo.IssueProject;
import br.com.devcave.jira.vo.IssueRequest;
import br.com.devcave.jira.vo.IssueResponse;
import br.com.devcave.jira.vo.IssueType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/jira")
public class JiraController {

    @Autowired
    private JiraClient jiraClient;

    @PostMapping("issues/project/{project}/issueType/{issueType}/priority/{priority}/bulk")
    public HttpEntity<List<IssueResponse>> createIssues(@PathVariable(value = "project") String project,
                                   @PathVariable(value = "issueType") String issueType,
                                   @PathVariable(value = "priority") String priority,
                                   @RequestParam(value = "user") String user,
                                   @RequestParam(value = "password") String password,
                                   @RequestBody String... titles) {
        log.info("M=createIssues, project={}, issueType={}, priority={}, user={}, password={}",
                project, issueType, priority, user, password);

        String token = String.format("Basic %s", new String(Base64.getEncoder()
                            .encode(String.format("%s:%s", user, password).getBytes())));

        List<IssueResponse> result = new ArrayList<>();

        Arrays.asList(titles).forEach(title -> {
            IssueRequest issueRequest = IssueRequest.builder().fields(
                    IssueField.builder().summary(title)
                            .description(title)
                            .project(new IssueProject(project))
                            .issuetype(new IssueType(issueType))
                            .priority(new IssuePriority(priority))
                            .build()
                ).build();
            IssueResponse issueResponse = jiraClient.createIssue(token, issueRequest);
            log.info("issueResponse={}", issueResponse);
            result.add(issueResponse);
        });

        return new HttpEntity<>(result);
    }
}
