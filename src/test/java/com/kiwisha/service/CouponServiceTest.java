package com.kiwisha.service;

import com.kiwisha.model.Coupon;
import com.kiwisha.repository.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponService;

    @Test
    @DisplayName("Debe retornar el cupón si existe y está activo")
    void testValidateCouponActive() {
        Coupon coupon = new Coupon();
        coupon.setCode("KIWISHA20");
        coupon.setActive(true);

        when(couponRepository.findByCode("KIWISHA20")).thenReturn(Optional.of(coupon));

        Optional<Coupon> result = couponService.validateCoupon("KIWISHA20");

        assertTrue(result.isPresent());
        assertEquals("KIWISHA20", result.get().getCode());
    }

    @Test
    @DisplayName("Debe retornar vacío si el cupón existe pero está inactivo (Branch Coverage)")
    void testValidateCouponInactive() {
        Coupon coupon = new Coupon();
        coupon.setCode("EXPIRED");
        coupon.setActive(false);

        when(couponRepository.findByCode("EXPIRED")).thenReturn(Optional.of(coupon));

        Optional<Coupon> result = couponService.validateCoupon("EXPIRED");

        // El filtro .filter(Coupon::isActive) hará que el resultado sea vacío
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Debe retornar vacío si el código no existe en la base de datos")
    void testValidateCouponNotFound() {
        when(couponRepository.findByCode("INVALID")).thenReturn(Optional.empty());

        Optional<Coupon> result = couponService.validateCoupon("INVALID");

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Debe guardar un cupón correctamente")
    void testSaveCoupon() {
        Coupon coupon = new Coupon();
        coupon.setCode("NEWCODE");
        
        when(couponRepository.save(coupon)).thenReturn(coupon);

        Coupon result = couponService.save(coupon);

        assertNotNull(result);
        verify(couponRepository, times(1)).save(coupon);
    }
}
