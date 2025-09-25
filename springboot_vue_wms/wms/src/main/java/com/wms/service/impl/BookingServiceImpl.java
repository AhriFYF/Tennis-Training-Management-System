package com.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.Booking;
import com.wms.mapper.BookingMapper;
import com.wms.service.BookingService;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl extends ServiceImpl<BookingMapper, Booking> implements BookingService {
    // 实现自定义方法
}