package com.jsw.mes.mdm.service;

import com.jsw.mes.mdm.model.request.DepartmentRequest;
import com.jsw.mes.mdm.model.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
  public  DepartmentResponse addDepartment(DepartmentRequest departmentRequest);

    public  DepartmentResponse updateDepartment(DepartmentRequest departmentRequest);

    public  List<DepartmentResponse> deleteDepartment(List<Integer> departmentIdsList);

    public  DepartmentResponse getDepartment(int departmentId);

    public List<DepartmentResponse> getAllDepartments();
}
