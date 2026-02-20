package com.example.orgmanager.soap;

import com.example.orgmanager.dto.OrganizationDTO;
import com.example.orgmanager.dto.OrganizationStatisticsDTO;
import com.example.orgmanager.ejb.OrganizationManagerRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@Stateless
@WebService(
        name = "OrganizationManagerService",
        serviceName = "OrganizationManagerService",
        portName = "OrganizationManagerPort",
        targetNamespace = "http://soap.orgmanager.example.com/",
        endpointInterface = "com.example.orgmanager.soap.OrganizationManagerService"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class OrganizationManagerServiceImpl implements OrganizationManagerService {

    private static final Logger log = LoggerFactory.getLogger(OrganizationManagerServiceImpl.class);

    @EJB
    private OrganizationManagerRemote managerBean;

    @Override
    public OrganizationDTO mergeOrganizations(Long id1, Long id2, String newName, String newAddress) {
        log.info("SOAP mergeOrganizations({}, {}, {}, {})", id1, id2, newName, newAddress);
        return managerBean.mergeOrganizations(id1, id2, newName, newAddress);
    }

    @Override
    public OrganizationDTO hireEmployee(Long id) {
        log.info("SOAP hireEmployee({})", id);
        return managerBean.hireEmployee(id);
    }

    @Override
    public OrganizationStatisticsDTO getOrganizationStatistics() {
        log.info("SOAP getOrganizationStatistics()");
        return managerBean.getOrganizationStatistics();
    }
}