package com.bsnandras.reedcatalog.unitTests;

import com.bsnandras.reedcatalog.repositories.PartnerRepository;
import com.bsnandras.reedcatalog.services.pages.PartnerListServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PartnerListServiceTest {

    @InjectMocks
    private PartnerListServiceImpl partnerListService;

    @Mock
    private PartnerRepository partnerRepository;

    @Test
    void getPartner() {
    }

    @Test
    void shouldGetAllPartners() {
        //When
        partnerListService.getAllPartners();
        //Then
        verify(partnerRepository).findAllByOrderByName();
    }

    @Test
    void addPartner() {
    }
}