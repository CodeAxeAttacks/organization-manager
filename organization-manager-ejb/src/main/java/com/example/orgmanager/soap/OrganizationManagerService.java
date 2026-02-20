package com.example.orgmanager.soap;

import com.example.orgmanager.dto.OrganizationDTO;
import com.example.orgmanager.dto.OrganizationStatisticsDTO;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService(
    name = "OrganizationManagerService",
    targetNamespace = "http://soap.orgmanager.example.com/"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface OrganizationManagerService {

    @WebMethod(operationName = "mergeOrganizations")
    @WebResult(name = "organization", targetNamespace = "http://soap.orgmanager.example.com/")
    OrganizationDTO mergeOrganizations(
        @WebParam(name = "id1", targetNamespace = "http://soap.orgmanager.example.com/") Long id1,
        @WebParam(name = "id2", targetNamespace = "http://soap.orgmanager.example.com/") Long id2,
        @WebParam(name = "newName", targetNamespace = "http://soap.orgmanager.example.com/") String newName,
        @WebParam(name = "newAddress", targetNamespace = "http://soap.orgmanager.example.com/") String newAddress
    );

    @WebMethod(operationName = "hireEmployee")
    @WebResult(name = "organization", targetNamespace = "http://soap.orgmanager.example.com/")
    OrganizationDTO hireEmployee(
        @WebParam(name = "id", targetNamespace = "http://soap.orgmanager.example.com/") Long id
    );

    @WebMethod(operationName = "getOrganizationStatistics")
    @WebResult(name = "statistics", targetNamespace = "http://soap.orgmanager.example.com/")
    OrganizationStatisticsDTO getOrganizationStatistics();
}