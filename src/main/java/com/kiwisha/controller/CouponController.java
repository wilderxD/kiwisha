package com.kiwisha.controller;

import com.kiwisha.model.Coupon;
import com.kiwisha.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/cupones")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/verificar/{code}")
    public String validate(@PathVariable String code, Model model) {
        Optional<Coupon> coupon = couponService.validateCoupon(code);
        model.addAttribute("code", code);
        if (coupon.isPresent()) {
            model.addAttribute("valid", true);
            model.addAttribute("discount", coupon.get().getDiscountPercentage());
        } else {
            model.addAttribute("valid", false);
        }
        return "coupon-check";
    }

    @GetMapping("/api/validate/{code}")
    @ResponseBody
    public String validateApi(@PathVariable String code) {
        Optional<Coupon> coupon = couponService.validateCoupon(code);
        if (coupon.isPresent()) {
            return "Cupón válido: " + coupon.get().getDiscountPercentage() + "% de descuento";
        } else {
            return "Cupón inválido o expirado";
        }
    }
}
