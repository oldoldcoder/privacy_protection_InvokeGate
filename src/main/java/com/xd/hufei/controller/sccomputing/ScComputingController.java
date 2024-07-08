package com.xd.hufei.controller.sccomputing;

import com.xd.hufei.services.sccomputing.ScComputingService;
import com.xd.hufei.utils.ToolUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@RequestMapping("/dd/scc")
@Api("安全协同计算控制总类")
@Slf4j
public class ScComputingController {

    @Autowired
    private ScComputingService service;

    @ApiOperation("计算均值的函数")
    @PostMapping("/avg")
    public ResponseEntity<Object> avg(MultipartFile file){
        try {
            Path path = ToolUtils.saveFile(file, "scComputing-avg");
            Resource resource = service.avg(path);
            // 文件可达，返回我的文件
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 未找到文件
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found: " + resource.getFilename());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
    @ApiOperation("数据比较")
    @PostMapping("/compare")
    public ResponseEntity<Object> compare(MultipartFile file){
        try {
            Path path = ToolUtils.saveFile(file, "scComputing-compare");
            Resource resource = service.compare(path);
            // 文件可达，返回我的文件
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 未找到文件
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found: " + resource.getFilename());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
    @ApiOperation("相等性测试")
    @PostMapping("/equal")
    public ResponseEntity<Object> equal(MultipartFile file){
        try {
            Path path = ToolUtils.saveFile(file, "scComputing-equal");
            Resource resource = service.equal(path);
            // 文件可达，返回我的文件
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 未找到文件
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found: " + resource.getFilename());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
    @ApiOperation("最大值最小值比较")
    @PostMapping("/min_max")
    public ResponseEntity<Object> minAndMax(MultipartFile file){
        try {
            Path path = ToolUtils.saveFile(file, "scComputing-min_max");
            Resource resource = service.min_max(path);
            // 文件可达，返回我的文件
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 未找到文件
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found: " + resource.getFilename());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
    @ApiOperation("包含性关系")
    @PostMapping("/include")
    public ResponseEntity<Object> include(MultipartFile file){
        try {
            Path path = ToolUtils.saveFile(file, "scComputing-include");
            Resource resource = service.include(path);
            // 文件可达，返回我的文件
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 未找到文件
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found: " + resource.getFilename());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
    @ApiOperation("范围相交")
    @PostMapping("/intersect")
    public ResponseEntity<Object> intersect(MultipartFile file){
        try {
            Path path = ToolUtils.saveFile(file, "scComputing-intersect");
            Resource resource = service.intersect(path);
            // 文件可达，返回我的文件
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 未找到文件
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found: " + resource.getFilename());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
    @ApiOperation("求内积")
    @PostMapping("/inner_product")
    public ResponseEntity<Object> inner_product(MultipartFile file){
        try {
            Path path = ToolUtils.saveFile(file, "scComputing-inner_product");
            Resource resource = service.inner_product(path);
            // 文件可达，返回我的文件
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 未找到文件
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found: " + resource.getFilename());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
    @ApiOperation("求欧式距离")
    @PostMapping("/distance")
    public ResponseEntity<Object> distance(MultipartFile file){
        try {
            Path path = ToolUtils.saveFile(file, "scComputing-distance");
            Resource resource = service.distance(path);
            // 文件可达，返回我的文件
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 未找到文件
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found: " + resource.getFilename());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
    @ApiOperation("将数据分箱")
    @PostMapping("/split")
    public ResponseEntity<Object> split(MultipartFile file){
        try {
            Path path = ToolUtils.saveFile(file, "scComputing-split");
            Resource resource = service.split(path);
            // 文件可达，返回我的文件
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 未找到文件
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found: " + resource.getFilename());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
    @ApiOperation("计算频率")
    @PostMapping("/frequency")
    public ResponseEntity<Object> frequency(MultipartFile file){
        try {
            Path path = ToolUtils.saveFile(file, "scComputing-frequency");
            Resource resource = service.frequency(path);
            // 文件可达，返回我的文件
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 未找到文件
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found: " + resource.getFilename());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }


}
