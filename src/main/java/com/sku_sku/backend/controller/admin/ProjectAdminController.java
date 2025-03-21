package com.sku_sku.backend.controller.admin;

import com.sku_sku.backend.domain.Project;
import com.sku_sku.backend.dto.Request.ProjectDTO;
import com.sku_sku.backend.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/project")
//@PreAuthorize("hasRole('ADMIN_LION')")
@Tag(name = "관리자 기능: 프로젝트 관련")
public class ProjectAdminController {

    private final ProjectService projectService;

    @Operation(summary = "(민규) Project 추가", description = "Headers에 Bearer token 필요, body에 form-data로 Project의 title, subTitle, image 필요",
            responses = {@ApiResponse(responseCode = "201", description = "생성"),
                    @ApiResponse(responseCode = "409", description = "그 title 이미 있")})
    @PostMapping("/add")
    public ResponseEntity<Project> addProject(ProjectDTO.ProjectCreateRequest request) throws IOException {
            Project project = projectService.addProject(
                    request.getClassTh(),
                    request.getTitle(),
                    request.getSubTitle(),
                    request.getUrl(),
                    request.getImage());
            return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @Operation(summary = "(민규) Project 수정", description = "Headers에 Bearer token 필요, body에 form-data로 Project의 id와 수정하고 싶은 값만 넣으면 됨",
            responses = {@ApiResponse(responseCode = "201", description = "수정 성공 후 변경된 정보를 포함한 객체 생성"),
                    @ApiResponse(responseCode = "409", description = "그 title 이미 있"),
                    @ApiResponse(responseCode = "404", description = "그 id에 해당하는 값 없")})
    @PutMapping("/update")
    public ResponseEntity<Project> updateProject(ProjectDTO.ProjectUpdateRequest request) throws IOException {
            Project project = projectService.updateProject(
                    request.getId(),
                    request.getClassTh(),
                    request.getTitle(),
                    request.getSubTitle(),
                    request.getUrl(),
                    request.getImage());
            return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @Operation(summary = "(민규) id로 Project 개별 정보 조회", description = "Headers에 Bearer token 필요, 쿼리 파라미터로 Project의 id 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회를 하면 프로젝트 제목, 프로젝트 부제목, 프로젝트 사진이 출력."),
                    @ApiResponse(responseCode = "404", description = "그 id에 해당하는 값 없")})
    @GetMapping("")
    public ResponseEntity<com.sku_sku.backend.dto.Response.ProjectDTO.ResponseProjectUpdate> findProjectById(@RequestParam Long projectId) {
        com.sku_sku.backend.dto.Response.ProjectDTO.ResponseProjectUpdate responseProject = projectService.findProjectById(projectId);
            return ResponseEntity.status(HttpStatus.OK).body(responseProject);
    }

    @Operation(summary = "(민규) Project 삭제", description = "Headers에 Bearer token 필요, 쿼리 파라미터로 Project의 id 필요",
              responses = {@ApiResponse(responseCode = "200", description = "프로젝트 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "그 id에 해당하는 값 없")})
    @DeleteMapping("")
    public ResponseEntity<String> deleteProject(@RequestParam Long id) {
            projectService.deleteProject(id);
            return ResponseEntity.ok("Project 삭제 성공");
    }
}
