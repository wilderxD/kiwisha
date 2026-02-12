package com.kiwisha.service;

import com.kiwisha.model.Coupon;
import com.kiwisha.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public Optional<Coupon> validateCoupon(String code) {
        return couponRepository.findByCode(code)
                .filter(Coupon::isActive);
    }
    
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }
}
